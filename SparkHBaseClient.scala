export SPARK_HOME=/usr/hdp/current/spark2-client

//Please ensure the spark shell or spark-submit adds hbase jars and configuration into both driver and executor classpath.

$SPARK_HOME/bin/spark-shell --master yarn  --conf spark.driver.extraClassPath=/etc/hbase/conf --conf spark.executor.extraClassPath=`hbase classpath`

import java.lang.String
import org.apache.spark.sql.functions._
import org.apache.spark.SparkContext

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{HBaseAdmin,HTable,Put,Get}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor}
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HColumnDescriptor
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.client.HTable
import org.apache.hadoop.hbase.TableName


val tblnm = "test_tbl"

val inputfile = "/path/to/parquet/hdfs/file"
val mydf = spark.read.parquet(inputfile)
val mydfrdd = mydf.rdd

// "paste" command helps you to enter multiple lines of code into scala REPL
:paste

//the below code within "foreachpartition" will run once for each partition in the dataframe/RDD. This helps to execute one hbase put for each partition
dfprdd.foreachPartition(partition => { 
var progress = 0
//import HBase Configuration, Admin etc., classes
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor}
import org.apache.hadoop.hbase.client.{HBaseAdmin,HTable,Put,Get}
val config = HBaseConfiguration.create()
//val admin = new HBaseAdmin(config)
config.set(TableInputFormat.INPUT_TABLE, tblnm)
val myTable = new HTable(config, tblnm)
myTable.setAutoFlush(false)
//Create a List of HBase Put objects 
var puts = new java.util.ArrayList[Put]()
// foreach method below loops through each record in a partition and builds the ArrayList of Put objects.
partition.foreach(record => {
var rowKey = record.getLong(0).toString;
var quotient = record.getDouble(2).toString
var category = record.getInt(1).toString
var p = new Put(rowKey.getBytes())
p.add("mycolfm".getBytes(), category.getBytes(), quotient.getBytes())
puts.add(p)
})
myTable.put(puts)
myTable.flushCommits()
progress = progress + puts.size()
println ("Finished loading ".concat(progress.toString).concat("rows") )
})

