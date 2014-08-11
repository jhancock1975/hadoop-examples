!#/bin/bash
hdfs dfs -rm -r /user/john/matrix-out
jar cvf rootser.jar classes/
 hadoop jar rootser.jar com.rootser.MatMultMapRed -libjars ./rootser.jar /user/john/matrix /user/john/matrix-out

