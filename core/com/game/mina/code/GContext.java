package com.game.mina.code;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * 
 * @ClassName: GContext 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:22:56 
 * @Description: 用于处理半包,粘包的Context
 *
 */
public class GContext {
    private IoBuffer buffer = IoBuffer.allocate(1024);
    private int matchCount = 0;
    private int overflowPosition = 0;

    public GContext() {
        this.buffer.setAutoExpand(true);//自动扩展Buffer限制了的容量大小
        this.buffer.setAutoShrink(true);
    }

    public IoBuffer getBuffer() {
        return this.buffer;
    }

    public int getOverflowPosition() {
        return this.overflowPosition;
    }

    public int getMatchCount() {
        return this.matchCount;
    }

    public void setMatchCount(int paramInt) {
        this.matchCount = paramInt;
    }

    public void reset() {
        this.overflowPosition = 0;
        this.matchCount = 0;
    }

    public void append(IoBuffer in) {
        getBuffer().put(in);
    }
}
