package com.game.mina.impl;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.game.logger.Logs;
import com.game.mina.IServer;
import com.game.mina.code.GProtocolCodecFactory;
import com.game.mina.handler.SSLProtocolHandler;
import com.game.mina.handler.ServerHandler;

/**
 * 
 * @ClassName: AbsGameServer 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:19:16 
 * @Description: 这里的游戏服务器包括了游戏服务器（游戏的业务处理）ConnectServer和 as3的policy验证SSLServer
 *
 */
public abstract class AbsGameServer implements IServer,Runnable{
	private int port;
	private int ssl_port;
	protected NioSocketAcceptor acceptor;
	public AbsGameServer(int port,int ssl_port){
		this.port = port;
		this.ssl_port = ssl_port;
	}

	@Override
	public void run() {
		new Thread(new ConnectServer(this)).start();
		new Thread(new SSLServer(this)).start();
	}
	
	/**
	 * 
	 * @ClassName: ConnectServer 
	 * @author chainisit@126.com
	 * @date 2014-9-28 下午4:20:22 
	 * @Description: 游戏服务器（游戏的业务处理）
	 *
	 */
	private class ConnectServer implements Runnable{
		private AbsGameServer server;
		public ConnectServer(AbsGameServer server){
			this.server = server;
		}
		
		@Override
		public void run() {
			AbsGameServer.this.acceptor = new NioSocketAcceptor();
			DefaultIoFilterChainBuilder chain = AbsGameServer.this.acceptor.getFilterChain();
			chain.addLast("codec", new ProtocolCodecFilter(new GProtocolCodecFactory()));
			OrderedThreadPoolExecutor threadpool = new OrderedThreadPoolExecutor(500);
			chain.addLast("threadPool", new ExecutorFilter(threadpool));

			int recsize = 524288;
			int sendsize = 1048576;

			AbsGameServer.this.acceptor.setReuseAddress(true);

			SocketSessionConfig sc = AbsGameServer.this.acceptor.getSessionConfig();
			sc.setReuseAddress(true);
			sc.setReceiveBufferSize(recsize);
			sc.setSendBufferSize(sendsize);
			sc.setTcpNoDelay(true);
			sc.setSoLinger(0);

			AbsGameServer.this.acceptor.setHandler(new ServerHandler(this.server));
			try {
				AbsGameServer.this.acceptor.bind(new InetSocketAddress(AbsGameServer.this.port));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	/**
	 * 
	 * @ClassName: SSLServer 
	 * @author chainisit@126.com
	 * @date 2014-9-28 下午4:20:46 
	 * @Description: as3的policy验证服务器
	 *
	 */
	private class SSLServer implements Runnable{
		private AbsGameServer server;
		public SSLServer(AbsGameServer server){
			this.server = server;
		}
		@Override
		public void run() {
			NioSocketAcceptor sslAcceptor = new NioSocketAcceptor();
			sslAcceptor.setHandler(new SSLProtocolHandler());
			try {
				sslAcceptor.bind(new InetSocketAddress(AbsGameServer.this.ssl_port));
			} catch (Exception e) {
				e.printStackTrace();
				Logs.debugLog.debug(server.ssl_port);
			}
		}
		
	}
}
