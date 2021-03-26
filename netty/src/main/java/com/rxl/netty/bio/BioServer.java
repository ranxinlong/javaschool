package com.rxl.netty.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: BioServer
 * Description: BioServer service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/15
 */
public class BioServer {

    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newCachedThreadPool();
        ServerSocket socket = new ServerSocket(6666);
        System.out.println("服务端启动");
        while (true){
            System.out.println("监听是否有连接连接---------------");
            Socket accept = socket.accept();
            System.out.println("监听连接一个客户端线程id为：" + Thread.currentThread().getId());
            service.execute(new Runnable() {
                @lombok.SneakyThrows
                @Override
                public void run() {
                    handler(accept);
                }
            });
        }
    }

    public static void  handler(Socket socket) throws Exception{
        byte[] bytes = new byte[1024];
        InputStream stream = socket.getInputStream();
        while (true){
            System.out.println("等待读取数据---------");
            int read = stream.read(bytes);
            if (read != -1){
                System.out.printf("接受数据为:" + new String(bytes,0,read) + "线程id为:" + Thread.currentThread().getId());
            }else {
                break;
            }
        }
        socket.close();
    }
}
