package org.ludaima.protobuf.base;

public class ByteArray {
	private byte[] hb;
	private int position;
	private int limit;

	private ByteArray(int limit) {
		this.limit = limit;
	}
	
	private ByteArray(byte[] hb, int position) {
		this.hb = hb;
		this.position = position;
		this.limit = hb.length;
	}
	
	public static ByteArray createNull(int limit) {
		return new ByteArray(limit);
	}
	
	public void create() {
		this.hb = new byte[this.limit];
	}
	
	public void incrLimit(int incr) {
		this.limit += incr;
	}

	public static ByteArray wrap(byte[] hb) {
		return new ByteArray(hb, 0);
	}

	public byte[] getHb() {
		return hb;
	}

	public int position() {
		return position;
	}
	
	public int limit() {
		return limit;
	}
	
	public byte getByte() {
		return hb[position++];
	}

	public void putByte(byte[] bytes) {
		for (byte b : bytes) {
			hb[position++] = b;
		}
	}
	
	public void putByte(byte b) {
		hb[position++] = b;
	}
	
	public char getChar() {
		return Bits.getChar(hb, nextGetIndex(2));
	}
	
	public void putChar(char ch) {
		Bits.putChar(hb, nextGetIndex(2), ch);
	}
	
	public short getShort() {
		return Bits.getShort(hb, nextGetIndex(2));
	}
	
	public void putShort(short val) {
		Bits.putShort(hb, nextGetIndex(2), val);
	}
	
	public int getInt() {
		return Bits.getInt(hb, nextGetIndex(4));
	}
	
	public void putInt(int val) {
		Bits.putInt(hb, nextGetIndex(4), val);
	}
	
	public long getLong() {
		return Bits.getLong(hb, nextGetIndex(8));
	}
	
	public void putLong(long val) {
		Bits.putLong(hb, nextGetIndex(8), val);
	}
	
	public float getFloat() {
		return Bits.getFloat(hb, nextGetIndex(4));
	}
	
	public void putFloat(float val) {
		Bits.putFloat(hb, nextGetIndex(4), val);
	}
	
	public double getDouble() {
		return Bits.getDouble(hb, nextGetIndex(8));
	}
	
	public void putDouble(double val) {
		Bits.putDouble(hb, nextGetIndex(8), val);
	}
	
	public String getString(short len) {
		return Bits.getString(hb, nextGetIndex(len), len);
	}
	
	public int nextGetIndex(int nb) {
		int p = position;
		position += nb;
		return p;
	}
	
	public int nextGetIndex(short nb) {
		int p = position;
		position += nb;
		return p;
	}
	
	public void reset() {
		this.position = 0;
	}
}
