package com.rxl.netty.httpnetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URL;

/**
 * ClassName: HttpNettyServerHandler
 * Description: HttpNettyServerHandler service impl
 *SimpleChannelInboundHandler 继承了ChannelInboundHandlerAdapter
 * HttpObject 客户端和服务器相互通讯的数据被封装成为了HttpObject
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/25
 */
public class HttpNettyServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest) msg;
            URL url = new URL(httpRequest.uri());
            //过滤指定的资源
            if ("/sss".equals(url.getPath())){
                return;
            }
            //收到消息之后回复数据给浏览器 http 协议
            ByteBuf byteBuf = Unpooled.copiedBuffer("你好，浏览器", CharsetUtil.UTF_8);

            //构建一个HttpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"test/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

            //构建完毕直接返回
            ctx.channel().write(response);
        }
    }
}
