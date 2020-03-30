package company;

import employee.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class Company implements Staff {

  public Company(int INCOME) {
    this.INCOME = INCOME;
  }

  private List<Employee> employees = new ArrayList<>();

  private List<Employee> sortEmployees(Comparator<Employee> comparator, int limit) {
    return employees.stream().sorted(comparator).limit(limit).collect(Collectors.toList());
  }

  //    private void sortEmployees (Comparator<Employee>comparator, int limit) {
  //        LinkedList<Employee>list = new LinkedList<>(employees);
  //        if (employees.size() < limit) {
  //            System.out.println("Вывод всех сотрудников компании:");
  //        }
  //        list.stream().sorted(comparator).limit(limit).forEach(System.out::println);
  //    }

  public List<Employee> getTopSalaryStaff(int count) {
    return sortEmployees(Comparator.comparing(Employee::getMonthSalary).reversed(), count);
  }

  public List<Employee> getLowestSalaryStaff(int count) {
    return sortEmployees(Comparator.comparing(Employee::getMonthSalary), count);
  }

  private int INCOME;

  public int getIncome() {
    return INCOME;
  }

  @Override
  public void hire(Employee employee) {
    employee.setMonthSalary();
    this.employees.add(employee);
  }

  @Override
  public void hireAll(List<Employee> employees) {
    this.employees.addAll(employees);
  }

  @Override
  public void fire(List<Employee> employees) {
    this.employees.remove(employees);
  }
}
