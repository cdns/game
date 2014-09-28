package com.game;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * @ClassName: IMsgBody 
 * @author chainisit@126.com
 * @date 2014-9-28 下午4:29:20 
 * @Description: 消息体
 *
 */
public abstract interface IMsgBody extends Serializable{
  public abstract void write(DataOutput paramDataOutput)
    throws IOException;

  public abstract void read(DataInput paramDataInput)
    throws IOException;
}