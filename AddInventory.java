import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddInventory extends JFrame {

    private JLabel idLabel, nameLabel, descLabel, categoryLabel, quantityLabel, priceLabel, reorderLabel, supplierLabel;
    private JTextField idField, nameField, descField, categoryField, quantityField, priceField, reorderField, supplierField;
    private JButton addButton;

    public AddInventory() {
        setTitle("Add Inventory");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        idLabel = new JLabel("Inventory ID:");
        nameLabel = new JLabel("Product Name:");
        descLabel = new JLabel("Description:");
        categoryLabel = new JLabel("Category:");
        quantityLabel = new JLabel("Quantity:");
        priceLabel = new JLabel("Unit Price:");
        reorderLabel = new JLabel("Reorder Level:");
        supplierLabel = new JLabel("Supplier ID:");

        idField = new JTextField(10);
        nameField = new JTextField(20);
        descField = new JTextField(20);
        categoryField = new JTextField(10);
        quantityField = new JTextField(5);
        priceField = new JTextField(10);
        reorderField = new JTextField(5);
        supplierField = new JTextField(10);

        addButton = new JButton("Add Inventory");

        // Add action listener to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInventoryToDatabase();
            }
        });

        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(descLabel);
        panel.add(descField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(reorderLabel);
        panel.add(reorderField);
        panel.add(supplierLabel);
        panel.add(supplierField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(addButton);

        // Add panel to the frame
        add(panel);
        setVisible(true);
    }

    private void addInventoryToDatabase() {
        int inventoryID = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String description = descField.getText();
        String category = categoryField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        int reorderLevel = Integer.parseInt(reorderField.getText());
        int supplierID = Integer.parseInt(supplierField.getText());

        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating SQL query to insert inventory data
                String query = "INSERT INTO Inventory (InventoryID, ProductName, Description, Category, Quantity, UnitPrice, ReorderLevel, SupplierID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, inventoryID);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, description);
                preparedStatement.setString(4, category);
                preparedStatement.setInt(5, quantity);
                preparedStatement.setDouble(6, price);
                preparedStatement.setInt(7, reorderLevel);
                preparedStatement.setInt(8, supplierID);

                // Executing the query
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(AddInventory.this, "Inventory added successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(AddInventory.this, "Failed to add inventory!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(AddInventory.this, "Error: " + ex.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            System.out.println("Failed to establish connection to the database.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        descField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
        reorderField.setText("");
        supplierField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddInventory::new);
    }
}

