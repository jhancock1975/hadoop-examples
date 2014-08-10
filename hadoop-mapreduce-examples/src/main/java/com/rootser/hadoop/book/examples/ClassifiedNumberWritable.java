package com.rootser.hadoop.book.examples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.codehaus.jackson.JsonParser.NumberType;

public class ClassifiedNumberWritable implements Writable {
	public ClassifiedNumberWritable(){
		index = new IntWritable();
		number = new IntWritable();
		numberClass = new IntWritable();
	}
	IntWritable index ;
	IntWritable number;
	IntWritable numberClass;
	public void set(int index, int number, NumberTypes numberClass){
		this.index.set(index);
		this.number.set(number);
		this.numberClass.set(numberClass.ordinal());
	}
	public void set(IntWritable index, IntWritable number, NumberTypes numberClass){
		this.index = index;
		this.number = number;
		if (numberClass == null){
			this.numberClass.set(NumberTypes.UNKNOWN.ordinal());
		} else {
			this.numberClass.set(numberClass.ordinal());
		}
		
	}
	public IntWritable getIndex() {
		return index;
	}

	public void setIndex(IntWritable index) {
		this.index = index;
	}

	public IntWritable getNumber() {
		return number;
	}

	public void setNumber(IntWritable number) {
		this.number = number;
	}

	public IntWritable getNumberClass() {
		return numberClass;
	}

	public void setNumberClass(IntWritable numberClass) {
		this.numberClass = numberClass;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		index.write(out);
		number.write(out);
		numberClass.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		index.readFields(in);
		number.readFields(in);
		numberClass.readFields(in);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result
				+ ((numberClass == null) ? 0 : numberClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassifiedNumberWritable other = (ClassifiedNumberWritable) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (numberClass == null) {
			if (other.numberClass != null)
				return false;
		} else if (!numberClass.equals(other.numberClass))
			return false;
		return true;
	}

}
