import company.Company;
import employee.Employee;
import employee.Manager;
import employee.Operator;
import employee.TopManager;

import java.util.*;

public class Main {
  public static void main(String args[]) {

    Company company = new Company(10000000);
    
    List<Employee> managers = new ArrayList<>();
    for (int i = 0; i < 80; i++) {
      managers.add(new Manager(company));
    }

    List<Employee> operators = new ArrayList<>();
    for (int i = 0; i < 180; i++) {
      operators.add(new Operator(company));
    }

    List<Employee> topManagers = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      topManagers.add(new TopManager(company));
    }

    company.hireAll(managers);
    company.hireAll(operators);
    company.hireAll(topManagers);

    System.out.println("Самые высокие зарплаты в компании:\n ");

    for (Employee employee : company.getTopSalaryStaff(20)) {
      System.out.println(employee.getMonthSalary() + " руб.");
    }

    System.out.println("-----------------------------------------------\n");

    System.out.println("Самые низкие зарплаты в компании:\n ");
    for (Employee employee : company.getLowestSalaryStaff(10)) {
      System.out.println(employee.getMonthSalary() + " руб.");
    }
  }
}
