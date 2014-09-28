package com.gs.login.message;

import com.game.mina.message.Message;

//玩家登陆
public class ResLoginMessage extends Message {
	private static final long serialVersionUID = 7128395488361657124L;
	//当前服务端版本(服务器版本和客户端版本不相同，等不能登录成功)
    public String serverVersion;
    //用户id
    public String uid;
    //是否登录成功
    public boolean isLoginSuccess;
    //是否有角色
    public boolean isHaveRole;

    public void write(java.io.DataOutput out) throws java.io.IOException {
        out.writeUTF(serverVersion == null ? "" : serverVersion);
        out.writeUTF(uid == null ? "" : uid);
        out.writeBoolean(isLoginSuccess);
        out.writeBoolean(isHaveRole);
    }

    public void read(java.io.DataInput inn) throws java.io.IOException {
        serverVersion = inn.readUTF();
        uid = inn.readUTF();
        isLoginSuccess = inn.readBoolean();
        isHaveRole = inn.readBoolean();
    }
}
