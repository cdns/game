package com.game.mina.code;


import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.game.util.SerializeUtil;

/**
 * 
 * @ClassName: GProtocalEncoder 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:23:57 
 * @Description: 编码器
 *
 */
public class GProtocalEncoder extends ProtocolEncoderAdapter {
    private static Logger log = Logger.getLogger(GProtocalEncoder.class);
    private boolean isCompress = false;

    public GProtocalEncoder() {
    }

    public GProtocalEncoder(boolean isCompress) {
        this.isCompress = isCompress;
    }

    public void encode(IoSession session, Object data, ProtocolEncoderOutput paramProtocolEncoderOutput) throws Exception {
        if ((null != session) && (session.isConnected())) {
            if ((data instanceof byte[])) {
                byte[] bytes = (byte[]) data;
                IoBuffer localIoBuffer1 = IoBuffer.allocate(bytes.length);
                localIoBuffer1.put(bytes);
                localIoBuffer1.flip();
                paramProtocolEncoderOutput.write(localIoBuffer1);
                localIoBuffer1.clear();
                log.error("Encode data is byte[]!");
                return;
            }

            if ((data instanceof DataPack)) {
            	DataPack baseDataPack = (DataPack) data;
                int i = 7;
                int bodyLength = null != baseDataPack.getMsgBody() ? baseDataPack.getMsgBody().length : 0;
                if (bodyLength > 0) {
                    if (this.isCompress) {
                        boolean bool = (bodyLength > 150) && (baseDataPack.isIszip());
                        if (bool) {
                            byte[] arrayOfByte = SerializeUtil.byteCompress(baseDataPack.getMsgBody());
                            baseDataPack.setMsgBody(null);
                            baseDataPack.setMsgBody(arrayOfByte);
                            bodyLength = arrayOfByte.length;
                        }
                        baseDataPack.setIszip(bool);
                    }
                    i += 2;
                    i += bodyLength;
                    IoBuffer localIoBuffer2 = IoBuffer.allocate(i);
                    localIoBuffer2.setAutoExpand(true);
                    localIoBuffer2.put((byte) 2);
                    localIoBuffer2.putShort((short) i);
                    localIoBuffer2.putInt(baseDataPack.getMsgid());
                    localIoBuffer2.put((byte) (baseDataPack.isIszip() ? 1 : 0));
                    localIoBuffer2.putShort((short) bodyLength);
                    localIoBuffer2.put(baseDataPack.getMsgBody());
                    localIoBuffer2.flip();
					log.info("Encoder  Msgid =" + baseDataPack.getMsgid()
							+ ", isZip = " + baseDataPack.isIszip()
							+ " , sessionid = " + session.getId() + "   "
							+ session.getRemoteAddress().toString()
							+ " , Total Length = " + (i + 1)
							+ " ,BodyLength = " + i + " ,MsgBodyLength = "
							+ baseDataPack.getMsgBody().length);
                    paramProtocolEncoderOutput.write(localIoBuffer2);
                }
            }
        }
        data = null;
    }

    public void dispose() throws Exception {}
}
