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

public class Inventory extends JFrame {

    private JTable table;

    public Inventory() {
        setTitle("Inventory Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchInventoryData();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addInventoryButton = new JButton("Add Inventory");
        JButton removeInventoryButton = new JButton("Remove Inventory");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open AddInventory window
                dispose(); // Close the current window
                new AddInventory().setVisible(true); // Show AddInventory window
            }
        });

        removeInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open removeInventory window
                dispose(); // Close the current window
                new removeInventory().setVisible(true); // Show removeInventory window
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
        buttonPanel.add(addInventoryButton);
        buttonPanel.add(removeInventoryButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchInventoryData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch inventory information
                String query = "SELECT * FROM Inventory";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Inventory ID");
                model.addColumn("Product Name");
                model.addColumn("Description");
                model.addColumn("Category");
                model.addColumn("Quantity");
                model.addColumn("Unit Price");
                model.addColumn("Reorder Level");
                model.addColumn("Supplier ID");

                while (resultSet.next()) {
                    int inventoryID = resultSet.getInt("InventoryID");
                    String productName = resultSet.getString("ProductName");
                    String description = resultSet.getString("Description");
                    String category = resultSet.getString("Category");
                    int quantity = resultSet.getInt("Quantity");
                    double unitPrice = resultSet.getDouble("UnitPrice");
                    int reorderLevel = resultSet.getInt("ReorderLevel");
                    int supplierID = resultSet.getInt("SupplierID");

                    model.addRow(new Object[]{inventoryID, productName, description, category,
                            quantity, unitPrice, reorderLevel, supplierID});
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
        SwingUtilities.invokeLater(Inventory::new);
    }
}
