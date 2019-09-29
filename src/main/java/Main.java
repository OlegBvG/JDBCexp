import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String pass ="123456789";

        Connection connection = DriverManager.getConnection( url,  user,  pass );
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("" +
                "select p1.course_name name, avg(amtCourse) avgPurchase  from skillbox.purchaselist p1, \n" +
                "(select distinctrow course_name, month(subscription_date) monthNum, count(course_name) amtCourse  \n" +
                        "from skillbox.purchaselist group by course_name, monthNum) p2\n" +
                "where p1.course_name = p2.course_name and month(p1.subscription_date) = monthNum\n" +
                "group by p1.course_name;");
        while (resultSet.next()){
            String coursName = resultSet.getString("name");
            String avgPurchase = resultSet.getString("avgPurchase");
            System.out.println("Среднее количество покупок в месяц, курса: " + coursName + " => " + avgPurchase);
        }
    }
}
