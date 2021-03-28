package com.rxl.netty.sticorunpac;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ClassName: MyMessageDecoder
 * Description: MyMessageDecoder service impl
 * 自定义的解码器
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/28
 */
public class MyMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int anInt = in.readInt();
        byte[] content =new byte[anInt];
        in.readBytes(content);

        MyAgree myAgree = new MyAgree();
        myAgree.setLen(anInt);
        myAgree.setContent(content);

        out.add(myAgree);
    }
}
