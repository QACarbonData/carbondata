/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.carbondata.query.filter.executor.impl;

import java.util.BitSet;

import org.carbondata.core.datastorage.store.columnar.ColumnarKeyStoreDataHolder;
import org.carbondata.core.util.ByteUtil;
import org.carbondata.core.util.MolapUtil;
import org.carbondata.query.filter.executor.FilterExecutor;

public class NonUniqueBlockEquals implements FilterExecutor {
    @Override
    public BitSet getFilteredIndexes(ColumnarKeyStoreDataHolder keyBlockArray, int numerOfRows,
            byte[][] filterValues) {
        return setFilterdIndexToBitSet(keyBlockArray, numerOfRows, filterValues);
    }

    private BitSet setFilterdIndexToBitSet(ColumnarKeyStoreDataHolder keyBlockArray,
            int numerOfRows, byte[][] filterValues) {

        int start = 0;
        int startIndex = 0;
        int last /*= numerOfRows - 1*/;
        int[] columnIndex = keyBlockArray.getColumnarKeyStoreMetadata().getColumnIndex();

        BitSet bitSet = new BitSet(numerOfRows);
        for (int i = 0; i < filterValues.length; i++) {
            start = MolapUtil
                    .getFirstIndexUsingBinarySearch(keyBlockArray, startIndex, numerOfRows - 1,
                            filterValues[i]);
            if (start == -1) {
                continue;
            }
            if (null != columnIndex) {
                bitSet.set(columnIndex[start]);
            } else {
                bitSet.set(start);
            }

            last = start;
            for (int j = start + 1; j < numerOfRows; j++) {
                if (ByteUtil.UnsafeComparer.INSTANCE
                        .compareTo(keyBlockArray.getKeyBlockData(), j * filterValues[i].length,
                                filterValues[i].length, filterValues[i], 0, filterValues[i].length)
                        == 0) {
                    if (null != columnIndex) {
                        bitSet.set(columnIndex[j]);
                    } else {
                        bitSet.set(j);
                    }
                    last++;
                } else {
                    break;
                }
            }
            startIndex = last;
            if (startIndex >= numerOfRows) {
                break;
            }
        }
        return bitSet;
    }
}
