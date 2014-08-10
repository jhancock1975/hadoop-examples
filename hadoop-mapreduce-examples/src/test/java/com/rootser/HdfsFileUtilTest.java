package com.rootser;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

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
		br.close();
		List<String> results = util.readLines("test.txt");
		util.close(br);
		assertTrue(results.get(0).equals(testStr));
	}
}
