import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class Main {

  static String HIBERNATE_CONFIG = "hibernate.cfg.xml";

  public static void main(String[] args) {

    try (SessionFactory factory = new Configuration().
            configure(HIBERNATE_CONFIG)
            .buildSessionFactory();
         Session session = factory.getCurrentSession()) {

      session.beginTransaction();
      Student student = session.get(Student.class, 30);
      List<Subscription> subscriptions = student.getSubscriptions();
      for (Subscription subscription : subscriptions) {
        System.out.println(subscription.getStudent().getName());
        System.out.println(subscription.getCourse().getName());
        System.out.println(subscription.getSubscriptionDate());
      }

      clearTable(session);

      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void clearTable(Session session) {
    List<PurchaseList> purchaseList = session.createQuery("from model.LinkedPurchaseList")
            .getResultList();
    for (PurchaseList var : purchaseList) {

      DetachedCriteria studentsCriteria = DetachedCriteria.forClass(Student.class)
              .add(Restrictions.eq("name", var.getStudentName()));
      Student student = (Student) studentsCriteria.getExecutableCriteria(session).list().stream()
              .findFirst().get();
      System.out.println(student.getName());

      DetachedCriteria coursesCriteria = DetachedCriteria.forClass(Course.class)
              .add(Restrictions.eq("name", var.getCourseName()));
      Course course = (Course) coursesCriteria.getExecutableCriteria(session).list().stream()
              .findFirst().get();
      System.out.println(course.getName());

      LinkedPurchaseList LPL = new LinkedPurchaseList(
              new LinkedPurchaseList.Id(student.getId(), course.getId()), student, course,
              course.getPrice(), var.getSubscriptionDate());
      session.save(LPL);
    }

  }

}
