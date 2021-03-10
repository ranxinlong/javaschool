package com.rxl.design.subject.test2;

/**
 * ClassName: QiaoZhi
 * Description: QiaoZhi service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/07
 */
public class QiaoZhi  implements ObService{
    private String milkName;

    @Override
    public void update(String milkName) {
        this.milkName = milkName;
        display();
    }
    // 显 示
    public void display() {
        System.out.println("===乔治家回答: 今日牛奶是："+milkName+"====");
    }
}
