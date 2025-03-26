import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addCus extends JFrame {

    private JLabel[] labels;
    private JTextField[] fields;
    private String[] labelTexts = {"CustomerID:", "Name:", "Gender:", "Age:", "Mobile Number:", "Email:"};
    private int numOfFields;

    private JButton addButton;

    public addCus() {
        setTitle("Add Customer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        numOfFields = labelTexts.length;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Adding labels and text fields
        labels = new JLabel[numOfFields];
        fields = new JTextField[numOfFields];
        for (int i = 0; i < numOfFields; i++) {
            labels[i] = new JLabel(labelTexts[i]);
            gbc.gridx = 0;
            panel.add(labels[i], gbc);

            fields[i] = new JTextField(15);
            gbc.gridx = 1;
            panel.add(fields[i], gbc);
            gbc.gridy++;
        }

        // Add button
        addButton = new JButton("Add Customer");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        // Add action listener to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomerToDatabase();
            }
        });

        add(panel);
        pack();
        setVisible(true);
    }

    private void addCustomerToDatabase() {
        // Retrieve input from text fields
        String customerID = fields[0].getText();
        String name = fields[1].getText();
        String gender = fields[2].getText();
        int age = Integer.parseInt(fields[3].getText());
        String mobileNumber = fields[4].getText();
        String email = fields[5].getText();

        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating SQL query to insert customer data
                String query = "INSERT INTO Customer (CustomerID, CustomerName, Gender, Age, MobileNumber, Email) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, customerID);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, gender);
                preparedStatement.setInt(4, age);
                preparedStatement.setString(5, mobileNumber);
                preparedStatement.setString(6, email);

                // Executing the query
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(addCus.this, "Customer added successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(addCus.this, "Failed to add customer!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(addCus.this, "Error: " + ex.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            System.out.println("Failed to establish connection to the database.");
        }
    }

    private void clearFields() {
        // Clear all text fields
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(addCus::new);
    }
}
