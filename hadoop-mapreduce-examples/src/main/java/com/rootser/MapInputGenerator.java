package com.rootser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class MapInputGenerator {
		 
	@Autowired
	 HdfsFileUtil util;
	
	@Autowired
	Random rand;
	
	public void run() throws IOException{
		BufferedWriter br = util.openBufferedWriter("test.txt");
		for (int i = 0; i < Integer.MAX_VALUE; i++){
			br.write(rand.nextInt());
		}
		br.close();
	}

	public static void main(String[] args) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"HdfsAppContext.xml");
		
		MapInputGenerator mgen = (MapInputGenerator) context.getBean(com.rootser.MapInputGenerator.class);
		mgen.run();
		
		((ClassPathXmlApplicationContext) context).close();
	}
}
