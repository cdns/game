import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.game.logger.Logs;
import com.gs.login.message.ResLoginMessage;


public class ClientHandler extends IoHandlerAdapter{

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if(message instanceof ResLoginMessage){
			ResLoginMessage msg = (ResLoginMessage) message;
			Logs.debugLog.debug(msg.uid);
			
		}
	}

	
}
