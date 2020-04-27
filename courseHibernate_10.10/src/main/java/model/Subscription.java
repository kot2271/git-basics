package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Subscriptions")
public class Subscription {
      @EmbeddedId
      private SubscriptionPK key;

  @Column(name = "subscription_date")
  private Date subscriptionDate;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "course_id", insertable = false, updatable = false)
  protected Course course;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "student_id", insertable = false, updatable = false)
  protected Student student;

      public SubscriptionPK getKey() {
          return key;
      }

  public Date getSubscriptionDate() {
    return subscriptionDate;
  }

  public void setSubscriptionDate(Date subscriptionDate) {
    this.subscriptionDate = subscriptionDate;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }


  @Override
  public String toString() {
    return "Subscription{" +
            "key=" + key +
            ", subscriptionDate=" + subscriptionDate +
            ", course=" + course +
            ", student=" + student +
            '}';
  }

  @Embeddable
  public static class SubscriptionPK implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    public SubscriptionPK(Student student, Course course) {
      this.student = student;
      this.course = course;
    }

    public Course getCourseId() {
      return course;
    }
    public Student getStudentId() {
      return student;
    }
  }
}



