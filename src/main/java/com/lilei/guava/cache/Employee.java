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

    // 假设JVM开辟128MB的堆内存,那么它能放多少Employee,肯定少于128的,因为Employee还有其他的一些属性也会占用内存
    private final byte[] data = new byte[1024 * 1024];

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
                .add("Name", this.getName())
                .add("department", this.getDept())
                .add("employeeID", this.getEmpID())
                .toString();
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("The name " + getName() + " will be GC.");
    }
}
