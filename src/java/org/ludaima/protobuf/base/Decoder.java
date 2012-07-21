package org.ludaima.protobuf.base;

import java.util.ArrayList;
import java.util.List;

public abstract class Decoder {
	abstract Message decode(ByteArray byteArray);
	
	List<Byte> getByteList(ByteArray byteArray) {
		short size = byteArray.getShort();
		List<Byte> list = new ArrayList<Byte>(size);
		for (; size > 0; size--) {
			list.add(byteArray.getByte());
		}
		return list;
	}
	
	List<Character> getCharList(ByteArray byteArray) {
		short size = byteArray.getShort();
		List<Character> list = new ArrayList<Character>(size);
		for (; size > 0; size--) {
			list.add(byteArray.getChar());
		}
		return list;
	}
	
	List<Short> getShortList(ByteArray byteArray) {
		short size = byteArray.getShort();
		List<Short> list = new ArrayList<Short>(size);
		for(; size > 0; size--) {
			list.add(byteArray.getShort());
		}
		return list;
	}
	
	List<Integer> getIntList(ByteArray byteArray) {
		short size = byteArray.getShort();
		List<Integer> list = new ArrayList<Integer>(size);
		for (; size > 0; size--) {
			list.add(byteArray.getInt());
		}
		return list;
	}
	
	List<Long> getLongList(ByteArray byteArray) {
		short size = byteArray.getShort();
		List<Long> list = new ArrayList<Long>(size);
		for (; size > 0; size--) {
			list.add(byteArray.getLong());
		}
		return list;
	}
	
	List<Float> getFloatList(ByteArray byteArray) {
		short size = byteArray.getShort();
		List<Float> list = new ArrayList<Float>(size);
		for (; size > 0; size--) {
			list.add(byteArray.getFloat());
		}
		return list;
	}
	
	List<Double> getDoubleList(ByteArray byteArray) {
		short size = byteArray.getShort();
		List<Double> list = new ArrayList<Double>(size);
		for(; size > 0; size--) {
			list.add(byteArray.getDouble());
		}
		return list;
	}
	
	String getString(ByteArray byteArray) {
		return byteArray.getString(byteArray.getShort());
	}
	
	List<String> getStringList(ByteArray byteArray) {
		short size = byteArray.getShort();
		List<String> list = new ArrayList<String>(size);
		for (; size > 0; size--) {
			list.add(getString(byteArray));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	<T extends Message> T getMessage(Decoder decoder, ByteArray byteArray, Class<T> requiredClass) {
		return (T) decoder.decode(byteArray);
	}
	
	<T extends Message> List<T> getMessageList(Decoder decoder, ByteArray byteArray, Class<T> requiredClass) {
		short size = byteArray.getShort();
		List<T> list = new ArrayList<T>(size);
		for (; size > 0; size--) {
			list.add(getMessage(decoder, byteArray, requiredClass));
		}
		return list;
	}
	
}
