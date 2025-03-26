import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer extends JFrame {

    private JTable table;

    public Customer() {
        setTitle("Customer Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addCustomerButton = new JButton("Add Customer");
        JButton removeCustomerButton = new JButton("Remove Customer");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open addCus class
                dispose(); // Close the current window
                new addCus().setVisible(true); // Show addCus window
            }
        });

        removeCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open removeCus class
                dispose(); // Close the current window
                new removeCus().setVisible(true); // Show removeCus window
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to HomeGUI
                dispose(); // Close the current window
                new HomeGUI().setVisible(true); // Show HomeGUI window
            }
        });

        // Add buttons to button panel
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(removeCustomerButton);
        buttonPanel.add(backButton);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        // Add scroll pane with table to the frame's content pane at the CENTER position
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchCustomerData();

        setVisible(true);
    }

    private void fetchCustomerData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch customer information
                String query = "SELECT * FROM Customer";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Customer ID");
                model.addColumn("Customer Name");
                model.addColumn("Gender");
                model.addColumn("Age");
                model.addColumn("Mobile Number");
                model.addColumn("Email");

                while (resultSet.next()) {
                    int customerID = resultSet.getInt("CustomerID");
                    String customerName = resultSet.getString("CustomerName");
                    String gender = resultSet.getString("Gender");
                    int age = resultSet.getInt("Age");
                    String mobileNumber = resultSet.getString("MobileNumber");
                    String email = resultSet.getString("Email");

                    model.addRow(new Object[]{customerID, customerName, gender, age, mobileNumber, email});
                }

                // Set table model
                table.setModel(model);

            } catch (SQLException e) {
                System.err.println("Error executing SQL query: " + e.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            System.out.println("Failed to establish connection to the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Customer::new);
    }
}
