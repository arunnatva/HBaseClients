
Objective:
This notes explains the process to load data from Spark to HBase. It involves the following steps.
- export SPARK_HOME to point to spark 2.x
- $ export SPARK_HOME=/usr/hdp/current/spark2-client
- Add the SHC(Spark HBase Connector) jars to the spark class path
- $ $SPARK_HOME/bin/spark-shell --master yarn --packages com.hortonworks:shc-core:1.1.1-2.1-s_2.11 --repositories http://repo.hortonworks.com/content/groups/public/ --conf spark.driver.extraClassPath=/etc/hbase/conf
- Map the schema from spark dataframe to a hbase table to create a hbase catalog
- Write the dataframe into a hbase table 
