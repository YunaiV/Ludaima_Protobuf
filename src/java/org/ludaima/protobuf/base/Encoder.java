package org.ludaima.protobuf.base;

import java.util.List;


// TODO System.arraycopy

public abstract class Encoder {
	abstract ByteArray encode(Message message);
	
	void convertByte(ByteArray byteArray, List<Byte> list) {
		byteArray.incrLimit(list.size() + 2);
	}
	
	void putByte(ByteArray byteArray, List<Byte> list) {
		byteArray.putShort((short) list.size());
		for (Byte b : list) {
			byteArray.putByte(b);
		}
	}
	
	void convertChar(ByteArray byteArray, List<Character> list) {
		byteArray.incrLimit(list.size() * 2 + 2);
	}
	
	void putChar(ByteArray byteArray, List<Character> list) {
		byteArray.putShort((short) list.size());
		for (char ch : list) {
			byteArray.putChar(ch);
		}
	}
	
	void convertShort(ByteArray byteArray, List<Short> list) {
		byteArray.incrLimit(list.size() * 2 + 2);
	}
	
	void putShort(ByteArray byteArray, List<Short> list) {
		byteArray.putShort((short) list.size());
		for (short sh : list) {
			byteArray.putShort(sh);
		}
	}
	
	void convertInt(ByteArray byteArray, List<Integer> list) {
		byteArray.incrLimit(list.size() * 4 + 2);
	}
	
	void putInt(ByteArray byteArray, List<Integer> list) {
		byteArray.putShort((short) list.size());
		for (int i : list) {
			byteArray.putInt(i);
		}
	}
	
	void convertLong(ByteArray byteArray, List<Long> list) {
		byteArray.incrLimit(list.size() * 8 + 2);
	}
	
	void putLong(ByteArray byteArray, List<Long> list) {
		byteArray.putShort((short) list.size());
		for (long l : list) {
			byteArray.putLong(l);
		}
	}
	
	void convertFloat(ByteArray byteArray, List<Float> list) {
		byteArray.incrLimit(list.size() * 4 + 2);
	}
	
	void putFloat(ByteArray byteArray, List<Float> list) {
		byteArray.putShort((short) list.size());
		for (float f : list) {
			byteArray.putFloat(f);
		}
	}
	
	void convertDouble(ByteArray byteArray, List<Double> list) {
		byteArray.incrLimit(list.size() * 8 + 2);
	}
	
	void putDouble(ByteArray byteArray, List<Double> list) {
		byteArray.putShort((short) list.size());
		for (double d : list) {
			byteArray.putDouble(d);
		}
	}
	
	byte[] convertString(ByteArray byteArray, String str) {
		byte[] bytes = Bits.convertString(str);
		byteArray.incrLimit(bytes.length + 2);
		return bytes;
	}
	
	byte[][] convertStringList(ByteArray byteArray, List<String> strs) {
		byteArray.incrLimit(2);
		byte[][] bytes = new byte[strs.size()][];
		short index = 0;
		for (String str : strs) {
			bytes[index++] = convertString(byteArray, str);
		}
		return bytes;
	}
	
	void putString(ByteArray byteArray, byte[] bytes) {
		byteArray.putShort((short) bytes.length);
		byteArray.putByte(bytes);
	}
	
	void putStringList(ByteArray byteArray, byte[][] bytes) {
		byteArray.putShort((short) bytes.length);
		for (byte[] bytez : bytes) {
			putString(byteArray, bytez);
		}
	}
	
	byte[] convertMessage(ByteArray byteArray, Encoder encoder, Message message) {
		ByteArray newbyteArray = encoder.encode(message);
		byteArray.incrLimit(newbyteArray.limit());
		return newbyteArray.getHb();
	}
	
	<T extends Message> byte[][] convertMessageList(ByteArray byteArray, Encoder encoder, List<T> messages) {
		byteArray.incrLimit(2);
		byte[][] bytes = new byte[messages.size()][];
		short index = 0;
		for (Message message : messages) {
			bytes[index++] = convertMessage(byteArray, encoder, message);
		}
		return bytes;
	}
	
	void putMessage(ByteArray byteArray, byte[] bytes) {
		byteArray.putByte(bytes);
	}
	
	void putMessageList(ByteArray byteArray, byte[][] bytes) {
		byteArray.putShort((short) bytes.length);
		for (byte[] bytez : bytes) {
			putMessage(byteArray, bytez);
		}
	}
}