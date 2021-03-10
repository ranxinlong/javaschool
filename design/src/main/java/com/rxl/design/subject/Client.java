package com.rxl.design.subject;

/**
 * ClassName: client
 * Description: client service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/07
 */
public class Client {

    public static void main(String[] args) {
        WeatherData data = new WeatherData();

        BaiduSite site = new BaiduSite();
        data.registerObserver(site);

        YaHuSite yaHuSite = new YaHuSite();
        data.registerObserver(yaHuSite);

        data.setData(10f, 100f, 30.3f);

    }
}
