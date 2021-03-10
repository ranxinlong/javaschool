package com.rxl.design.subject;

/**
 * ClassName: YahuSite
 * Description: YahuSite service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/07
 */
public class YaHuSite implements Observer {

    // 温度，气压，湿度
    private float temperature;
    private float pressure;
    private float humidity;


    @Override
    public void update(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    // 显 示
    public void display() {
        System.out.println("===雅虎网站====");
        System.out.println("***雅虎网站 气温 : " + temperature + "***");
        System.out.println("***雅虎网站 气压: " + pressure + "***");
        System.out.println("***雅虎网站 湿度: " + humidity + "***");
    }
}
