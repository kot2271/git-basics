import java.sql.*;

public class Main {
  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/skillbox?serverTimezone=UTC";
    //      String url = "jdbc:mysql://localhost:3306/skillbox?serverTimezone=Europe/Moscow";
    String user = "root";
    String password = "Testtest";
    String courseQuery =
        "SELECT (SELECT name FROM courses WHERE id=course_id) AS course"
            + "   ,MONTH(subscription_date) AS monthNum"
            + "   ,MONTHNAME(subscription_date) AS monthName"
            + "   ,COUNT(*) AS purchaseNum"
            + "   FROM subscriptions"
            + "   GROUP by course, (subscription_date)"
            + "   ORDER by course, monthNum"
            + ";";

    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      Statement statement = connection.createStatement();

      ResultSet resultSet = statement.executeQuery(courseQuery);

      while (resultSet.next()) {
        String courseName = resultSet.getString("course");
        String monthName = resultSet.getString("monthName");
        int purchaseNum = resultSet.getInt("purchaseNum");
        System.out.println(courseName + "\t\t" + monthName + "\t\t" + purchaseNum);
      }

      resultSet.close();
      statement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
