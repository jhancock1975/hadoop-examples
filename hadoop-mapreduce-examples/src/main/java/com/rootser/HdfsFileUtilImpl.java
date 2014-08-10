package com.rootser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class HdfsFileUtilImpl implements HdfsFileUtil{
	@Autowired
	Configuration configuration;
	
	@Autowired
	URI uri;
	
	@Autowired
	Path file;
	
	@Autowired
	FileSystem hdfs;
	
	
	public BufferedWriter openHdfsBufferedWriter(String filePath) throws IOException{
		Path newfile = (Path.mergePaths( file, new Path(filePath)));
		if ( hdfs.exists( newfile)) {
			hdfs.delete( newfile, true ); 
			} 
		OutputStream os = hdfs.create( newfile);
		BufferedWriter br = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );
		return br;
	}
	@PreDestroy
	private void close() throws IOException{
		hdfs.close();
	}
	/**
	 * code copied from 
	 * http://blog.matthewrathbone.com/2013/12/28/Reading-data-from-HDFS-even-if-it-is-compressed.html
	 * hdfs is for storing big files
	 * this method reads the contents of a file into a list
	 * so if you try and read a really big file it is going to use 
	 * all your RAM.
	 * 
	 * @param location
	 * @param conf
	 * @return
	 * @throws IOException
	 */
	public List<String> readLines(String fileName) throws IOException{
		Path location = Path.mergePaths(file, new Path(fileName));
	    CompressionCodecFactory factory = new CompressionCodecFactory(configuration);
	    FileStatus[] items = hdfs.listStatus(location);
	    if (items == null) return new ArrayList<String>();
	    List<String> results = new ArrayList<String>();
	    for(FileStatus item: items) {

	      // ignoring files like _SUCCESS
	      if(item.getPath().getName().startsWith("_")) {
	        continue;
	      }

	      CompressionCodec codec = factory.getCodec(item.getPath());
	      InputStream stream = null;

	      // check if we have a compression codec we need to use
	      if (codec != null) {
	        stream = codec.createInputStream(hdfs.open(item.getPath()));
	      }
	      else {
	        stream = hdfs.open(item.getPath());
	      }

	      StringWriter writer = new StringWriter();
	      IOUtils.copy(stream, writer, "UTF-8");
	      String raw = writer.toString();
	      String[] resulting = raw.split("\n");
	      for(String str: resulting) {
	        results.add(str);
	      }
	    }
	    return results;
	  }
}
