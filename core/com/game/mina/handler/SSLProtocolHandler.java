package com.game.mina.handler;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.game.logger.Logs;

/**
 * 
 * @ClassName: SSLProtocolHandler 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:21:14 
 * @Description: AS3的验证handler
 *
 */
public class SSLProtocolHandler extends IoHandlerAdapter{
	private static String _$2 = "<policy-file-request/>";
    private static String _$1 = "<cross-domain-policy>\n  <allow-access-from  domain=\"*\"  to-ports=\"*\"  />\n</cross-domain-policy>";

    public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
        super.exceptionCaught(session, throwable);
    }

    public void messageReceived(IoSession session, Object object) throws Exception {
        IoBuffer localIoBuffer1 = (IoBuffer) object;
        IoBuffer localIoBuffer2 = (IoBuffer) session.getAttribute("processBuf");
        localIoBuffer2.put(localIoBuffer1);
        localIoBuffer2.flip();
        String str = _$1(localIoBuffer2);
        if ((str != null) && (_$2.equals(str))) {
            byte[] arrayOfByte = _$1.getBytes("UTF-8");
            IoBuffer localIoBuffer3 = IoBuffer.allocate(arrayOfByte.length + 1);
            localIoBuffer3.put(arrayOfByte);
            localIoBuffer3.put((byte)0);
            localIoBuffer3.flip();
            session.write(localIoBuffer3);
            session.setAttribute("policySend", Boolean.valueOf(true));
        }
    }

    public void messageSent(IoSession session, Object paramObject) throws Exception {
        session.close(true);
    }

    public void sessionClosed(IoSession session) throws Exception {
        session.removeAttribute("processBuf");
        super.sessionClosed(session);
    }

    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        IoBuffer localIoBuffer = IoBuffer.allocate(64);
        session.setAttribute("processBuf", localIoBuffer);
        Logs.debugLog.debug("------------------------");
    }

    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    private String _$1(IoBuffer paramIoBuffer) {
        IoBuffer localIoBuffer = IoBuffer.allocate(64);
        boolean f = false;
        for (int j = 0; j < paramIoBuffer.limit(); j++) {
            byte b = paramIoBuffer.get();
            if (b != 0) {
                localIoBuffer.put(b);
            } else {
                f = true;
                break;
            }
        }
        if (f) {
            localIoBuffer.flip();
            paramIoBuffer.compact();
            try {
                byte[] arrayOfByte = new byte[localIoBuffer.limit()];
                localIoBuffer.get(arrayOfByte, 0, localIoBuffer.limit());
                String str = new String(arrayOfByte, "UTF-8");
                return str;
            } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
                return null;
            }
        }
        return null;
    }
}
