package model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "courses")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private int duration;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum")
  private CourseType type;

  private String description;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @Column(name = "students_count")
  private Integer studentCount;

  private int price;

  @Column(name = "price_per_hour")
  private Float pricePerHour;

  @OneToMany(mappedBy = "course")
  private List<Subscription> subscriptions;

  @OneToMany(mappedBy = "course")
  private List<LinkedPurchaseList> linkedPurchaseList;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public CourseType getType() {
    return type;
  }

  public void setType(CourseType type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStudentCount() {
    return studentCount;
  }

  public void setStudentCount(int studentCount) {
    this.studentCount = studentCount;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Float getPricePerHour() {
    return pricePerHour;
  }

  public void setPricePerHour(float pricePerHour) {
    this.pricePerHour = pricePerHour;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public void setStudentCount(Integer studentCount) {
    this.studentCount = studentCount;
  }

  public void setPricePerHour(Float pricePerHour) {
    this.pricePerHour = pricePerHour;
  }

  public List<Subscription> getSubscriptions() {
    return subscriptions;
  }

  public void setSubscriptions(List<Subscription> subscriptions) {
    this.subscriptions = subscriptions;
  }
  public List<LinkedPurchaseList> getLinkedPurchaseList() {
    return linkedPurchaseList;
  }

  public void setLinkedPurchaseList(List<LinkedPurchaseList> linkedPurchaseList) {
    this.linkedPurchaseList = linkedPurchaseList;
  }

}
