package company;

import employee.Employee;

import java.util.List;

public interface Staff {
  void hire(Employee employee);

  void hireAll(List<Employee> employee);

  void fire(List<Employee> employee);
}
