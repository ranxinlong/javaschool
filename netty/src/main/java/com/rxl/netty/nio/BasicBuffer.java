package com.rxl.netty.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * ClassName: BasicBuffer
 * Description: BasicBuffer service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/15
 */
public class BasicBuffer {

    public static void main(String[] args) {
        /*IntBuffer allocate = IntBuffer.allocate(5);
        for (int a =0 ; a < allocate.capacity() ; a++){
            allocate.put(a);
        }

        allocate.flip();
        while (allocate.hasRemaining()){
            System.out.println(allocate.get());
        }*/

        ByteBuffer allocate = ByteBuffer.allocate(64);
        for (int a = 0 ; a < 64 ; a ++){
            allocate.put((byte) a);
        }
        allocate.flip();
        System.out.println(allocate.getClass());

        while (allocate.hasRemaining()){
            System.out.println(allocate.get());
        }

        //把buffer转为只读的 在加入数据就ReadOnlyBufferException
        ByteBuffer byteBuffer = allocate.asReadOnlyBuffer();
        byteBuffer.put((byte) 99);
    }
}
