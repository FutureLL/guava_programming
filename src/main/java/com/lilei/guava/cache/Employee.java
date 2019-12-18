package com.lilei.guava.cache;

import com.google.common.base.MoreObjects;

/**
 * @description: 缓存测试对象
 * @author: Mr.Li
 * @date: Created in 2019/12/18 16:28
 * @version: 1.0
 * @modified By:
 */
public class Employee {

    private final String name;
    private final String dept;
    private final String empID;

    public Employee(String name, String dept, String empID) {
        this.name = name;
        this.dept = dept;
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getEmpID() {
        return empID;
    }



    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Name",this.getName())
                .add("department",this.getDept())
                .add("employeeID",this.getEmpID())
                .toString();
    }
}
