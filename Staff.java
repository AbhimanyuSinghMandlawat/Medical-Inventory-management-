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

public class Staff extends JFrame {

    private JTable table;

    public Staff() {
        setTitle("Staff Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchStaffData();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addStaffButton = new JButton("Add Staff");
        JButton removeStaffButton = new JButton("Remove Staff");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open AddStaff window
                dispose(); // Close the current window
                new AddStaff().setVisible(true); // Open AddStaff window
            }
        });

        removeStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open RemoveStaff window
                new removeStaff().setVisible(true);
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
        buttonPanel.add(addStaffButton);
        buttonPanel.add(removeStaffButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchStaffData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch staff information
                String query = "SELECT * FROM staff";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Staff ID");
                model.addColumn("Name");
                model.addColumn("Email");
                model.addColumn("Auth ID");
                model.addColumn("Role");
                model.addColumn("Date of Joining");
                model.addColumn("Salary");
                model.addColumn("Contact Number");
                model.addColumn("Shop Name");

                while (resultSet.next()) {
                    int staffID = resultSet.getInt("staffID");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    int authID = resultSet.getInt("auth_id");
                    String role = resultSet.getString("role");
                    String dateOfJoining = resultSet.getString("date_of_joining");
                    double salary = resultSet.getDouble("salary");
                    String contactNumber = resultSet.getString("contact_number");
                    String shopName = resultSet.getString("ShopName");

                    model.addRow(new Object[]{staffID, name, email, authID, role, dateOfJoining, salary, contactNumber, shopName});
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
        SwingUtilities.invokeLater(Staff::new);
    }
}
