package com.gs.login.handler;

import com.game.command.Handler;
import com.game.logger.Logs;
import com.game.mina.code.DataPack;
import com.game.util.SerializeUtil;
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
		res.isHaveRole = false;
		res.isLoginSuccess = true;
		res.uid = "10000002010";
		msg.setMsgBody(res);
		
		DataPack d = new DataPack();
		d.setMsgBody(SerializeUtil.msgBodyToByte(res));
		d.setMsgid(1002);
		msg.getSession().write(d);
	}

}
