package com.rxl.netty.protobuf;

import com.rxl.netty.protobuf.pojo.DataInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * ClassName: NettyServer
 * Description: NettyServer service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/24
 */
public class NettyServer {

    private Integer port;

    NettyServer(Integer port){
        this.port = port;
    }

    private void stare(){
        //创建两个线程组，bossGroup处理注册事件 workerGroup处理读写事件 两个都是无限循环 两个group里面都包含多个NioEventLoop子线程
        //子线程数计算方式：机器CPU核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //链式编程
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    //使用NioServerSocketChannel作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到的连接个数
                    .option(ChannelOption.SO_BACKLOG,128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    //绑定通道 匿名对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //空闲连接处理机制 读空闲 写空闲 读写空闲 事件单位 并在后面跟上自己需要处理空连接逻辑的handler
                          /*  socketChannel.pipeline().addLast(new IdleStateHandler(5,10,60, TimeUnit.SECONDS));
                            socketChannel.pipeline().addLast(new MyHeartbeatHandler());*/
                            //解码时解决粘包拆包
                            socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            //加入谷歌proto解码，并指定需要解码的对象
                            socketChannel.pipeline().addLast(new ProtobufDecoder(DataInfo.MessageInfo.getDefaultInstance()));
                            //编码时解决粘包拆包
                            socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            socketChannel.pipeline().addLast(new ProtobufEncoder());
                            //绑定自定义通道处理器 按照这里的先后顺序处理 pipeline是管道
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            //绑定端口并同步
            ChannelFuture sync = bootstrap.bind(port).sync();

            //可以这样监听是否监听端口成功 使用了future-listener机制 异步回调机制
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (sync.isSuccess()){
                        System.out.println("服务端监听端口成功!");
                    }else {
                        System.out.println("服务端监听端口失败!");
                    }
                }
            });
            System.out.println("服务端准备就绪!");
            //对关闭通道进行同步建议
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //出现异常关闭两个线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(6666);
        nettyServer.stare();
    }
}
