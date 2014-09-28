package com.gs.login.message;
import com.game.mina.message.Message;

public class ReqLoginMessage extends Message {

	private static final long serialVersionUID = 3285775071230039591L;
	
	public MsgLoginKeyValue[] longins = null;

	public void write(java.io.DataOutput out) throws java.io.IOException {
		int len = 0;
		len = longins == null ? 0 : longins.length;
		out.writeShort(len);
		for (int i = 0; i < len; i++) {
			out.writeByte(longins[i] == null ? 0 : 1);
			if (longins[i] != null)
				longins[i].write(out);
		}
	}

	public void read(java.io.DataInput inn) throws java.io.IOException {
		int len = 0;
		int i = 0;
		len = inn.readShort();
		if (len > 0) {
			longins = new MsgLoginKeyValue[len];
			for (i = 0; i < len; i++) {
				if (inn.readByte() == 1) {
					longins[i] = new MsgLoginKeyValue();
					longins[i].read(inn);
				}
			}
		}
	}

}
