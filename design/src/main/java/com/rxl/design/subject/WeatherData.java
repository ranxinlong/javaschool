package com.rxl.design.subject;

import lombok.Data;

import java.util.ArrayList;

/**
 * ClassName: WeatherData
 * Description: WeatherData service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/07
 */
@Data
public class WeatherData implements Subject {

    private float temperatrue;
    private float pressure;
    private float humidity;
    //观察者集合
    private ArrayList<Observer> observers;

    public WeatherData() {
        observers = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * 遍历所有的观察者，并通知 最新的天气情况
     *
     * @param o
     */
    @Override
    public void notifyObservers() {
        observers.forEach(index -> {
            index.update(this.getTemperatrue(), this.getPressure(), this.getHumidity());
        });
    }

    //当数据有更新时，就调用 setData
    public void setData(float temperature, float pressure, float humidity) {
        this.temperatrue = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        //调用 dataChange， 将最新的信息 推送给 接入方 currentConditions
        dataChange();
    }

    public void dataChange() {
        //调用 接入方的 update
        notifyObservers();
    }

}
