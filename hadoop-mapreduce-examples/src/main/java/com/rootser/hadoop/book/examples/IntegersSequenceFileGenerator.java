package com.rootser.hadoop.book.examples;

import java.io.IOException;
import java.net.URI;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;

public class IntegersSequenceFileGenerator {
	static Random rand = new Random();
	public static void main(String[] args) throws IOException {
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		Path path = new Path(uri);

		IntWritable key = new IntWritable();
		IntWritable value = new IntWritable();
		SequenceFile.Writer writer = null;
		try {
			writer = SequenceFile.createWriter(fs, conf, path,
					key.getClass(), value.getClass());

			for (int i = 0; i < 6000000; i++) {
				key.set(i);
				value.set(rand.nextInt());
				writer.append(key, value);
				if (i % 1000 == 0){
					System.out.println("wrote " + i + "paris to file.");
				}
			}
		} finally {
			IOUtils.closeStream(writer);
		}
	}
}

