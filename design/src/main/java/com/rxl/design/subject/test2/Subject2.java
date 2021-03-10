package com.rxl.design.subject.test2;


/**
 * ClassName: Subject
 * Description:  被观察者
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/07
 */
public interface Subject2 {

    /**
     * 注册观察者
     */
    void registerObserver(ObService o );

    /**
     * 删除观察者
     */
    void removeObserver(ObService o);

    /**
     * 通知所有观察者
     */
    void notifyObservers();

}
