package com.rxl.design.subject.test2;

import com.rxl.design.subject.Observer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MilkStation
 * Description: MilkStation service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/07
 */
@Data
public class MilkStation implements Subject2{

    private String milkName;

    private List<ObService> obServices;

    public MilkStation() {
        obServices = new ArrayList<>();
    }

    @Override
    public void registerObserver(ObService o) {
        obServices.add(o);
    }

    @Override
    public void removeObserver(ObService o) {
        obServices.remove(o);
    }

    @Override
    public void notifyObservers() {
        obServices.forEach(index ->{
            index.update(milkName);
        });
    }

    public void setData(String milkName){
        this.milkName = milkName;
        notifyObservers();
    }
}
