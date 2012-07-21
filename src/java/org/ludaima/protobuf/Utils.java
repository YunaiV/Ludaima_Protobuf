package org.ludaima.protobuf;

import java.io.Closeable;
import java.io.IOException;

public class Utils {
	
	public static final int LEVEL_SIMPLE = 1;
	public static final int LEVEL_LIST = 2;
	
	/**
	 * 基本数据类型. char/byte/short/int/long/float/double
	 */
	public static final int DATA_TYPE_SIMPLE = 1;
	/**
	 * 字符串数据类型. String
	 */
	public static final int DATA_TYPE_STRING = 2;
	/**
	 * 消息数据类型(即, 消息类型). Message接口的实现类
	 */
	public static final int DATA_TYPE_MESSAGE = 3;
	
	/**
	 * 根据type转化为其在JAVA里的基本数据类型
	 * @param type 配置文件里定义的type
	 * @return
	 */
	public static String convertDataType(String type) {
		return "byte".equals(type) ? "byte" :
			"char".equals(type) ? "char" :
			"short".equals(type) ? "short" :
			"int".equals(type) ? "int" :
			"long".equals(type) ? "long" :
			"float".equals(type) ? "float" :
			"double".equals(type) ? "double" :
			"string".equals(type) ? "String" : type;
	}
	
	/**
	 * 根据type转化为其在JAVA里的对象数据类型
	 * @param type 配置文件里的type
	 * @return
	 */
	public static String convertDataObject(String type) {
		return "byte".equals(type) ? "Byte" :
			"char".equals(type) ? "Character" :
			"short".equals(type) ? "Short" :
			"int".equals(type) ? "Integer" :
			"long".equals(type) ? "Long" :
			"float".equals(type) ? "Float" :
			"double".equals(type) ? "Double" :
			"string".equals(type) ? "String" : type;
	}
	
	/**
	 * 根据type获得其字节长度
	 * @param type
	 * @return
	 */
	public static int getTypeLength(String type) {
		return "byte".equals(type) ? 1 :
				"char".equals(type) ? 2 :
				"short".equals(type) ? 2 :
				"int".equals(type) ? 4 :
				"long".equals(type) ? 8 :
				"float".equals(type) ? 4 :
				"double".equals(type) ? 8 : -1;
	}
	
	/**
	 * 根据Property的数据类型返回它的数据定义编号
	 * @param type
	 * @return
	 */
	public static int defineDataType(String type) {
		return "byte".equals(type) ? DATA_TYPE_SIMPLE :
			"short".equals(type) ? DATA_TYPE_SIMPLE :
			"char".equals(type) ? DATA_TYPE_SIMPLE :
 			"int".equals(type) ? DATA_TYPE_SIMPLE :
			"long".equals(type) ? DATA_TYPE_SIMPLE :
			"float".equals(type) ? DATA_TYPE_SIMPLE :
			"double".equals(type) ? DATA_TYPE_SIMPLE :
			"string".equals(type) ? DATA_TYPE_STRING : DATA_TYPE_MESSAGE;
	}
	
	/**
	 * 根据Property的属性类型返回它的类型定义编号
	 * @param level
	 * @return
	 */
	public static int defineLevelType(String level) {
		return "required".equals(level) ? LEVEL_SIMPLE :
			"repeated".equals(level) ? LEVEL_LIST : -1;
	}
	
	/**
	 * 将字符串首字母变成大写<br />
	 * ×× 请保证首字母在['a', 'z']内
	 * @param str
	 * @return
	 */
	public static String convertFirst2Big(String str) {
		byte[] bytes = str.getBytes();
		bytes[0] = (byte)(bytes[0] - 'a' + 'A');;
		return new String(bytes);
	}
	
	/**
	 * 将字符串首字母变成小写<br />
	 * ×× 请保证首字母在['A', 'Z']内
	 * @param str
	 * @return
	 */
	public static String convertFirst2Small(String str) {
		byte[] bytes = str.getBytes();
		bytes[0] = (byte)(bytes[0] + 'a' - 'A');;
		return new String(bytes);
	}
	
	/**
	 * 关闭读写流
	 * @param closeable
	 * @throws IOException
	 */
	public static void closeIo(Closeable closeable) throws IOException {
		if (null != closeable) {
			closeable.close();
			closeable = null;
		}
	}
	
	/**
	 * 关闭一堆读写流
	 * @param closeables
	 * @throws IOException
	 */
	public static void closeIo(Closeable... closeables) throws IOException {
		for (Closeable closeable : closeables) {
			closeIo(closeable);
		}
	}
}
