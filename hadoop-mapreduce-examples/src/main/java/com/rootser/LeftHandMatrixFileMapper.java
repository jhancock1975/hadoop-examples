package com.rootser;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LeftHandMatrixFileMapper extends Mapper<Object, Text, MatrixColumnEntryWritable, DoubleWritable>{
	static int globalRowCount=0;
	static int globalColumnCount=0;
	DoubleWritable curMatrixEntry = new DoubleWritable();
	MatrixColumnEntryWritable curEntry = new MatrixColumnEntryWritable();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer itr = new StringTokenizer(value.toString());
		while (itr.hasMoreTokens()) {
			curEntry.setI(globalRowCount);
			curEntry.setJ(globalColumnCount);
			curMatrixEntry.set(Double.parseDouble(itr.nextToken()));
			context.write(curEntry, curMatrixEntry);
		}
	}
}
