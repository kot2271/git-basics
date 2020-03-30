package employee;

import company.Company;

import java.util.Arrays;

public class Manager implements Employee {
  private int INCOME_PROFIT = 500_000;
  private final int FIX_SALARY = 40_000 + (int) (Math.random() * 20000);
  private final int BONUS = (int) ((INCOME_PROFIT * 0.05) + (int) (Math.random() * 20000));
  private Company company;

  public Manager(Company company) {
    this.company = company;
  }

  @Override
  public int getMonthSalary() {
    int monthSalary = FIX_SALARY + BONUS;
    return monthSalary;
  }

  @Override
  public void setMonthSalary() {}

  @Override
  public String toString() {
    return String.valueOf(this.getMonthSalary());
  }
}
