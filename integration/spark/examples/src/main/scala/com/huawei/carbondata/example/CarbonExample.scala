package com.huawei.carbondata.example

import org.apache.spark.sql.OlapContext
import org.apache.spark.{SparkConf, SparkContext}

object CarbonExample {

  def main(args: Array[String]) {

    val hdfsCarbonBasePath = "/home/root1/carbon/metadata"
    val sc = new SparkContext(new SparkConf()
      .setAppName("CarbonSpark")
      .setMaster("local[2]"))

    val oc = new OlapContext(sc, hdfsCarbonBasePath)
    oc.setConf("molap.kettle.home", "/home/root1/carbon/carbondata/processing/molapplugins/molapplugins")

//    oc.sql("drop cube alldatatypescube")

    oc.sql("CREATE CUBE alldatatypescube DIMENSIONS (empno Integer, empname String, " +
      "designation String, doj Timestamp, workgroupcategory Integer, workgroupcategoryname String, " +
      "deptno Integer, deptname String, projectcode Integer, projectjoindate Timestamp, " +
      "projectenddate Timestamp) MEASURES (attendance Integer,utilization Integer,salary Integer) " +
      "OPTIONS (PARTITIONER [PARTITION_COUNT=1])")

    oc.sql("LOAD DATA fact from '/home/root1/carbon/carbondata/integration/spark/src/test/resources/data.csv' INTO CUBE alldatatypescube PARTITIONDATA(DELIMITER ',', QUOTECHAR '\"')");

    oc.sql("select empno,empname,utilization,count(salary),sum(empno) from alldatatypescube where empname in ('arvind','ayushi') group by empno,empname,utilization").show()

  }

}
