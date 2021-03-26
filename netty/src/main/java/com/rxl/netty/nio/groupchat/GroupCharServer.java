package com.rxl.netty.nio.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * ClassName: GroupCharServer
 * Description: GroupCharServer service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/22
 */
public class GroupCharServer {

    private Logger logger;

    //定义服务端基本的属性
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private final static int PORT = 6666;

    public GroupCharServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            logger = LoggerFactory.getLogger(GroupCharServer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int select = selector.select();
                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        //注册事件
                        if (next.isAcceptable()) {
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                            logger.info(accept.getRemoteAddress() + "：上线");
                            //检测到读取事件
                        } else if (next.isReadable()) {
                            readData(next);
                        }
                        iterator.remove();
                    }
                } else {
                    logger.info("等待中");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            int read = channel.read(allocate);
            if (read > 0) {
                String string = new String(allocate.array());
                logger.info("客户端发送消息：" + string);
                //然后转发数据到其他
                sendInfoToOtherClients(string, channel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                logger.info(channel.getRemoteAddress() + "离线");
                key.cancel();
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 转发数据到其他的通道客户端
     *
     * @param msg
     * @param self
     */
    private void sendInfoToOtherClients(String msg, SocketChannel self) {
        try {
            Set<SelectionKey> keys = selector.keys();
            for (SelectionKey key : keys) {
                Channel channel = key.channel();
                if (channel instanceof SocketChannel && channel != self) {
                    SocketChannel socketChannel = (SocketChannel) channel;
                    ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                    logger.info("服务端向" + socketChannel.getRemoteAddress().toString() + " 发送数据");
                    socketChannel.write(wrap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupCharServer groupCharServer = new GroupCharServer();
        groupCharServer.listen();
    }
}
