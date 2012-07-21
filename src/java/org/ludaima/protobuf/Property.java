package org.ludaima.protobuf;

/**
 * 属性对象配置类
 * @author yunai
 * @version Jul 14, 2012
 */
public class Property {
	/**
	 * 属性类型
	 */
	private String level;
	/**
	 * 属性数据类型
	 */
	private String type;
	/**
	 * 属性名
	 */
	private String name;
	
	public Property(String level, String type, String name) {
		this.level = level;
		this.type = type;
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
