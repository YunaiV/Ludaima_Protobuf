package org.ludaima.protobuf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {
	
	private static final Pattern PATTERN_CLASS = Pattern.compile("message[ ]+([A-Za-z]+)[ ]+\\{");
	private static final Pattern PATTERN_PROPERTY = Pattern.compile("(.+)[ ]+(.+)[ ]+([a-zA-Z]+)[ ]*=[ ]*(\\d+)[ ]*;");
	private static final Pattern PATTERN_PACKAGE = Pattern.compile("option[ ]+package[ ]*=[ ]*\"([A-Za-z0-9\\.]+)\"[ ]*;");
	private static final Pattern PATTERN_CLASS_END = Pattern.compile("[ ]*}");
	
	public static Config read(String path) throws IOException {
		Config config = new Config();
		InputStream in = null;
		BufferedReader reader = null;
		try {
			in = Reader.class.getResourceAsStream(path);
			reader = new BufferedReader(new InputStreamReader(in));
			String s = null;
			Message message = null;
			while ((s = reader.readLine()) != null) {
				s = s.replaceAll("	", " ").trim();
				// 匹配[一个消息对象的类名]
				Matcher m = PATTERN_CLASS.matcher(s);
				if (m.matches()) {
					message = new Message();
					message.setName(m.group(1));
					continue;
				}
				// 匹配[一个消息对象的属性]
				m = PATTERN_PROPERTY.matcher(s);
				if (m.matches()) {
					Property property = new Property(m.group(1), m.group(2), m.group(3));
					message.addProperty(Integer.valueOf(m.group(4)), property);
					continue;
				}
				// 匹配[一个消息对象配置结束]
				m = PATTERN_CLASS_END.matcher(s);
				if (m.matches()) {
					config.addMessage(message);
					message = null;
					continue;
				}
				// 匹配[配置包名]
				if (config.getPackageName() == null) {
					m = PATTERN_PACKAGE.matcher(s);
					if (m.matches()) {
						config.setPackageName(m.group(1));
						continue;
					}
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			Utils.closeIo(reader, in);
		}
		return config;
	}
}
