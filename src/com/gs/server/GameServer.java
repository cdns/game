package com.gs.server;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.game.command.Handler;
import com.game.logger.Logs;
import com.game.mina.code.DataPack;
import com.game.mina.impl.AbsGameServer;
import com.game.mina.message.Message;
import com.game.struct.NonOrderedQueuePoolExecutor;
import com.game.util.SerializeUtil;
import com.gs.message.pool.MessagePool;

/**
 * 
 * @ClassName: GameServer 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:14:24 
 * @Description: 游戏服务器
 * 		
 */
public class GameServer extends AbsGameServer {
	public GameServer(int port, int ssl_port) {
		super(port, ssl_port);
	}

	private NonOrderedQueuePoolExecutor actionExcutor = new NonOrderedQueuePoolExecutor(500);
	private MessagePool messagePool = new MessagePool();
	
	@Override
	public void doCommand(IoSession session, Object o) {
		try {
			if (null != o && o instanceof DataPack) {
				DataPack pack = (DataPack) o;
				Logs.debugLog.debug("pack.getSerialize() is  : " + pack.getSerialize());
				if(0 == pack.getSerialize()){
					//生成处理函数
					Handler handler = messagePool.getHandler(pack.getMsgid());
					Message msg = messagePool.getMessage(pack.getMsgid());
					if(null != msg){
						SerializeUtil.byteToObject(pack.getMsgBody(), msg);
					}
					msg.setSession(session);
					handler.setMessage(msg);
					actionExcutor.execute(handler);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void sessionCreate(IoSession session) {
		
	}

	@Override
	public void sessionOpened(IoSession session) {
		Logs.debugLog.debug("session id : " + session.getId());
	}

	@Override
	public void sessionClosed(IoSession session) {
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus idleStatus) {
		
	}

}
