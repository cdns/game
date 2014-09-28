import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.game.mina.code.GProtocolCodecFactory;
import com.gs.login.message.MsgLoginKeyValue;
import com.gs.login.message.ReqLoginMessage;


public class GameClient {
	protected NioSocketConnector socket = null;
	public void launch() {
		this.socket = new NioSocketConnector();
		this.socket.getFilterChain().addLast("codec", new ProtocolCodecFilter(new GProtocolCodecFactory()));
		OrderedThreadPoolExecutor threadpool = new OrderedThreadPoolExecutor(500);
		this.socket.getFilterChain().addLast("threadPool",new ExecutorFilter(threadpool));
		int recsize = 524288;
		int sendsize = 1048576;
		SocketSessionConfig sc = this.socket.getSessionConfig();
		sc.setReceiveBufferSize(recsize);
		sc.setSendBufferSize(sendsize);
		sc.setSoLinger(0);
		this.socket.setHandler(new ClientHandler());
		ConnectFuture future = this.socket.connect(new InetSocketAddress("127.0.0.1",8001));
        future.awaitUninterruptibly();
        
        sendMsg(future.getSession());
	}
	private void sendMsg(IoSession session) {
		MsgLoginKeyValue m = new MsgLoginKeyValue();
		m.key = "username";
		m.value = "zhangsan";
		MsgLoginKeyValue[] ms = new MsgLoginKeyValue[1];
		ms[0] = m;
		ReqLoginMessage msg = new ReqLoginMessage();
		msg.longins = ms;
		msg.setMsgId(1001);
		session.write(msg);
	}
	
	public static void main(String[] args) {
		GameClient c  = new GameClient();
		c.launch();
	}
}
