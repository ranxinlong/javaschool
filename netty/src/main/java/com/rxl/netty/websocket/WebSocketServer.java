package com.rxl.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * ClassName: WebSocketServer
 * Description: WebSocketServer service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/26
 */
public class WebSocketServer {

    private Integer port;

    WebSocketServer(Integer port){
        this.port = port;
    }

    private void stare(){
        //子线程数计算方式：机器CPU核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到的连接个数
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //websocket基于HTTP协议的，所以需用HTTP的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块方式写
                            pipeline.addLast(new ChunkedWriteHandler());
                            //http传输数据可能是分段的，HttpObjectAggregator就是将多段数据聚合起来
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            //websocket数据是以帧发送的，浏览器需要访问类似：ws://localhost:7000/cj的url，
                            // WebSocketServerProtocolHandler就是通过状态码101 将http协议升级为ws，保持一个长连接
                            pipeline.addLast(new WebSocketServerProtocolHandler("/dj"));

                            //自定义的处理器
                            pipeline.addLast(new MyWebSocketFrameHandler());

                        }
                    });

            ChannelFuture sync = bootstrap.bind(port).sync();
            System.out.println("服务端准备就绪!");
            //对关闭通道进行同步建议
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new WebSocketServer(6666).stare();
    }
}
