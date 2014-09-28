package com.game.command;

import com.game.mina.message.Message;

public abstract class Handler implements ICommand {
	private Message message;
	private Object parameter;
	private long createTime;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Object getParameter() {
		return this.parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	public long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
