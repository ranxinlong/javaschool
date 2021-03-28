package com.rxl.netty.sticorunpac;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: MyAgree
 * Description: MyAgree service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/28
 */
@Data
public class MyAgree implements Serializable {
    private static final long serialVersionUID = 6091653754667284873L;

    /**
     * 数据报文长度
     */
    private Integer len;

    /**
     * 数据二进制文件
     */
    private byte[] content;
}
