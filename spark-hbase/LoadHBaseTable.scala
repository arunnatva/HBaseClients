
//export SPARK_HOME=/usr/hdp/current/spark2-client
//$SPARK_HOME/bin/spark-shell --master yarn --packages com.hortonworks:shc-core:1.1.1-2.1-s_2.11 --repositories http://repo.hortonworks.com/content/groups/public/ --conf spark.driver.extraClassPath=/etc/hbase/conf

import org.apache.spark.sql.execution.datasources.hbase._
import org.apache.spark.sql.functions._

def catalog = s"""{
     |         |"table":{"namespace":"default", "name":"shc_test"},
     |         |"rowkey":"key",
     |         |"columns":{
     |           |"col0":{"cf":"rowkey", "col":"key", "type":"string"},
     |           |"col1":{"cf":"cf1", "col":"col1", "type":"boolean"},
     |           |"col2":{"cf":"cf2", "col":"col2", "type":"double"},
     |           |"col3":{"cf":"cf3", "col":"col3", "type":"float"},
     |           |"col4":{"cf":"cf4", "col":"col4", "type":"int"},
     |           |"col5":{"cf":"cf5", "col":"col5", "type":"bigint"},
     |           |"col6":{"cf":"cf6", "col":"col6", "type":"smallint"},
     |           |"col7":{"cf":"cf7", "col":"col7", "type":"string"},
     |           |"col8":{"cf":"cf8", "col":"col8", "type":"tinyint"}
     |         |}
     |       |}""".stripMargin
	 
//Class with the schema that maps to the data from the input file or spark dataframe
case class HBaseRecord(col0: String, col1: Boolean,col2: Double, col3: Float,col4: Int,col5: Long, col6: Short, col7: String, col8: Byte)

val myrdd = sc.textFile("/user/arun/testfile")
val myrdd1 = myrdd.map(line => line.split(","))
val mydf = myrdd1.map(line =>  HBaseRecord(line(0),line(1).toBoolean,line(2).toDouble,line(3).toFloat,line(4).toInt,line(5).toLong,line(6).toShort,line(7),line(8).toByte)).toDF()
mydf.write.options(Map(HBaseTableCatalog.tableCatalog -> catalog, HBaseTableCatalog.newTable -> "4")).format("org.apache.spark.sql.execution.datasources.hbase").save()
