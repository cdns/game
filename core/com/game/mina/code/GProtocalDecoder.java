package com.game.mina.code;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.game.util.SerializeUtil;

/**
 * 
 * @ClassName: GProtocalDecoder 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:23:41 
 * @Description: 解码器
 *
 */
public class GProtocalDecoder implements ProtocolDecoder {
	private static Logger log = Logger.getLogger(GProtocalDecoder.class);
	private int maxPackLength = 512;
	final Charset charset;
	private final AttributeKey key = new AttributeKey(getClass(), "context");

	public GProtocalDecoder() {
		this(Charset.defaultCharset());
	}

	public GProtocalDecoder(Charset charset) {
		this.charset = charset;
	}

	public int getMaxLineLength() {
		return maxPackLength;
	}

	public void setMaxLineLength(int maxLineLength) {
		if (maxLineLength <= 0) {
			throw new IllegalArgumentException("MaxLineLength: " + maxLineLength);
		}
		this.maxPackLength = maxLineLength;
	}

	private GContext getContext(IoSession session) {
		GContext ctx = (GContext) session.getAttribute(key); // 从 I/O 会话中获取键为
																// key的用户自定义的属性。
		if (ctx == null) {
			ctx = new GContext();
			session.setAttribute(key, ctx);
		}
		return ctx;
	}

	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		if (!session.isClosing()) {
			// 先获取上次的处理上下文，其中可能有未处理完的数据
			GContext ctx = getContext(session);
			// 先把当前buffer中的数据追加到Context的buffer当中
			ctx.append(in);
			// 把position指向0位置，把limit指向原来的position位置
			IoBuffer buf = ctx.getBuffer();
			buf.flip();// 极限设为position
			// 然后按数据包的协议进行读取
			while (buf.remaining() > 0) {
				buf.mark(); // 上次position的快照
				// 读取消息头部分
				int length = buf.get();
				int j = 0;
				if (length == 1) {
					j = buf.get();
				} else if (length == 2) {
					if (buf.remaining() < 2)
						break;
					j = buf.getShort();
					if ((j <= 0) || (j > 10240)) {
						buf.clear();
						buf.limit(0);
						log.error("Recived message's head length error,the message head length is " + j + "!!");
						break;
					}
					if ((j >= 2) && (j - 2 <= buf.remaining())) {
						DataPack dataPack = new DataPack();
						dataPack.setSessionId(session.getId());
						dataPack.setMsgid(buf.getInt());
						int isCompress = buf.get();
						int bodyLength = buf.getShort();
						byte[] body = new byte[bodyLength];
						buf.get(body);
						if (isCompress == 1) {
							dataPack.setMsgBody(SerializeUtil.byteUncompress(body));
							log.info("Decoder  Msgid =" + dataPack.getMsgid()
									+ ", isZip = " + true + " , sessionid = "
									+ session.getId() + "   "
									+ session.getRemoteAddress().toString()
									+ " ,Total Length = " + (bodyLength + 1)
									+ " , BodyLength = " + bodyLength
									+ " , MesBodyLength = "
									+ dataPack.getMsgBody().length);
						} else {
							dataPack.setMsgBody(body);
							log.info("Decoder  Msgid =" + dataPack.getMsgid()
									+ ", isZip = " + false + " , sessionid = "
									+ session.getId() + "   "
									+ session.getRemoteAddress().toString()
									+ " ,Total Length = " + (bodyLength + 1)
									+ " , BodyLength = " + bodyLength
									+ " , MesBodyLength = "
									+ dataPack.getMsgBody().length);
						}
						if (dataPack.getMsgid() > 0) {
							out.write(dataPack);
						}
					} else {
						buf.reset();
						break;
					}
				} else {
					buf.clear();
					buf.limit(0);
					break;
				}
			}
			if (buf.hasRemaining()) {
				IoBuffer buffer = IoBuffer.allocate(maxPackLength).setAutoExpand(true);
				buffer.setAutoShrink(true);
				buffer.put(buf);
				buffer.flip();
				buf.clear();
				buf.put(buffer);
			} else {
				buf.clear();
			}
		}
	}

	public void dispose(IoSession session) throws Exception {
		GContext localGContext = (GContext) session.getAttribute(key);
		if (localGContext != null)
			session.removeAttribute(key);
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
	}
}
