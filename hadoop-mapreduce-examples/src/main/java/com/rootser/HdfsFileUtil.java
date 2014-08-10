package com.rootser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public interface HdfsFileUtil {
	public BufferedWriter openBufferedWriter(String fileName) throws IOException;
	public List<String> readLines(String fileName) throws IOException;
}
