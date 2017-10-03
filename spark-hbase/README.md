
Objective:
This notes explains the process to load data from Spark to HBase. It involves the following steps.
- export SPARK_HOME to point to spark 2.x
- $ export SPARK_HOME=/usr/hdp/current/spark2-client
- Add the SHC(Spark HBase Connector) jars to the spark class path
- $ $SPARK_HOME/bin/spark-shell --master yarn --packages com.hortonworks:shc-core:1.1.1-2.1-s_2.11 --repositories http://repo.hortonworks.com/content/groups/public/ --conf spark.driver.extraClassPath=/etc/hbase/conf
- Map the schema from spark dataframe to a hbase table to create a hbase catalog
- Write the dataframe into a hbase table "shc_test"
- verify the hbase table whether it is populated or not by scanning the same

hbase(main):001:0> scan 'shc_test'
ROW                               COLUMN+CELL
 arun                             column=cf1:col1, timestamp=1507066574896, value=\xFF
 arun                             column=cf2:col2, timestamp=1507066574896, value=@A\x1Dp\xA3\xD7\x0A=
 arun                             column=cf3:col3, timestamp=1507066574896, value=A6\xB8R
 arun                             column=cf4:col4, timestamp=1507066574896, value=\x00\x00\x00'
 arun                             column=cf5:col5, timestamp=1507066574896, value=\x00\x00\x00\x00\x00@\xB0\x17
 arun                             column=cf6:col6, timestamp=1507066574896, value=\x00\x0C
 arun                             column=cf7:col7, timestamp=1507066574896, value=natva
 arun                             column=cf8:col8, timestamp=1507066574896, value=\x08
 mickey                           column=cf1:col1, timestamp=1507066574896, value=\x00
 mickey                           column=cf2:col2, timestamp=1507066574896, value=@O\x9Dp\xA3\xD7\x0A=
 mickey                           column=cf3:col3, timestamp=1507066574896, value=B\x88u\xC3
 mickey                           column=cf4:col4, timestamp=1507066574896, value=\x00\x00\x00R
 mickey                           column=cf5:col5, timestamp=1507066574896, value=\x00\x00\x00\x00\x00r\x06\xB3
 mickey                           column=cf6:col6, timestamp=1507066574896, value=\x007
 mickey                           column=cf7:col7, timestamp=1507066574896, value=vihari
 mickey                           column=cf8:col8, timestamp=1507066574896, value=\x05
 vaishu                           column=cf1:col1, timestamp=1507066575164, value=\xFF
 vaishu                           column=cf2:col2, timestamp=1507066575164, value=@I\xB4z\xE1G\xAE\x14
 vaishu                           column=cf3:col3, timestamp=1507066575164, value=AF\xB8R
 vaishu                           column=cf4:col4, timestamp=1507066575164, value=\x00\x00\x002
 vaishu                           column=cf5:col5, timestamp=1507066575164, value=\x00\x00\x00\x00\x00Wh9
 vaishu                           column=cf6:col6, timestamp=1507066575164, value=\x00\x0B
 vaishu                           column=cf7:col7, timestamp=1507066575164, value=natva
 vaishu                           column=cf8:col8, timestamp=1507066575164, value=\x09
3 row(s) in 0.4410 seconds
