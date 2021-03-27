package com.rxl.netty.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * ClassName: HeartbeatHandler
 * Description: HeartbeatHandler service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/26
 */
public class MyHeartbeatHandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx 上下文
     * @param evt 心跳事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("心跳事件");
        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String evtType = null;
            switch (idleStateEvent.state()){
                case ALL_IDLE:
                    evtType = "读写空闲";
                    break;
                case READER_IDLE:
                    evtType = "读空闲";
                    break;
                case WRITER_IDLE:
                    evtType = "写空闲";
                    break;
                default:
                    evtType = "不知道什么空闲";
            }
            //这里针对空闲连接做其他的一些处理
            System.out.println(evtType);
        }
    }
}
