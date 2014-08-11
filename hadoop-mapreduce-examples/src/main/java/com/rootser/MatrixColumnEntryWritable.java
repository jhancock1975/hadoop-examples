package com.rootser;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
/**
 * this class serves as keys when we want
 * to map row elements of a matrix to 
 * @author john
 *
 */
public class MatrixColumnEntryWritable implements WritableComparable<MatrixColumnEntryWritable> {
	private int i;
	private int j;

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(i);
		out.writeInt(j);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		i = in.readInt();
		j = in.readInt();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
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
		MatrixColumnEntryWritable other = (MatrixColumnEntryWritable) obj;
		if (i != other.i)
			return false;
		return true;
	}

	@Override
	public int compareTo(MatrixColumnEntryWritable o) {
		return this.i - o.i;
	}

}
