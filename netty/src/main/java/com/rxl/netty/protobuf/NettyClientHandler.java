package com.rxl.netty.protobuf;

import com.rxl.netty.protobuf.pojo.DataInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * ClassName: NettyClientHandler
 * Description: NettyClientHandler service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/24
 */
public class NettyClientHandler extends SimpleChannelInboundHandler {

    /**
     * 通道就绪调用的方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int a = 0 ; a <= 10 ; a++){
            DataInfo.MessageInfo messageInfo = DataInfo.MessageInfo.newBuilder().setDataType(DataInfo.MessageInfo.DataType.CarInfoType)
                    .setCarInfo(DataInfo.CarInfo.newBuilder().setBrand("GTR" + a).setPrice(999999).build()).build();
            ctx.writeAndFlush(messageInfo);
        }


        /*int anInt = new Random().nextInt(2);
        DataInfo.MessageInfo messageInfo = null;
        if (0 == anInt) {
            //发送CarInfo对象
            messageInfo = DataInfo.MessageInfo.newBuilder().setDataType(DataInfo.MessageInfo.DataType.CarInfoType)
                    .setCarInfo(DataInfo.CarInfo.newBuilder().setBrand("GTR").setPrice(999999).build()).build();
        } else {
            //发送UserInfo对象
            messageInfo = DataInfo.MessageInfo.newBuilder().setDataType(DataInfo.MessageInfo.DataType.UserInfoType)
                    .setUserInfo(DataInfo.UserInfo.newBuilder().setAge(24).setName("老王").build()).build();
        }
        ctx.writeAndFlush(messageInfo);*/
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到服务器返回的数据: " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 通道异常处理
     *
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
