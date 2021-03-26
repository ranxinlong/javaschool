package com.rxl.netty.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * ClassName: NioClient
 * Description: NioClient service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/19
 */
public class NioClient {

    public static void main(String[] args) throws Exception {
        SocketChannel open = SocketChannel.open();
        open.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);

        if (!open.connect(address)){
            while (!open.finishConnect()){
                System.out.println("连接需要事件但是客户端不会堵塞");
            }
        }
        String str = "您好，服务器";
        ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
        open.write(wrap);
        System.in.read();
    }
}
