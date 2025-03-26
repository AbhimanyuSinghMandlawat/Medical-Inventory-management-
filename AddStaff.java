import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStaff extends JFrame {

    private JLabel staffIdLabel, nameLabel, emailLabel, authIdLabel, roleLabel, joiningDateLabel, salaryLabel, contactNumberLabel, shopNameLabel;
    private JTextField staffIdField, nameField, emailField, authIdField, roleField, joiningDateField, salaryField, contactNumberField, shopNameField;
    private JButton addButton;

    public AddStaff() {
        setTitle("Add Staff Information");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        staffIdLabel = new JLabel("Staff ID:");
        nameLabel = new JLabel("Name:");
        emailLabel = new JLabel("Email:");
        authIdLabel = new JLabel("Auth ID:");
        roleLabel = new JLabel("Role:");
        joiningDateLabel = new JLabel("Date of Joining (YYYY-MM-DD):");
        salaryLabel = new JLabel("Salary:");
        contactNumberLabel = new JLabel("Contact Number:");
        shopNameLabel = new JLabel("Shop Name:");

        staffIdField = new JTextField(20);
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        authIdField = new JTextField(20);
        roleField = new JTextField(20);
        joiningDateField = new JTextField(20);
        salaryField = new JTextField(20);
        contactNumberField = new JTextField(20);
        shopNameField = new JTextField(20);

        addButton = new JButton("Add Staff");

        // Set layout
        setLayout(new GridLayout(10, 2));

        // Add components to the frame
        add(staffIdLabel);
        add(staffIdField);
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(authIdLabel);
        add(authIdField);
        add(roleLabel);
        add(roleField);
        add(joiningDateLabel);
        add(joiningDateField);
        add(salaryLabel);
        add(salaryField);
        add(contactNumberLabel);
        add(contactNumberField);
        add(shopNameLabel);
        add(shopNameField);
        add(addButton);

        // Add action listener to add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStaff();
            }
        });

        setVisible(true);
    }

    private void addStaff() {
        // Get user input
        int staffId = Integer.parseInt(staffIdField.getText());
        String name = nameField.getText();
        String email = emailField.getText();
        int authId = Integer.parseInt(authIdField.getText());
        String role = roleField.getText();
        String joiningDate = joiningDateField.getText();
        double salary = Double.parseDouble(salaryField.getText());
        String contactNumber = contactNumberField.getText();
        String shopName = shopNameField.getText();

        // Insert staff data into the database
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connect.getConnection();
            if (connection != null) {
                String query = "INSERT INTO staff (staffID, name, email, auth_id, role, date_of_joining, salary, contact_number, ShopName) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                // Set values for the prepared statement
                preparedStatement.setInt(1, staffId);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, email);
                preparedStatement.setInt(4, authId);
                preparedStatement.setString(5, role);
                preparedStatement.setString(6, joiningDate);
                preparedStatement.setDouble(7, salary);
                preparedStatement.setString(8, contactNumber);
                preparedStatement.setString(9, shopName);

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Staff added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add staff.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to establish connection to the database.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } finally {
            // Close PreparedStatement and Connection
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new AddStaff();
    }
}

