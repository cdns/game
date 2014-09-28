package com.game.mina.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.game.mina.IServer;

/**
 * 
 * @ClassName: ServerHandler 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:21:42 
 * @Description: 游戏业务handler
 *
 */
public class ServerHandler extends IoHandlerAdapter{
	private IServer server;
	public ServerHandler(IServer server){
		this.server = server;
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		this.server.exceptionCaught(session, cause);
	}
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		this.server.doCommand(session, message);
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		this.server.sessionClosed(session);
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		this.server.sessionCreate(session);
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		this.server.sessionIdle(session, status);
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		this.server.sessionOpened(session);
	}
}
