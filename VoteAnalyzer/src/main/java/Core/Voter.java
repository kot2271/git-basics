package Core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Voter {
  private String name;
  private LocalDate birthDay;

  public Voter(String name, LocalDate birthDay) {
    this.name = name;
    this.birthDay = birthDay;
  }

  @Override
  public boolean equals(Object obj) {
    Voter voter = (Voter) obj;
    return name.equals(voter.name) && birthDay.equals(voter.birthDay);
  }

  @Override
  public int hashCode() {
    long code = name.hashCode() + birthDay.hashCode();
    while (code > Integer.MAX_VALUE) {
      code = code / 10;
    }
    return (int) code;
  }

  public String toString() {
    DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault());
    return name + " (" + dayFormat.format(birthDay) + ")";
  }

  public String getName() {
    return name;
  }

  public LocalDate getBirthDay() {
    return birthDay;
  }
}
