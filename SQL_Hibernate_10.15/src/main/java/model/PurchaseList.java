package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "purchaseList")
public class PurchaseList {


  @EmbeddedId
  Id id;

  @Column(name = "student_name", updatable = false, insertable = false)
  private String studentName;

  @Column(name = "course_name", updatable = false, insertable = false)
  private String courseName;

  @Column(name = "price")
  private int price;

  @Column(name = "subscription_date")
  private Date subscriptionDate;

  public PurchaseList() {
  }


  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Date getSubscriptionDate() {
    return subscriptionDate;
  }

  public void setSubscriptionDate(Date subscriptionDate) {
    this.subscriptionDate = subscriptionDate;
  }

  @Embeddable
  private static class Id implements Serializable {

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

    public String getStudentName() {
      return studentName;
    }

    public void setStudentName(String studentName) {
      this.studentName = studentName;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }

    public Id() {
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Id id = (Id) o;
      return Objects.equals(studentName, id.studentName) &&
              Objects.equals(courseName, id.courseName);
    }

    @Override
    public int hashCode() {
      return Objects.hash(studentName, courseName);
    }
  }
}
