package com.rootser.hadoop.book.examples;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxTextIntWritableReducer extends Reducer<IntWritable, TextIntWritablePair, Text, IntWritable> {
	  
	  @Override
	  public void reduce(IntWritable key, Iterable<TextIntWritablePair> values,
	      Context context)
	      throws IOException, InterruptedException {
	    
	    int maxValue = Integer.MIN_VALUE;
	    Text maxText = new Text("not found");
	    for (TextIntWritablePair value : values) {
	      maxValue = Math.max(maxValue, key.get());
	      if (maxValue == key.get()){
	    	  maxText = value.getFirst();
	      }
	    }
	    context.write(maxText, new IntWritable(maxValue));
	  }
	} 