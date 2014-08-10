package com.rootser.hadoop.book.examples;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTextIntWritablePair {
	 public static void main(String[] args) throws Exception {
		    if (args.length != 2) {
		      System.err.println("Usage: MaxTextIntWritablePair <input path> <output path>");
		      System.exit(-1);
		    }
		    
		    Job job = new Job();
		    job.setJarByClass(MaxTextIntWritablePair.class);
		    job.setJobName("Max MaxTextIntWritablePair");

		    FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		    
		    job.setMapperClass(MaxTextIntWritableMapper.class);
		    job.setReducerClass(MaxTextIntWritableReducer.class);

		    job.setOutputKeyClass(IntWritable.class);
		    job.setOutputValueClass(TextIntWritablePair.class);
		    
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
		  }
}
