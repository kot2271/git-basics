package employee;

import company.Company;

public class Operator implements Employee {

  private final int FIX_SALARY = 30_000 + (int) (Math.random() * 20000);

  private Company company;

  public Operator(Company company) {
    this.company = company;
  }

  @Override
  public int getMonthSalary() {
    int monthSalary = FIX_SALARY;
    return monthSalary;
  }

  @Override
  public void setMonthSalary() {}

  @Override
  public String toString() {
    return String.valueOf(this.getMonthSalary());
  }
}
