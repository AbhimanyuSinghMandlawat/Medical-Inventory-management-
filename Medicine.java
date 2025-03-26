import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Medicine extends JFrame {

    private JTable table;

    public Medicine() {
        setTitle("Medicine Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchMedicineData();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addMedicineButton = new JButton("Add Medicine");
        JButton removeMedicineButton = new JButton("Remove Medicine");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addMedicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open AddMedic class
                dispose(); // Close the current window
                new AddMedic().setVisible(true); // Show AddMedic window
            }
        });

        removeMedicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open removeMedic class
                dispose(); // Close the current window
                new removeMedic().setVisible(true); // Show removeMedic window
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
        buttonPanel.add(addMedicineButton);
        buttonPanel.add(removeMedicineButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchMedicineData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch medicine information
                String query = "SELECT * FROM Medicine";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Medicine ID");
                model.addColumn("Medicine Name");
                model.addColumn("Company Name");
                model.addColumn("Purchase Rate");
                model.addColumn("Expiry Date");

                while (resultSet.next()) {
                    int medicineID = resultSet.getInt("MedicineID");
                    String medicineName = resultSet.getString("MedicineName");
                    String companyName = resultSet.getString("CompanyName");
                    double purchaseRate = resultSet.getDouble("PurchaseRate");
                    String expiryDate = resultSet.getString("ExpiryDate");

                    model.addRow(new Object[]{medicineID, medicineName, companyName, purchaseRate, expiryDate});
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
        SwingUtilities.invokeLater(Medicine::new);
    }
}
