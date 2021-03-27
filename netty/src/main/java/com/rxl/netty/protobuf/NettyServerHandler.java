package com.rxl.netty.protobuf;

import com.rxl.netty.protobuf.pojo.DataInfo;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: NettyServerHandler
 * Description: NettyServerHandler service impl
 * 自定义的rHandler需要继承netty规定好的某个HandlerAdapter规范才得行
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/24
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    /**
     * 定义一个channle组，管理所有的channle GlobalEventExecutor.INSTANCE是一个全局的时间执行器，而且是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * handlerAdded表示连接建立，一旦连接，这个方法是第一执行的
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String str = "[客户端]" + ctx.channel().remoteAddress() + "成功加入家庭";
        channelGroup.writeAndFlush(Unpooled.copiedBuffer(str,CharsetUtil.UTF_8));
        channelGroup.add(ctx.channel());
    }

    /**
     *  channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端：" +ctx.channel().remoteAddress() + "连接成功!");
    }


    /**
     *  channel 通道 Inactive 不活跃的
     *  当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String str = "[客户端]" + ctx.channel().remoteAddress() + "下线了\n";
        channelGroup.writeAndFlush(Unpooled.copiedBuffer(str,CharsetUtil.UTF_8));
        logger.info("channelGroup的大小为:" + channelGroup.size());
        logger.info("客户端：" +ctx.channel().remoteAddress() + "关闭连接成功!");
    }

    /**
     * 读取客户端发来的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        DataInfo.MessageInfo messageInfo = (DataInfo.MessageInfo) msg;
        DataInfo.MessageInfo.DataType type = messageInfo.getDataType();
        if (type == DataInfo.MessageInfo.DataType.CarInfoType){
            DataInfo.CarInfo carInfo = messageInfo.getCarInfo();
            logger.info("汽车品牌为：" +carInfo.getBrand() + "价格为: " + carInfo.getPrice());
        }else if (type == DataInfo.MessageInfo.DataType.UserInfoType){
            DataInfo.UserInfo userInfo = messageInfo.getUserInfo();
            logger.info("用户年龄为：" +userInfo.getAge()+ "姓名为: " + userInfo.getName());
        }else {
            logger.info("未识别类型");
        }
    }

    /**
     * 数据读取完毕以后的操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 将数据写入缓冲区并刷新，并且对数据进行编码
        //ctx.writeAndFlush(Unpooled.copiedBuffer("你好，客户端",CharsetUtil.UTF_8));
    }

    /**
     * 通道异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
