package com.rxl.design.cor;

/**
 * ClassName: Approver
 * Description: Approver service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/10
 */
public abstract class Approver {

    Approver approver;

    String name;

    public Approver(String name) {
        this.name = name;
    }

    /**
     * 设置下一个请求处理者
     * @param approver
     */
    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    //处理审批请求的方法，得到一个请求, 处理是子类完成，因此该方法做成抽象
    public abstract void processRequest(PurchaseRequest purchaseRequest);



}
