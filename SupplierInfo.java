import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SupplierInfo extends JFrame {

    private JTable table;

    public SupplierInfo() {
        setTitle("Supplier Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchSupplierData();

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addSupplierButton = new JButton("Add Supplier");
        JButton removeSupplierButton = new JButton("Remove Supplier");
        JButton backButton = new JButton("Back to Home");

        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the AddSup class
                new AddSup().setVisible(true);
            }
        });

        removeSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RemoveSup class
                new RemoveSup().setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window and return to the HomeGUI
                dispose();
                new HomeGUI().setVisible(true);
            }
        });

        buttonPanel.add(addSupplierButton);
        buttonPanel.add(removeSupplierButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchSupplierData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch supplier information
                String query = "SELECT * FROM Supplier";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Supplier ID");
                model.addColumn("Supplier Name");
                model.addColumn("Company Name");
                model.addColumn("Mobile Number");
                model.addColumn("Medicine Info");
                model.addColumn("Email ID");
                model.addColumn("Transaction Detail");
                model.addColumn("Transaction Mode");
                model.addColumn("Delivery Date");
                model.addColumn("Delivery Time");

                while (resultSet.next()) {
                    int supplierID = resultSet.getInt("SupplierID");
                    String supplierName = resultSet.getString("SupplierName");
                    String companyName = resultSet.getString("SupplierCompanyName");
                    String mobileNumber = resultSet.getString("MobileNumber");
                    String medicineInfo = resultSet.getString("MedicinInfo");
                    String emailID = resultSet.getString("emailID");
                    String transactionDetail = resultSet.getString("TransactionDetail");
                    String transactionMode = resultSet.getString("TransactionMode");
                    String deliveryDate = resultSet.getString("DeliveryDate");
                    String deliveryTime = resultSet.getString("DeliveryTime");

                    model.addRow(new Object[]{supplierID, supplierName, companyName, mobileNumber,
                            medicineInfo, emailID, transactionDetail, transactionMode, deliveryDate, deliveryTime});
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
        SwingUtilities.invokeLater(SupplierInfo::new);
    }
}
