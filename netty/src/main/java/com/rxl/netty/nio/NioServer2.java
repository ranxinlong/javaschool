package com.rxl.netty.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * ClassName: NioServer2
 * Description: NioServer2 service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/19
 */
public class NioServer2 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel open = ServerSocketChannel.open();
        Selector selector = Selector.open();
        //设置通道是非阻塞的
        open.configureBlocking(false);
        open.socket().bind(new InetSocketAddress(6666));
        //监听注册事件
        open.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            if (selector.select(1000) == 0){
                System.out.println("我等待了一秒钟了，还是莫得客户端连接我");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                //如果是注册事件
                if (next.isAcceptable()){
                    SocketChannel accept = open.accept();
                    accept.configureBlocking(false);
                    System.out.println("客户端注册成功，分配的通道code是：" +accept.hashCode() );
                    //给一个最新的通道，并且注册到selector，而且绑定一个ByteBuffer
                    accept.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }
                //获取到读事件，
                if (next.isReadable()){
                    SocketChannel channel = (SocketChannel)next.channel();
                    ByteBuffer attachment = (ByteBuffer)next.attachment();
                    channel.read(attachment);
                    System.out.println("服务器收到数据为:" + new String(attachment.array()));
                }
            }
            iterator.remove();
        }

    }
}
