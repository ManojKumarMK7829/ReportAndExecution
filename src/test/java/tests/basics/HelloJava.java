package tests.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.demoFiles.Employee;

import java.io.*;

@Slf4j
public class HelloJava {

    @Test(groups = {"basics"})
    public void printAsciValue() throws InterruptedException {
        int ch1 = 'a';
        int ch2 = 'b';
        System.out.println("The ASCII value of a is: "+ch1);
        System.out.println("The ASCII value of b is: "+ch2);

        /*  In oder to check in report  */
        log.info("The ASCII value of a is: "+ch1);
        log.info("The ASCII value of b is: "+ch2);

//        /*
//           In order to check fail status of report
//        Assert.assertTrue(Boolean.FALSE);
//        * */

        /*  In order to check parallel execution

        for (int i= 0; i<100; i++){
            Thread.sleep(5000);
            System.out.println("The ASCII value of a is: "+ch1);
            System.out.println("The ASCII value of b is: "+ch2);
            Thread.sleep(5000);
        }
         */
    }

    @Test
    public void createObject() throws Exception {
        //Creating the object
        Employee emp = new Employee(198054,"Andrew");
        //Creates a stream and writes the object
        FileOutputStream fout=new FileOutputStream("employee.txt");
        ObjectOutputStream out=new ObjectOutputStream(fout);
        out.writeObject(emp);
        out.flush();
        out.close();
        log.info("Object created Successfully");
    }

    @Test(groups = {"basics"},dependsOnMethods = "createObject")
    public void getInstanceFromFile() throws Exception {
        ObjectInputStream in=new ObjectInputStream(new FileInputStream("employee.txt"));
        Employee e=(Employee)in.readObject();

        log.info(e.getEmpid()+" "+e.getEmpname());
        in.close();
    }

    @Test(groups = {"basics"})
    public void createInstanceWithoutNewKeyword() throws Exception {
        Employee employee =Employee.class.getConstructor().newInstance();
        employee.setEmpid(1);
        employee.setEmpname("manu");
        log.info(employee.getEmpid()+"----------"+employee.getEmpname());

        Employee emp = (Employee) Class.forName("tests.demoFiles.Employee").getConstructor().newInstance();
        emp.setEmpid(1);
        emp.setEmpname("Raj");
        log.info(emp.getEmpid()+"----------"+emp.getEmpname());
    }

    @Test(groups = {"basics"})
    public void checkTheNumberIsPerfectSqrt()  {
        double number = 100;
        double sqrt = Math.sqrt(number);

        if(sqrt - Math.floor(sqrt) == 0)
            log.info("Number is perfect square root number");
        else
            log.info("Number is not perfect square root number");
    }

}
