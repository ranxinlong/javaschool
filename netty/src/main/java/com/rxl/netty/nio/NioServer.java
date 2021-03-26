package com.rxl.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * ClassName: NIOServer
 * Description: NIOServer service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/19
 */
public class NioServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel open = ServerSocketChannel.open();
        Selector selector = Selector.open();
        open.socket().bind(new InetSocketAddress(6666));
        open.configureBlocking(false);

        //监控注册事件
        open.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            if (selector.select(1000) == 0){
                System.out.println("选择器禁止1秒，无客户端连接");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //如果又新客户端连接
                if (selectionKey.isAcceptable()){
                    //就给客户端生一个socketchannel
                    SocketChannel socketChannel = open.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                //如果发生了读事件
                if (selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    ByteBuffer attachment = (ByteBuffer)selectionKey.attachment();
                    channel.read(attachment);
                    System.out.println("服务端收到的数据为: "+ new String(attachment.array()));
                }
            }
            iterator.remove();
        }
    }

}
