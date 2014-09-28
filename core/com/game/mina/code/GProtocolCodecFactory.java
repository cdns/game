package com.game.mina.code;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 
 * @ClassName: GProtocolCodecFactory 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:24:07 
 * @Description: 编解码工厂
 *
 */
public class GProtocolCodecFactory implements ProtocolCodecFactory {
    private GProtocalEncoder encoder;
    private GProtocalDecoder decoder;

    public GProtocolCodecFactory() {
        this.encoder = new GProtocalEncoder();
        this.decoder = new GProtocalDecoder();
    }

    public ProtocolEncoder getEncoder(IoSession paramIoSession) {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession paramIoSession) {
        return decoder;
    }
}
