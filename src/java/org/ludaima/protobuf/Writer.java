package org.ludaima.protobuf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Writer {
	public static void write(Config config, String out) throws IOException {
		// 新建包
		String packagePath = out + config.getPackageName().replace(".", "/");
		File packageFile = new File(packagePath);
		if (!packageFile.exists()) {
			packageFile.mkdirs();
		}
		
		// 生成类
		for (Message clazz : config.getMessages()) {
			writeMessage(config, clazz, packagePath);
			writeMessageDecoder(config, clazz, packagePath);
			writeMessageEncoder(config, clazz, packagePath);
		}
	}
	
	public static void writeMessage(Config config, Message clazz, String packagePath) throws IOException {
		// 创建文件
		String javaPath = packagePath + "/" + clazz.getName() + ".java";
		File javaFile = new File(javaPath);
		javaFile.createNewFile();
		
		// 写文件
		OutputStream os = new FileOutputStream(javaFile);	
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
		writer.write(Template.formatClass(config, clazz));
		writer.flush();
		
		// 关闭文件流
		Utils.closeIo(writer, os);
	}
	
	public static void writeMessageDecoder(Config config, Message clazz, String packagePath) throws IOException {
		// 创建文件
		String javaPath = packagePath + "/" + clazz.getName() + "Decoder.java";
		File javaFile = new File(javaPath);
		javaFile.createNewFile();
		
		// 写文件
		OutputStream os = new FileOutputStream(javaFile);	
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
		writer.write(Template.formatDecoderClass(config, clazz));
		writer.flush();
		
		// 关闭文件流
		Utils.closeIo(writer, os);
	}
	
	public static void writeMessageEncoder(Config config, Message clazz, String packagePath) throws IOException {
		// 创建文件
		String javaPath = packagePath + "/" + clazz.getName() + "Encoder.java";
		File javaFile = new File(javaPath);
		javaFile.createNewFile();
		
		// 写文件
		OutputStream os = new FileOutputStream(javaFile);	
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
		writer.write(Template.formatEncoderClass(config, clazz));
		writer.flush();
		
		// 关闭文件流
		Utils.closeIo(writer, os);
	}
	
}
