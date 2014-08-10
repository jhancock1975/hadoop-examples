package com.rootser;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class WordPair implements WritableComparable{
	private String w1;
	private String w2;
	public void setWordPair(String w1, String w2){
		this.w1 = w1;
		this.w2 = w2;
	}
	public String toString(){
		return w1 + w2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((w1 == null) ? 0 : w1.hashCode());
		result = prime * result + ((w2 == null) ? 0 : w2.hashCode());
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
		WordPair other = (WordPair) obj;
		if (w1 == null) {
			if (other.w1 != null)
				return false;
		} else if (!w1.equals(other.w1))
			return false;
		if (w2 == null) {
			if (other.w2 != null)
				return false;
		} else if (!w2.equals(other.w2))
			return false;
		return true;
	}
	private String readAWord(DataInput arg0){
		StringBuffer s1 = new StringBuffer();
		try {
			char curChar = arg0.readChar();
			while (!Character.isWhitespace(curChar)){
				s1.append(curChar);
				curChar = arg0.readChar();
			}
			return s1.toString();
		}catch(IOException e){
			return null;
		}
	}
	@Override
	public void readFields(DataInput arg0) throws IOException {
		w1 = readAWord(arg0);
		w2 = readAWord(arg0);		
	}
	@Override
	public void write(DataOutput arg0) throws IOException {
		arg0.write(w1.getBytes());
		arg0.write(w2.getBytes());
	}
	@Override
	public int compareTo(Object o) {
		if (o != null){
			WordPair other = (WordPair) o;
			return this.w1.compareTo(other.w1)+ this.w2.compareTo(other.w2);
		} else {
			return 0;
		}
	}
	
}
