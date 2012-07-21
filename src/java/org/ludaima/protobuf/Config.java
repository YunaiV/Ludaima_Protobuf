package org.ludaima.protobuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息所有配置
 * @author yunai
 * @version Jul 14, 2012
 */
public class Config {
	/**
	 * 生成代码所在包名
	 */
	private String packageName;
	/**
	 * 所有消息对象配置
	 */
	private List<Message> messages;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void addMessage(Message msg) {
		if (null == messages) {
			messages = new ArrayList<Message>();
		}
		messages.add(msg);
	}
}
