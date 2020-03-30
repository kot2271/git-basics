package employee;

import company.Company;

public class TopManager implements Employee {

  private int INCOME = 10000000;
  private static final int INCOME_FOR_BONUS = 10_000_000;
  private final int FIX_SALARY = 50_000 + (int) (Math.random() * 20000);
  private final int BONUS = 30_000 + (int) (Math.random() * 20000);
  private Company company;

  public TopManager(Company company) {
    this.company = company;
  }

  @Override
  public int getMonthSalary() {
    int monthSalary = FIX_SALARY + BONUS;
    if (INCOME_FOR_BONUS < INCOME) {
      monthSalary = (FIX_SALARY);
    }
    return monthSalary;
  }

  @Override
  public void setMonthSalary() {}

  @Override
  public String toString() {
    return String.valueOf(this.getMonthSalary());
  }
}
