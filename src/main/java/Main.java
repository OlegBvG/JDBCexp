import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String pass ="123456789";

        Connection connection = DriverManager.getConnection( url,  user,  pass );
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("" +
                "SELECT  p.course_name name\n" +
                ", max(p.subscription_date) mx\n" +
                ", min(p.subscription_date) mn\n" +
                ", count(p.course_name) / \n" +
                "  (period_diff(DATE_FORMAT(max(p.subscription_date), \"%Y%m\"), DATE_FORMAT(min(p.subscription_date), \"%Y%m\")) + 1) avgPurchase\n" +
                "from purchaselist p group by p.course_name;");

       
        while (resultSet.next()){
            String coursName = resultSet.getString("name");
            String avgPurchase = resultSet.getString("avgPurchase");
            System.out.println("Среднее количество покупок в месяц, курса: " + coursName + " => " + avgPurchase);

        }
        resultSet.close();
        statement.close();
        connection.close();

    }
}
