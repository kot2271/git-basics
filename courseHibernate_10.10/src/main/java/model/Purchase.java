package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Purchases")
public class Purchase {

    @EmbeddedId
    private PurchaseKey key;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

    private int price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;


    public Purchase (Student student, Course course, int price, Date subscriptionDate){
        this.key = new PurchaseKey (student, course);
        this.studentName = student.getName();
        this.courseName = course.getName();
        this.price = price;
        this.subscriptionDate = subscriptionDate;
    }

    public PurchaseKey getKey() {
        return key;
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

    @Override
    public String toString() {
        return "Purchase{" +
                "studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", price=" + price +
                "subscriptionDate=" + subscriptionDate +
                '}';
    }

    public static class PurchaseKey implements Serializable {

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "student_id")
        protected Student student;


        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "course_id")
        protected Course course;

        public PurchaseKey (Student student, Course course) {
            this.student = student;
            this.course = course;
        }
    }
}
