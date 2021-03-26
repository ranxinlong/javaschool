package com.rxl.netty.nio.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * ClassName: GroupCharClient
 * Description: GroupCharClient service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/22
 */
public class GroupCharClient {

    private final Logger logger = LoggerFactory.getLogger(GroupCharClient.class);

    private final String IP = "127.0.0.1";
    private final Integer HOST = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupCharClient(){
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(IP,HOST));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器发送数据
     * @param info
     */
    public void sendInfo(String info){
        try {
            info = userName + "说" + info;
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收取服务器发来的数据
     */
    public void readInfo(){
        try {
            logger.info("开始读取数据");
            System.out.println("开始读取数据");
            int select = selector.select();
            if (select > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if (next.isReadable()){
                        SocketChannel channel = (SocketChannel)next.channel();
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
                        channel.read(allocate);
                        String s = new String(allocate.array());
                        logger.info("接收到转发数据为:" + s);
                    }
                }
                iterator.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        GroupCharClient charClient = new GroupCharClient();
        new Thread(){
            @Override
            public void run() {
                while (true){
                    charClient.readInfo();
                    try {
                        System.out.println("静默3秒");
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
        //发送数据到服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            charClient.sendInfo(s);
        }

    }
}
