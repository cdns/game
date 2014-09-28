package com.game.mina;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 
 * @ClassName: IServer 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:25:59 
 * @Description: 服务器接口 
 *
 */
public interface IServer {
	public static final int GATE_SERVER = 1;//网关服务器
	public static final int GAME_SERVER = 2;//游戏服务器
	public static final int WORLD_SERVER = 3;//世界服务器
	public static final int PUBLIC_SERVER = 4;//公共服务器

	/**
	 * 
	 * @Title: doCommand 
	 * @param @param session
	 * @param @param o   接受的消息
	 * @return void    
	 * @throws 
	 * @Description: messageReceived的处理方法
	 */
	public abstract void doCommand(IoSession session,Object o);

	public abstract void sessionCreate(IoSession session);

	public abstract void sessionOpened(IoSession session);

	public abstract void sessionClosed(IoSession session);

	public abstract void exceptionCaught(IoSession session,Throwable cause);

	public abstract void sessionIdle(IoSession session,IdleStatus idleStatus);
}
