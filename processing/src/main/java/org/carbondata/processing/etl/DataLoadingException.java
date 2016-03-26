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

package org.carbondata.processing.etl;

public class DataLoadingException extends Exception {
    private static final long serialVersionUID = 1L;

    private long errorCode = -1;

    public DataLoadingException() {
        super();
    }

    public DataLoadingException(long errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DataLoadingException(String message) {
        super(message);
    }

    public DataLoadingException(Throwable cause) {
        super(cause);
    }

    public DataLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public long getErrorCode() {
        return errorCode;
    }

}