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

public class Doctor extends JFrame {

    private JTable table;

    public Doctor() {
        setTitle("Doctor Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addDoctorButton = new JButton("Add Doctor");
        JButton removeDoctorButton = new JButton("Remove Doctor");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open AddDoc.java when Add Doctor button is clicked
                dispose(); // Close the current window
                new AddDoc().setVisible(true); // Show AddDoc window
            }
        });

        removeDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open removeDoc.java when Remove Doctor button is clicked
                dispose(); // Close the current window
                new removeDoc().setVisible(true); // Show removeDoc window
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
        buttonPanel.add(addDoctorButton);
        buttonPanel.add(removeDoctorButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchDoctorData();

        setVisible(true);
    }

    private void fetchDoctorData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch doctor information
                String query = "SELECT * FROM Doctor";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Doctor ID");
                model.addColumn("Doctor Name");
                model.addColumn("Specialization");

                while (resultSet.next()) {
                    int doctorID = resultSet.getInt("DoctorID");
                    String doctorName = resultSet.getString("DoctorName");
                    String specialization = resultSet.getString("Specialization");

                    model.addRow(new Object[]{doctorID, doctorName, specialization});
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
        SwingUtilities.invokeLater(Doctor::new);
    }
}
