package org.ludaima.protobuf;

public class Template {
	public static final String LINE_SEPARATOR = "\n";
	
	/**
	 * 消息对象基本类的模板
	 */
	public static final String CLASS_MESSAGE_BASE = "package #{package};" + LINE_SEPARATOR
			+ "#{REGION_IMPORT}"
			+ LINE_SEPARATOR
			+ "public class #{class} implements Message {" + LINE_SEPARATOR
			+ "#{REGION_PROPERTY}"
			+ "#{REGION_PROPERTY_SETTING_GETTING}"
			+ "}";
	/**
	 * 消息对象的属性
	 */
	public static final String CLASS_MESSAGE_PROPERtY = "	private #{type} #{property};" + LINE_SEPARATOR;
	/**
	 * 消息对象的属性的SETTING/GETTING方法
	 */
	public static final String CLASS_MESSAGE_SETTING_GETTING = LINE_SEPARATOR
			+ "	public #{type} get#{Property}() {" + LINE_SEPARATOR
			+ "		return #{property};" + LINE_SEPARATOR
			+ "	}" + LINE_SEPARATOR
			+ LINE_SEPARATOR
			+ "	public void set#{Property}(#{type} #{property}) {" + LINE_SEPARATOR
			+ "		this.#{property} = #{property};" + LINE_SEPARATOR
			+ "	}" + LINE_SEPARATOR;
	
	/**
	 * 生成消息对象的代码
	 * @param config 配置
	 * @param clazz 消息对象配置
	 * @return
	 */
	public static String formatClass(Config config, Message clazz) {
		String regionImport = "";
		String regionProperty = "";
		String regionPropertySettingGetting = "";
		boolean importList = false;
		for (Property property : clazz.getPropertyMap().values()) {
			int levelType = Utils.defineLevelType(property.getLevel());
			String typeStr = null;
			if (levelType == Utils.LEVEL_LIST) {
				importList = true;
				typeStr = "List<" + Utils.convertDataObject(property.getType()) + ">";
			} else {
				typeStr = Utils.convertDataType(property.getType());
			}
			regionProperty += CLASS_MESSAGE_PROPERtY.replace("#{type}", typeStr)
					.replace("#{property}", property.getName());
			regionPropertySettingGetting += CLASS_MESSAGE_SETTING_GETTING.replace("#{type}", typeStr)
					.replace("#{property}", property.getName())
					.replace("#{Property}", Utils.convertFirst2Big(property.getName()));
		}
		if (importList) {
			regionImport += "import java.util.List;";
		}
		if (!"".equals(regionImport)) {
			regionImport = LINE_SEPARATOR + regionImport + LINE_SEPARATOR;
		}
		return CLASS_MESSAGE_BASE.replace("#{package}", config.getPackageName())
				.replace("#{REGION_IMPORT}", regionImport)
				.replace("#{class}", clazz.getName())
				.replace("#{REGION_PROPERTY}", regionProperty)
				.replace("#{REGION_PROPERTY_SETTING_GETTING}", regionPropertySettingGetting);
	}
	
	/**
	 * 消息对象基本Decoder类的模板
	 */
	public static final String CLASS_MESSAGE_DECODER_BASE = "package #{package};" + LINE_SEPARATOR
			+ "#{REGION_IMPORT}"
			+ LINE_SEPARATOR
			+ "public class #{class}Decoder extends Decoder {" + LINE_SEPARATOR
			+ "	private static #{class}Decoder decoder = new #{class}Decoder();" + LINE_SEPARATOR
			+ LINE_SEPARATOR
			+ "	public static #{class}Decoder getInstance() {" + LINE_SEPARATOR
			+ "		return decoder;" + LINE_SEPARATOR
			+ "	}" + LINE_SEPARATOR
			+ LINE_SEPARATOR
			+ "	public #{class} decode(ByteArray byteArray) {" + LINE_SEPARATOR
			+ "		#{class} #{clazz} = new #{class}();" + LINE_SEPARATOR
			+ "#{REGION_DECODE}"
			+ "		return #{clazz};" + LINE_SEPARATOR
			+ "	}" + LINE_SEPARATOR
			+ "}";
	
	/**
	 * 消息对象基本Decoder类的decoder模板(基础类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_DECODER_TYPE_1_LEVEL_1 = "		#{clazz}.set#{Property}(byteArray.get#{Type}());" + LINE_SEPARATOR;
	
	/**
	 * 消息对象基本Decoder类的decoder模板(基础类型 + 数组级别[repeated])
	 */
	public static final String CLASS_MESSAGE_DECODER_TYPE_1_LEVEL_2 = "		#{clazz}.set#{Property}(get#{Type}List(byteArray));" + LINE_SEPARATOR;
	
	/**
	 * 消息对象基本Decoder类的decoder模板(字符串类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_DECODER_TYPE_2_LEVEL_1 = "		#{clazz}.set#{Property}(getString(byteArray));" + LINE_SEPARATOR;
	
	/**
	 * 消息对象基本Decoder类的decoder模板(字符串类型+数组级别[required])
	 */
	public static final String CLASS_MESSAGE_DECODER_TYPE_2_LEVEL_2 = "		#{clazz}.set#{Property}(getStringList(byteArray));" + LINE_SEPARATOR;
	
	/**
	 * 消息对象基本Decoder类的decoder模板(Message类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_DECODER_TYPE_3_LEVEL_1 = "		#{clazz}.set#{Property}((getMessage(#{type}Decoder.getInstance(), byteArray, #{type}.class)));" + LINE_SEPARATOR;
	
	/**
	 * 消息对象基本Decoder类的decoder模板(Message类型 + 数组级别[required])
	 */
	public static final String CLASS_MESSAGE_DECODER_TYPE_3_LEVEL_2 = "		#{clazz}.set#{Property}(getMessageList(#{type}Decoder.getInstance(), byteArray, #{type}.class));" + LINE_SEPARATOR;
	
	public static String formatDecoderClass(Config config, Message clazz) {
		String regionDecode = "";
		for (Property property : clazz.getPropertyMap().values()) {
			int dataType = Utils.defineDataType(property.getType());
			int levelType = Utils.defineLevelType(property.getLevel());
			if (dataType == Utils.DATA_TYPE_SIMPLE) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					regionDecode += CLASS_MESSAGE_DECODER_TYPE_1_LEVEL_1.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{Type}", Utils.convertFirst2Big(property.getType()));
				} else if (levelType == Utils.LEVEL_LIST) {
					regionDecode += CLASS_MESSAGE_DECODER_TYPE_1_LEVEL_2.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{Type}", Utils.convertFirst2Big(property.getType()));
				}
			} else if (dataType == Utils.DATA_TYPE_STRING) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					regionDecode += CLASS_MESSAGE_DECODER_TYPE_2_LEVEL_1.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()));
				} else if (levelType == Utils.LEVEL_LIST) {
					regionDecode += CLASS_MESSAGE_DECODER_TYPE_2_LEVEL_2.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()));
				}
			} else if (dataType == Utils.DATA_TYPE_MESSAGE) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					regionDecode += CLASS_MESSAGE_DECODER_TYPE_3_LEVEL_1.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{type}", property.getType())
							.replace("#{class}", clazz.getName());
				} else if (levelType == Utils.LEVEL_LIST) {
					regionDecode += CLASS_MESSAGE_DECODER_TYPE_3_LEVEL_2.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{type}", property.getType())
							.replace("#{class}", clazz.getName());
				}
			}
		}
		return CLASS_MESSAGE_DECODER_BASE.replace("#{package}", config.getPackageName())
				.replace("#{REGION_IMPORT}", "")
				.replace("#{REGION_DECODE}", regionDecode)
				.replace("#{class}", clazz.getName())
				.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()));
	}
	
	/**
	 * 消息对象基本Encoder类的模板
	 */
	public static final String CLASS_MESSAGE_ENCODER_BASE = "package #{package};" + LINE_SEPARATOR
			+ "#{REGION_IMPORT}"
			+ LINE_SEPARATOR
			+ "public class #{class}Encoder extends Encoder {" + LINE_SEPARATOR
			+ "	private static #{class}Encoder encoder = new #{class}Encoder();" + LINE_SEPARATOR
			+ LINE_SEPARATOR
			+ "	public static #{class}Encoder getInstance() {" + LINE_SEPARATOR
			+ "		return encoder;" + LINE_SEPARATOR
			+ "	}" + LINE_SEPARATOR
			+ LINE_SEPARATOR
			+ "	public ByteArray encode(Message message) {" + LINE_SEPARATOR
			+ "		#{class} #{clazz} = (#{class}) message;" + LINE_SEPARATOR
			+ "		ByteArray byteArray = ByteArray.createNull(#{msgLength});" + LINE_SEPARATOR
			+ "#{REGION_ENCODE_LIMIT}"
			+ "		byteArray.create();" + LINE_SEPARATOR
			+ "#{REGION_ENCODE}"
			+ "		return byteArray;" + LINE_SEPARATOR
			+ "	}" + LINE_SEPARATOR
			+ "}";
	
	/**
	 * 消息对象基本Encoder类的encode模板的求长度(基础类型 + 数组级别[repeated])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_1_LEVEL_2_LENGTH = "		convert#{Type}(byteArray, #{clazz}.get#{Property}());" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板的求长度(字符串类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_2_LEVEL_1_LENGTH = "		byte[] #{property}Bytes = convertString(byteArray, #{clazz}.get#{Property}());" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板的求长度(字符串类型 + 数组级别[repeated])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_2_LEVEL_2_LENGTH = "		byte[][] #{property}Bytes = convertStringList(byteArray, #{clazz}.get#{Property}());" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板的求长度(Message类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_3_LEVEL_1_LENGTH = "		byte[] #{property}Bytes = convertMessage(byteArray, #{type}Encoder.getInstance(), #{clazz}.get#{Property}());" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板的求长度(Message类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_3_LEVEL_2_LENGTH = "		byte[][] #{property}Bytes = convertMessageList(byteArray, #{type}Encoder.getInstance(), #{clazz}.get#{Property}());" + LINE_SEPARATOR;

	/**
	 * 消息对象基本Encoder类的encode模板(基础类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_1_LEVEL_1 = "		byteArray.put#{Type}(#{clazz}.get#{Property}());" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板(基础类型 + 数组级别[repeated])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_1_LEVEL_2 = "		put#{Type}(byteArray, #{clazz}.get#{Property}());" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板(字符串类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_2_LEVEL_1 = "		putString(byteArray, #{property}Bytes);" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板(字符串类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_2_LEVEL_2 = "		putStringList(byteArray, #{property}Bytes);" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板(Message类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_3_LEVEL_1 = "		putMessage(byteArray, #{property}Bytes);" + LINE_SEPARATOR;
	/**
	 * 消息对象基本Encoder类的encode模板(Message类型 + 普通级别[required])
	 */
	public static final String CLASS_MESSAGE_ENCODER_TYPE_3_LEVEL_2 = "		putMessageList(byteArray, #{property}Bytes);" + LINE_SEPARATOR;
	
	public static String formatEncoderClass(Config config, Message clazz) {
		// 计算长度
		String regionEncodeLimit = "";
		short msgLength = 0; // 基础类型 + 普通级别[required] 的所有数据长度
		for (Property property : clazz.getPropertyMap().values()) {
			int dataType = Utils.defineDataType(property.getType());
			int levelType = Utils.defineLevelType(property.getLevel());
			if (dataType == Utils.DATA_TYPE_SIMPLE) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					msgLength += Utils.getTypeLength(property.getType());
				} else if (levelType == Utils.LEVEL_LIST) {
					regionEncodeLimit += CLASS_MESSAGE_ENCODER_TYPE_1_LEVEL_2_LENGTH.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{Type}", Utils.convertFirst2Big(property.getType()));
				}
			} else if (dataType == Utils.DATA_TYPE_STRING) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					regionEncodeLimit += CLASS_MESSAGE_ENCODER_TYPE_2_LEVEL_1_LENGTH.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{property}", property.getName());;
				} else if (levelType == Utils.LEVEL_LIST) {
					regionEncodeLimit += CLASS_MESSAGE_ENCODER_TYPE_2_LEVEL_2_LENGTH.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{property}", property.getName());;
				}
			} else if (dataType == Utils.DATA_TYPE_MESSAGE) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					regionEncodeLimit += CLASS_MESSAGE_ENCODER_TYPE_3_LEVEL_1_LENGTH.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{property}", property.getName())
							.replace("#{type}", property.getType());
				} else if (levelType == Utils.LEVEL_LIST) {
					regionEncodeLimit += CLASS_MESSAGE_ENCODER_TYPE_3_LEVEL_2_LENGTH.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{property}", property.getName())
							.replace("#{type}", property.getType());
				}
			}
		}
		
		// 编码对象
		String regionEncode = "";
		for (Property property : clazz.getPropertyMap().values()) {
			int dataType = Utils.defineDataType(property.getType());
			int levelType = Utils.defineLevelType(property.getLevel());
			if (dataType == Utils.DATA_TYPE_SIMPLE) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					regionEncode += CLASS_MESSAGE_ENCODER_TYPE_1_LEVEL_1.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{Type}", Utils.convertFirst2Big(property.getType()));
				} else if (levelType == Utils.LEVEL_LIST) {
					regionEncode += CLASS_MESSAGE_ENCODER_TYPE_1_LEVEL_2.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{Property}", Utils.convertFirst2Big(property.getName()))
							.replace("#{Type}", Utils.convertFirst2Big(property.getType()));
				}
			} else if (dataType == Utils.DATA_TYPE_STRING) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					regionEncode += CLASS_MESSAGE_ENCODER_TYPE_2_LEVEL_1.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{property}", property.getName());
				} else if (levelType == Utils.LEVEL_LIST) {
					regionEncode += CLASS_MESSAGE_ENCODER_TYPE_2_LEVEL_2.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{property}", property.getName());
				}
			} else if (dataType == Utils.DATA_TYPE_MESSAGE) {
				if (levelType == Utils.LEVEL_SIMPLE) {
					regionEncode += CLASS_MESSAGE_ENCODER_TYPE_3_LEVEL_1.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{property}", property.getName());
				} else if (levelType == Utils.LEVEL_LIST) {
					regionEncode += CLASS_MESSAGE_ENCODER_TYPE_3_LEVEL_2.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()))
							.replace("#{property}", property.getName());
				}
			}
		}
		return CLASS_MESSAGE_ENCODER_BASE.replace("#{package}", config.getPackageName())
				.replace("#{REGION_ENCODE_LIMIT}", regionEncodeLimit)
				.replace("#{msgLength}", String.valueOf(msgLength))
				.replace("#{REGION_IMPORT}", "")
				.replace("#{REGION_ENCODE}", regionEncode)
				.replace("#{class}", clazz.getName())
				.replace("#{clazz}", Utils.convertFirst2Small(clazz.getName()));
	}
}
