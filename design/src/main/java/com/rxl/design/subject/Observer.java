package com.rxl.design.subject;

/**
 * ClassName: Observer
 * Description: 观察者接口，由观察者来实现
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/07
 */
public interface Observer {

    /**
     * 更新天气情况
     * @param temperature
     * @param pressure
     * @param humidity
     */
    public void update(float temperature, float pressure, float humidity);
}
