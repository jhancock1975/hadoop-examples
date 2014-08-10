package com.rootser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class HdfsFileUtil {
	@Autowired
	Configuration configuration;
	/*public void setConfiguration(Configuration conf){
		this.configuration = conf;
	}
	public Configuration getConfiguration(){
		return this.configuration;
	}*/
	@Autowired
	URI uri;
	public void setUri(URI uri){
		this.uri = uri;
	}
	public URI getUri(){
		return this.uri;
	}
	@Autowired
	Path file;
	public Path getFile() {
		return file;
	}
	public void setFile(Path file) {
		this.file = file;
	}
	@Autowired
	FileSystem hdfs;
	public FileSystem getHdfs(){
		return this.hdfs;
	}
	public void setHdfs(FileSystem hdfs){
		this.hdfs = hdfs;
	}
	
	public BufferedWriter openBufferedWriter(String filePath) throws IOException{
		FileSystem hdfs = getHdfs();
		setFile(Path.mergePaths( getFile(), new Path(filePath)));
		if ( hdfs.exists( getFile() )) {
			hdfs.delete( getFile(), true ); 
			} 
		OutputStream os = hdfs.create( getFile());
		BufferedWriter br = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );
		return br;
	}
	
	public void close(BufferedWriter br) throws IOException{
		br.write("Hello World");
		br.close();
		hdfs.close();
	}
}
