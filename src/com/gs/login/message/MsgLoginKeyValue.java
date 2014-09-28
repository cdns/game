package com.gs.login.message;

import com.game.IMsgBody;

public class MsgLoginKeyValue implements IMsgBody {
	private static final long serialVersionUID = 8253624902755077880L;
	public String key;
	public String value;

	public void write(java.io.DataOutput out) throws java.io.IOException {
		out.writeUTF(key == null ? "" : key);
		out.writeUTF(value == null ? "" : value);
	}

	public void read(java.io.DataInput inn) throws java.io.IOException {
		key = inn.readUTF();
		value = inn.readUTF();
	}
}