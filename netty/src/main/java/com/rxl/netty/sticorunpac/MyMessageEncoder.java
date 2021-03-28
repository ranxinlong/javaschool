package com.rxl.netty.sticorunpac;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ClassName: MyMessageEncoder
 * Description: MyMessageEncoder service impl
 * 自定义的编码器
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/28
 */
public class MyMessageEncoder extends MessageToByteEncoder<MyAgree> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MyAgree msg, ByteBuf out) throws Exception {
        System.out.println("MyMessageEncoder调用，编码报文数据");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
