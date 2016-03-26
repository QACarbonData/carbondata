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

package org.apache.spark.sql.hive

import org.apache.spark.sql.catalyst.ParserDialect
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.execution.SparkPlan
import org.apache.spark.sql.execution.datasources.DDLException
import org.apache.spark.sql.{MolapSqlDDLParser, SQLContext, Strategy}

/**
  * @author V71149
  */
private[sql] object CarbonStrategy {
  def getStrategy(context: SQLContext): Strategy = {
    new OlapStrategies(context).OlapCubeScans
  }
}

private[spark] class CarbonSQLDialect extends HiveQLDialect {

  @transient
  protected val sqlParser = new MolapSqlDDLParser

  override def parse(sqlText: String): LogicalPlan = {

    try {
      sqlParser.parse(sqlText)
    } catch {
      //case ddlException: DDLException => throw ddlException
      case _ => super.parse(sqlText)
      case x: Throwable => throw x
    }
  }
}