package com.rootser;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
	static String hdfsUrlStr;
	@BeforeClass
	/**
	 * this keeps the test flexible
	 * so if we run it on another hdfs with
	 * a different url it will still work
	 * 
	 * @throws IOException
	 */
	public static void setUp() throws IOException{
		InputStream inputStream = HdfsFileUtilTest.class.getClassLoader().getResourceAsStream("hdfs.properties");
		Properties prop = new Properties();
		prop.load(inputStream);
		hdfsUrlStr = prop.getProperty("HDFS_URL");
	}
	private static String test1FileName = "/tmp/test.txt";
	@Test
	public void writeTest() throws IOException{
		
		BufferedWriter br = util.openBufferedWriter(test2FileName);
		String testStr = "hello much improved file system object." ;
		br.write(testStr);
		br.close();
		Path myfile = new Path(hdfsUrlStr + test2FileName);
		List<String> results = readLines(myfile, new Configuration());
		assertTrue(results.get(0).equals(testStr));
	}
	
	/**
	 * copied from
	 * http://blog.matthewrathbone.com/2013/12/28/Reading-data-from-HDFS-even-if-it-is-compressed.htmll
	 * need separate logic for testing util, so we're not hiding bugs
	 * 
	 * @param location
	 * @param conf
	 * @return
	 * @throws IOException
	 */
	public List<String> readLines(Path location, Configuration conf) throws IOException {
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
	      for(String str: resulting) {
	        results.add(str);
	      }
	    }
	    return results;
	  }
	static String test2FileName = "test2.txt";
	static String test2Str = "hello world";
	@Test
	public void readTest() throws IOException, URISyntaxException{
		
		writeToFile(test2FileName, test2Str);
		List<String> actual = util.readLines(test2FileName);
		assertTrue(actual.get(0).equals(test2Str));
	}
	
	private void writeToFile(String fileName, String textToWrite) throws IOException, URISyntaxException{
		Configuration configuration = new Configuration();
		FileSystem hdfs = FileSystem.get( new URI( hdfsUrlStr ), configuration );
		Path file = new Path(hdfsUrlStr + fileName);
		if ( hdfs.exists( file )) {
			hdfs.delete( file, true ); 
			} 
		OutputStream os = hdfs.create( file);
		BufferedWriter br = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );
		br.write(textToWrite);
		br.close();
	}
	
	@AfterClass
	public static void cleanUp() throws IOException, URISyntaxException{
		deleteFile(test2FileName);
		deleteFile(test1FileName);
	}
	private static void deleteFile(String fileName)  throws IOException, URISyntaxException{
		Configuration configuration = new Configuration();
		FileSystem hdfs = FileSystem.get( new URI( hdfsUrlStr ), configuration );
		Path file = new Path(hdfsUrlStr + fileName);
		if ( hdfs.exists( file )) {
			hdfs.delete( file, true ); 
			}
	}
}
