package com.rootser.hadoop.book.examples;
import java.io.*;

import org.apache.hadoop.io.*;

public class TextIntWritablePair implements WritableComparable<TextIntWritablePair> {

  private Text first;
  private IntWritable second;
  
  public TextIntWritablePair() {
    set(new Text(), new IntWritable());
  }
  
  public TextIntWritablePair(String first, Integer second) {
    set(new Text(first), new IntWritable(second));
  }
  
  public TextIntWritablePair(Text first, IntWritable second) {
    set(first, second);
  }
  
  public void set(Text first, IntWritable second) {
    this.first = first;
    this.second = second;
  }
  
  public Text getFirst() {
    return first;
  }

  public IntWritable getSecond() {
    return second;
  }

  @Override
  public void write(DataOutput out) throws IOException {
    first.write(out);
    second.write(out);
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    first.readFields(in);
    second.readFields(in);
  }
  
  @Override
  public int hashCode() {
    return first.hashCode() * 163 + second.hashCode();
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof TextIntWritablePair) {
    	TextIntWritablePair tp = (TextIntWritablePair) o;
      return first.equals(tp.first) && second.equals(tp.second);
    }
    return false;
  }

  @Override
  public String toString() {
    return first + "\t" + second;
  }
  
  @Override
  public int compareTo(TextIntWritablePair tp) {
    int cmp = first.compareTo(tp.first);
    if (cmp != 0) {
      return cmp;
    }
    return second.compareTo(tp.second);
  }


}
