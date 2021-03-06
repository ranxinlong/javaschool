package com.rxl.netty.protobuf;

import com.rxl.netty.protobuf.pojo.DataInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * ClassName: NettyClient
 * Description: NettyClient service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/24
 */
public class NettyClient {

    private String ip;

    private Integer port;

    public NettyClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    private void start(){
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //解码时解决粘包拆包
                            socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            //加入谷歌proto解码，并指定需要解码的对象
                            socketChannel.pipeline().addLast(new ProtobufDecoder(DataInfo.MessageInfo.getDefaultInstance()));
                            //编码时解决粘包拆包
                            socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            //客户端加入ProtobufEncoder处理谷歌proto
                            socketChannel.pipeline().addLast(new ProtobufEncoder());
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.connect(new InetSocketAddress(ip, port)).sync();
            Channel channel = sync.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                channel.writeAndFlush(Unpooled.copiedBuffer(s, CharsetUtil.UTF_8));
            }
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            loopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 6666);
        client.start();
    }
}
