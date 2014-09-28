package com.gs.login.handler;

import com.game.command.Handler;
import com.game.logger.Logs;
import com.gs.login.message.ReqLoginMessage;
import com.gs.login.message.ResLoginMessage;


public class ReqLoginHandler extends Handler {

	@Override
	public void action() {
		ReqLoginMessage msg = (ReqLoginMessage) this.getMessage();
//		MsgLoginKeyValue[] mkv = msg.longins;
		Logs.debugLog.debug("ReqLoginHandler " + msg.longins.length);
		Logs.debugLog.debug("ReqLoginHandler  value : " + msg.longins[0].value);
		ResLoginMessage res = new ResLoginMessage();
		res.setMsgId(1002);
		res.isHaveRole = false;
		res.isLoginSuccess = true;
		res.uid = "10000002010";
		msg.setMsgBody(res);
		msg.getSession().write(msg);
	}

}
