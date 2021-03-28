package com.rxl.netty.sticorunpac;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * ClassName: NettyClientHandler
 * Description: NettyClientHandler service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/24
 */
public class NettyClientHandler  extends SimpleChannelInboundHandler<MyAgree> {

    /**
     * 通道就绪调用的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int a = 0 ; a <= 10 ;a ++){
            String str = "我是一个很帅的人+" + a;
            byte[] bytes = str.getBytes(CharsetUtil.UTF_8);
            int length = bytes.length;

            MyAgree myAgree = new MyAgree();
            myAgree.setLen(length);
            myAgree.setContent(bytes);
            ctx.writeAndFlush(myAgree);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyAgree msg) throws Exception {
        System.out.println("收到服务器返回的数据: " + new String(msg.getContent(),CharsetUtil.UTF_8));
    }

    /**
     * 通道异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
