package com.gs.message.pool;
import java.util.HashMap;

import com.game.command.Handler;
import com.game.mina.message.Message;
import com.gs.login.handler.ReqLoginHandler;
import com.gs.login.message.ReqLoginMessage;

public class MessagePool {
	// 消息类字典
	HashMap<Integer, Class<?>> messages = new HashMap<Integer, Class<?>>();
	// 处理类字典
	HashMap<Integer, Class<?>> handlers = new HashMap<Integer, Class<?>>();
	
	public MessagePool(){
		register(1001, ReqLoginMessage.class, ReqLoginHandler.class);
	}
	
	private void register(int id, Class<?> messageClass, Class<?> handlerClass){
		messages.put(id, messageClass);
		if(handlerClass!=null) handlers.put(id, handlerClass);
	}
	
	/**
	 * 获取消息体
	 * @param id
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Message getMessage(int id) throws InstantiationException, IllegalAccessException{
		if(!messages.containsKey(id)){
			return null;
		}else{
			return (Message)messages.get(id).newInstance();
		}
	}
	
	/**
	 * 获取处理函数
	 * @param id
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Handler getHandler(int id) throws InstantiationException, IllegalAccessException{
		if(!handlers.containsKey(id)){
			return null;
		}else{
			return (Handler)handlers.get(id).newInstance();
		}
	}
}
