import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginGUI extends JFrame {

    private JTextField authIDField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel authIDLabel = new JLabel("AuthID:");
        panel.add(authIDLabel);
        authIDField = new JTextField();
        panel.add(authIDField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String authID = authIDField.getText().trim();
                String password = String.valueOf(passwordField.getPassword());

                if (authenticateUser(authID, password)) {
                    dispose(); // Close login window
                    new ProfileGUI(authID); // Open profile window
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid AuthID or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(loginButton);

        add(panel);

        setVisible(true);
    }

    private boolean authenticateUser(String authID, String password) {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch user information
                String query = "SELECT * FROM authentication WHERE auth_id='" + authID + "' AND password='" + password + "'";
                ResultSet resultSet = statement.executeQuery(query);

                // Check if user exists
                return resultSet.next();

            } catch (SQLException e) {
                System.err.println("Error executing SQL query: " + e.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            System.out.println("Failed to establish connection to the database.");
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
    }
}
