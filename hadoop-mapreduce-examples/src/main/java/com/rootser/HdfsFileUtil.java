package com.rootser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public interface HdfsFileUtil {
	/**
	 * opens a file in HDFS for (over)-writing
	 * will destroy existing file
	 * 
	 * @param fileName name of file to create or overwrite in HDFS 
	 * 
	 * @return - buffered writer that one can use to write data to HDFS
	 * 
	 * @throws IOException
	 */
	public BufferedWriter openHdfsBufferedWriter(String fileName) throws IOException;
	
	/**
	 * stores all lines in a file in a list of strings (in RAM)
	 * this method could potentially consume all available ram
	 * if used to read a large file.
	 * 
	 * @param fileName - name of file in HDFS to read
	 * 
	 * @return - list of strings where each string is a line
	 * of the file
	 * 
	 * @throws IOException
	 */
	public List<String> readLines(String fileName) throws IOException;
}
