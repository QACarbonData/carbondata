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

package org.carbondata.query.executer.calcexp.impl;

import org.carbondata.query.aggregator.MeasureAggregator;

/**
 * This class is for calculating the equal functionality.
 */
public class MolapEqualFunction extends AbstractMolapCalcFunction {

    /**
     *
     */
    private static final long serialVersionUID = 6534281025960495301L;

    /**
     * If true then this function returns 1 and if false then 0.
     */
    @Override public double calculate(MeasureAggregator[] msrAggs) {
        double left = leftOperand.calculate(msrAggs);
        double right = rightOperand.calculate(msrAggs);
        //CHECKSTYLE:OFF    Approval No:Approval-212
        if (left == right)//CHECKSTYLE:ON
        {
            conditionValue = true;
            return 1;
        }
        return 0;
    }

}
