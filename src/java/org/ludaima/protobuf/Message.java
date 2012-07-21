package org.ludaima.protobuf;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 消息对象配置类
 * @author yunai
 * @version Jul 14, 2012
 */
public class Message {
	/**
	 * 类名. 即消息名称
	 */
	private String name;
	/**
	 * 属性列表. <K, V> 代表<属性次序, 属性>
	 */
	private SortedMap<Integer, Property> propertyMap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortedMap<Integer, Property> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(SortedMap<Integer, Property> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public void addProperty(Integer index, Property property) {
		if (null == propertyMap) {
			propertyMap = new TreeMap<Integer, Property>();
		}
		propertyMap.put(index, property);
	}
}
