package com.game.mina.code;

/**
 * 
 * @ClassName: DataPack 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:22:28 
 * @Description: 自定义协议包
 *
 */
public class DataPack implements Cloneable, java.io.Serializable{
	private static final long serialVersionUID = 983368647700334467L;
	private short sid;//
	private int msgid;//消息ID
	private long sessionId;//session id
	private String userid;  //用户ID
	private String playerid;//玩家ID
	private String[] players;//玩家ID列表
	private boolean iszip;// 是否需要压缩
	private byte[] msgBody;//消息体
	private byte serialize;

	public short getSid() {
		return sid;
	}

	public void setSid(short sid) {
		this.sid = sid;
	}

	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPlayerid() {
		return playerid;
	}

	public void setPlayerid(String playerid) {
		this.playerid = playerid;
	}

	public String[] getPlayers() {
		return players;
	}

	public void setPlayers(String[] players) {
		this.players = players;
	}

	public boolean isIszip() {
		return iszip;
	}

	public void setIszip(boolean iszip) {
		this.iszip = iszip;
	}

	public byte[] getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(byte[] msgBody) {
		this.msgBody = msgBody;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public byte getSerialize() {
		return serialize;
	}

	public void setSerialize(byte serialize) {
		this.serialize = serialize;
	}
	
	

}
