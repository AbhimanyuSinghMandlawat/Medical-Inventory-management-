import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Prescription extends JFrame {

    private JTable table;

    public Prescription() {
        setTitle("Prescription Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchPrescriptionData();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addPrescriptionButton = new JButton("Add Prescription");
        JButton removePrescriptionButton = new JButton("Remove Prescription");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addPrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the AddPrescription window
                new AddPrescription().setVisible(true);
            }
        });

        removePrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open removePrescription when remove Prescription button is clicked
                new removePrescription();
                dispose(); // Close the current window
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
        buttonPanel.add(addPrescriptionButton);
        buttonPanel.add(removePrescriptionButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchPrescriptionData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch prescription information
                String query = "SELECT * FROM Prescription";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Prescription ID");
                model.addColumn("Transaction ID");
                model.addColumn("Doctor ID");
                model.addColumn("Customer ID");
                model.addColumn("Prescription Data");

                while (resultSet.next()) {
                    int prescriptionID = resultSet.getInt("PrescriptionID");
                    int transactionID = resultSet.getInt("TransactionID");
                    int doctorID = resultSet.getInt("DoctorID");
                    int customerID = resultSet.getInt("CustomerID");
                    String prescriptionData = resultSet.getString("PrescriptionData");

                    model.addRow(new Object[]{prescriptionID, transactionID, doctorID, customerID, prescriptionData});
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
        SwingUtilities.invokeLater(Prescription::new);
    }
}
