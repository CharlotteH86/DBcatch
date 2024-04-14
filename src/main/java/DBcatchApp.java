import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.*;

public class DBcatchApp {

    static JProgressBar pro;


    public static void main(String[] args) {
        String uname = "root";
        String pass = "root";
        JFrame frame = new JFrame("DBcatch Application");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel();

        // Progressbar
        //JProgressBar pro = new JProgressBar();
        /*pro = new JProgressBar();
        pro.setBounds(200, 20, 200, 50);
        pro.setStringPainted(true);
        pro.setVisible(true);
        label.add(pro);
        fill();*/


        // Add fetch button
        JButton btnGetData = new JButton("Get Data");
        btnGetData.setBounds(10,20,100, 40);

        // Reset button
        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(10, 20, 100, 40);
        btnReset.setVisible(false);

        btnReset.addActionListener(re -> {
            btnGetData.setVisible(true);
            btnReset.setVisible(false);
            label.setText("");
        });



        btnGetData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ev) {

                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etl_test", uname, pass);
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

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                btnGetData.setVisible(false);
                btnReset.setVisible(true);

            }
        });

        label.add(btnReset);
        label.add(btnGetData);
        frame.add(label);

        frame.setVisible(true);

        }

    static public void fill() {
        int i = 0;
        try {
            while (i <= 100) {
                // fill the menu bar
                pro.setValue(i + 10);

                // delay the thread
                Thread.sleep(1000);
                i += 20;
            }
        }
        catch (Exception e) {
        }
    };

}
