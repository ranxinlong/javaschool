package com.rxl.design.cor;

/**
 * ClassName: Client
 * Description: Client service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/10
 */
public class Client {

    /**
     * 采购员采购教学器材
     * 1)	如果金额 小于等于 5000,  由教学主任审批 （0<=x<=5000）
     * 2)	如果金额 小于等于 10000,  由院长审批 (5000<x<=10000)
     * 3)	如果金额 小于等于 30000,  由副校长审批 (10000<x<=30000)
     * 4)	如果金额 超过 30000 以上，有校长审批 ( 30000<x)
     * @param args
     */
    public static void main(String[] args) {
        //创建一个请求
        PurchaseRequest purchaseRequest = new PurchaseRequest(1, 11000, 1);
        //创建相关的审批人
        DepartmentApprover departmentApprover = new DepartmentApprover("张主任");
        CollegeApprover collegeApprover = new CollegeApprover("李院长");
        ViceSchoolMasterApprover viceSchoolMasterApprover = new ViceSchoolMasterApprover("王副校");
        SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("佟校长");


        //需要将各个审批级别的下一个设置好 (处理人构成环形: )
        departmentApprover.setApprover(collegeApprover);
        collegeApprover.setApprover(viceSchoolMasterApprover);
        viceSchoolMasterApprover.setApprover(schoolMasterApprover);
        schoolMasterApprover.setApprover(departmentApprover);


        departmentApprover.processRequest(purchaseRequest);
        //viceSchoolMasterApprover.processRequest(purchaseRequest);

    }
}
