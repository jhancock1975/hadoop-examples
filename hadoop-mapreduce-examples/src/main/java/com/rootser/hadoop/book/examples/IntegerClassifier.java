package com.rootser.hadoop.book.examples;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IntegerClassifier {

	static class ClassifierMapper extends Mapper<IntWritable, IntWritable, IntWritable, ClassifiedNumberWritable>{
		//checks whether an int is prime or not.
		boolean isPrime(int n) {
			//check if n is a multiple of 2
			if (n%2==0) return false;
			//if not, then just check the odds
			for(int i=3;i*i<=n;i+=2) {
				if(n%i==0)
					return false;
			}
			return true;
		}
		ClassifiedNumberWritable outputVal = new ClassifiedNumberWritable();
		@Override
		public void map(IntWritable key, IntWritable value, Context context)
				throws IOException, InterruptedException {
			int val = value.get();
			if (isPrime(val)){
				outputVal.set(key, value, NumberTypes.PRIME);
			} else {
				outputVal.set(key, value, NumberTypes.COMPOSITE);
			}
			context.write(value, outputVal);
		}
	}
	static class ClassifierReducer extends Reducer<IntWritable, ClassifiedNumberWritable, Text, Text>{
		Text outputText1 = new Text();
		Text outputText2 = new Text();
		StringBuffer numList = new StringBuffer();
		@Override
		public void reduce(IntWritable key, Iterable<ClassifiedNumberWritable> values,
				Context context)
						throws IOException, InterruptedException {
			numList.setLength(0);
			outputText1.set(key.toString());
			for (ClassifiedNumberWritable c: values){
				numList.append(c.getNumber()).append(", ");
			}
			outputText2.set(numList.toString());
			context.write(outputText1, outputText2);
		}
	}
	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.err.println("Usage: IntegerClassifier <input path> <output path>");
			System.exit(-1);
		}

		Job job = new Job();
		job.setJarByClass(IntegerClassifier.class);
		job.setJobName("IntegerClassifier");
		

		job.setInputFormatClass(SequenceFileInputFormat.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(ClassifierMapper.class);
		job.setReducerClass(ClassifierReducer.class);

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(ClassifiedNumberWritable.class);
		boolean result = false;
		try {
			result = job.waitForCompletion(true);
		} catch (Exception e){
			e.printStackTrace();
		}
		finally {
			System.exit(result ? 0 : 1);
		}
	}
}
