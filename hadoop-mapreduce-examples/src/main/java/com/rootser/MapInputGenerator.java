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
	
	private void createMatrixFile(String fileName) throws IOException{
		BufferedWriter br = util.openHdfsBufferedWriter(fileName);
		for (int i = 0; i < 1000; i++){
			for (int j = 0; j  < 1000; j++){
				br.write(rand.nextDouble() + " ");
			}
			br.write("\n");
		}
		br.close();
	}
	
	public void run() throws IOException{
		createMatrixFile("/tmp/matrix1.txt");
		createMatrixFile("/tmp/matrix2.txt");
	}

	public static void main(String[] args) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"HdfsAppContext.xml");
		
		MapInputGenerator mgen = (MapInputGenerator) context.getBean(com.rootser.MapInputGenerator.class);
		
		try {
			mgen.run();
		} catch(IOException e){
			System.out.println("add proper logging");
		}
		
		((ClassPathXmlApplicationContext) context).close();
	}
}
