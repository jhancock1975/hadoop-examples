package com.rootser;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MatMultReducer  extends Reducer<MatrixColumnEntryWritable, DoubleWritable, Text, NullWritable>{
	Text outputText = new Text();
	StringBuffer outputLine = new StringBuffer();
	public void reduce(MatrixColumnEntryWritable entry, Iterable<DoubleWritable> values, Context context)
			throws  IOException, InterruptedException {
		outputLine.setLength(0);
		outputLine.append("[").append(entry.getI()).append(", ").append(entry.getJ()).append("] = ");
		for (DoubleWritable d: values){
			outputLine.append(d).append(", ");
		}
		outputLine.append("\n");
	}
}
