import model.Course;
import model.Student;
import model.Subscription;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {

  static String HIBERNATE_CONFIG = "hibernate.cfg.xml";

  public static void main(String[] args) {
    try (StandardServiceRegistry registry =
        new StandardServiceRegistryBuilder().configure(HIBERNATE_CONFIG).build(); ) {
      Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

      try (
              SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

          )

      {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Course course = session.get(Course.class, 3);
        List<Student> studentList = course.getStudents();
        for (Student students : studentList) {
          System.out.println(students.getName());
        }

        Student student = course.getStudents().iterator().next();
        Subscription subscription = session.get(Subscription.class,
                new Subscription.SubscriptionPK(student, course));
        System.out.println(subscription.toString());

        transaction.commit();
        session.close();

      } catch (Exception ex) {
        System.out.println("Не удалось прочитать базу данных");
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
