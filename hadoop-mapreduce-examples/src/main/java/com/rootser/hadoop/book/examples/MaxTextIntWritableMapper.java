package com.rootser.hadoop.book.examples;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxTextIntWritableMapper extends
		Mapper<LongWritable, Text, IntWritable, TextIntWritablePair> {
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] splitLine = line.split("\t");
		IntWritable outputKey = new IntWritable(Integer.parseInt(splitLine[0].trim()));
		TextIntWritablePair outputValue = new TextIntWritablePair(value, outputKey);
		try {
		context.write(outputKey,outputValue);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
