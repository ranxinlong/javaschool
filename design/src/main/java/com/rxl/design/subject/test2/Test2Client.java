package com.rxl.design.subject.test2;

/**
 * ClassName: Test2Client
 * Description: Test2Client service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/07
 */
public class Test2Client {

    public static void main(String[] args) {
        MilkStation station = new MilkStation();

        QiaoZhi qiaoZhi = new QiaoZhi();
        station.registerObserver(qiaoZhi);
        station.setData("核桃花生牛奶");


    }
}
