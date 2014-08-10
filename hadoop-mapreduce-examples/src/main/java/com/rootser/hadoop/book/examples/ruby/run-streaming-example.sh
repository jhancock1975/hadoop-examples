# this is an example command using streaming
hdfs dfs -rm -r hdfs://oishii:9000/max-temp-output
SCRIPTS_DIR=/streaming-scripts
yarn jar /home/john/Downloads/hadoop-2.4.0/share/hadoop/tools/lib/hadoop-streaming-2.4.0.jar -input /weather-data/sample.txt -output /max-temp-output -mapper $SCRIPTS_DIR/max-temp-mapper.rb -reducer $SCRIPTS_DIR=/max-temp-reduce.rb

