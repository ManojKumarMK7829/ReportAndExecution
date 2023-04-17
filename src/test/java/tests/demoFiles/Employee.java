package tests.demoFiles;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable
{
    private int empid;
    private String empname;
    public Employee(int empid, String empname) {
        this.empid = empid;
        this.empname = empname;
    }

    public Employee () {}

}
