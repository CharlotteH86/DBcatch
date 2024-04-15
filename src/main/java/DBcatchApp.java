import javax.swing.*;
import java.sql.Connection;
import java.sql.*;
import java.util.Properties;

public class DBcatchApp {

    public static void main(String[] args) {
        Properties prop = new Properties();
        String username = prop.getProperty("db.username");
        String password = prop.getProperty("db.password");
        JFrame frame = new JFrame("DBcatch Application");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel();


        // Add fetch button
        JButton btnGetData = new JButton("Get Data");
        btnGetData.setBounds(10,20,100, 40);

        // Reset button
        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(10, 20, 100, 40);
        btnReset.setVisible(false);

        btnReset.addActionListener(e -> {
            btnGetData.setVisible(true);
            btnReset.setVisible(false);
            label.setText("");
        });

        System.out.println(username);

        btnGetData.addActionListener(ev -> {

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etl_test", username, password);
                    Statement statement = conn.createStatement();

                    ResultSet resultSet = statement.executeQuery("SELECT id, email, firstName FROM user_data");

                    StringBuilder showData = new StringBuilder("<html>");

                    showData.append("Data from database connection: ").append(statement).append("<br>");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String email = resultSet.getString("email");
                        String name = resultSet.getString("firstName");
                        showData.append("ID: ").append(id).append(", E-mail: ").append(email).append(", Name: ").append(name).append("<br>");
                    }

                    showData.append("</html>");
                    label.setText(showData.toString());

                    resultSet.close();
                    statement.close();
                    conn.close();

                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            btnGetData.setVisible(false);
            btnReset.setVisible(true);

        });

        label.add(btnReset);
        label.add(btnGetData);
        frame.add(label);
        frame.setVisible(true);
    }
    public static String returnUsers(String uname) {
      return uname;
    };
}
