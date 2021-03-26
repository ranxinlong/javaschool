package com.rxl.netty.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

/**
 * ClassName: FileChannel
 * Description: FileChannel service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/15
 */
public class FileChannel {
    /**
     * 把文字使用FileChannel 管道和byteBuffer 的方式输出到指定位置
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception{

        //1 数据输出到本地文件

/*        String str = "你好，未来可期，也许现在很多的烦恼";
        FileOutputStream outputStream = new FileOutputStream("d:\\file.text");
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        allocate.put(str.getBytes());

        allocate.flip();
        java.nio.channels.FileChannel channel = outputStream.getChannel();
        channel.write(allocate);*/

        //2 从本地文件读取数据
/*        File file = new File("d:\\file.text");
        FileInputStream stream = new FileInputStream(file);
        java.nio.channels.FileChannel channel1 = stream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int)file.length());
        channel1.read(buffer);
        byte[] array = buffer.array();
        System.out.println(new String(array));
        stream.close();
        */

        //3 读取一个 文件的数据并且拷贝到另外一个文件里面
/*        File file = new File("d:\\file.text");
        FileInputStream fileInputStream = new FileInputStream(file);
        java.nio.channels.FileChannel channel = fileInputStream.getChannel();

        FileOutputStream stream = new FileOutputStream("d:\\file3.text");
        java.nio.channels.FileChannel channel1 = stream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate(200);
        while (true){
            //todo 如果不复位，position 和limit相同read会返回0，死循环
            allocate.clear();
            int read = channel.read(allocate);
            //-1标示数据已经读取完毕了
            if (read == -1){
                break;
            }
            allocate.flip();
            channel1.write(allocate);
        }
        stream.close();
        fileInputStream.close();*/


        //4 使用通道拷贝文件里面
        File file2 = new File("d:\\file.text");
        FileInputStream fileInputStream4 = new FileInputStream(file2);
        java.nio.channels.FileChannel channel4 = fileInputStream4.getChannel();

        FileOutputStream stream4 = new FileOutputStream("d:\\file4.text");
        java.nio.channels.FileChannel channel14 = stream4.getChannel();

        //通道拷贝
        channel14.transferFrom(channel4,0,channel4.size());
        channel14.close();
        stream4.close();
        channel4.close();
        fileInputStream4.close();

    }
}
