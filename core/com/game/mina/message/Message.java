package com.game.mina.message;

import org.apache.mina.core.session.IoSession;

import com.game.IMsgBody;
/**
 * 
 * @ClassName: Message 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:25:38 
 * @Description: 消息类
 *
 */
public abstract class Message implements IMsgBody{
	private static final long serialVersionUID = 2196000202837573795L;
	private IoSession session;
	private int msgId;
	private String playerid;
	private String[] players;
	public byte serialize;
	private int sendTime;
	public Object msgBody;
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	}
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
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
	public byte getSerialize() {
		return serialize;
	}
	public void setSerialize(byte serialize) {
		this.serialize = serialize;
	}
	public int getSendTime() {
		return sendTime;
	}
	public void setSendTime(int sendTime) {
		this.sendTime = sendTime;
	}
	public Object getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(Object msgBody) {
		this.msgBody = msgBody;
	}
}
