package com.rootser;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:HdfsAppContext.xml" })
public class HdfsFileUtilTest {
	@Autowired
	 HdfsFileUtil util;
	@Test
	public void writeTest() throws IOException{
		BufferedWriter br = util.openBufferedWriter("test.txt");
		String testStr = "hello much improved file system object." ;
		br.write(testStr);
		util.close(br);
		Path myfile = new Path("hdfs://oishii:9000/test.txt");
		List<String> results = readLines(myfile, new Configuration());
		assertTrue(results.get(0).equals(testStr));
	}
	/**
	 * code copied from 
	 * http://blog.matthewrathbone.com/2013/12/28/Reading-data-from-HDFS-even-if-it-is-compressed.html
	 * @param location
	 * @param conf
	 * @return
	 * @throws IOException
	 */
	public List<String> readLines(Path location, Configuration conf) throws IOException{
	    FileSystem fileSystem = FileSystem.get(location.toUri(), conf);
	    CompressionCodecFactory factory = new CompressionCodecFactory(conf);
	    FileStatus[] items = fileSystem.listStatus(location);
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
	        stream = codec.createInputStream(fileSystem.open(item.getPath()));
	      }
	      else {
	        stream = fileSystem.open(item.getPath());
	      }

	      StringWriter writer = new StringWriter();
	      IOUtils.copy(stream, writer, "UTF-8");
	      String raw = writer.toString();
	      String[] resulting = raw.split("\n");
	      for(String str: raw.split("\n")) {
	        results.add(str);
	      }
	    }
	    return results;
	  }
}
