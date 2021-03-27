package com.rxl.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * ClassName: MyWebSocketFrameHandler
 * Description: MyWebSocketFrameHandler service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/26
 */
public class MyWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到数据: " + msg.text());
        //回复数据
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器：" + msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示唯一值，asLongText是绝对唯一的
        System.out.println("handlerAdded调用："+ ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
