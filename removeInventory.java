import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class removeInventory extends JFrame {

    public removeInventory() {
        setTitle("Remove Inventory");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel inventoryIDLabel = new JLabel("Inventory ID:");
        JTextField inventoryIDField = new JTextField();
        JButton removeButton = new JButton("Remove");

        panel.add(inventoryIDLabel);
        panel.add(inventoryIDField);
        panel.add(new JLabel()); // Placeholder
        panel.add(removeButton);

        add(panel, BorderLayout.CENTER);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the inventory ID from the text field
                String inventoryIDText = inventoryIDField.getText().trim();
                if (!inventoryIDText.isEmpty()) {
                    try {
                        int inventoryID = Integer.parseInt(inventoryIDText);

                        // Remove the inventory with the given ID
                        removeInventoryFromDatabase(inventoryID);

                        // Show success message
                        JOptionPane.showMessageDialog(removeInventory.this, "Inventory removed successfully.");

                        // Clear the text field
                        inventoryIDField.setText("");

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(removeInventory.this, "Invalid inventory ID. Please enter a valid integer.");
                    }
                } else {
                    JOptionPane.showMessageDialog(removeInventory.this, "Please enter an inventory ID.");
                }
            }
        });

        setVisible(true);
    }

    private void removeInventoryFromDatabase(int inventoryID) {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing delete query
                String query = "DELETE FROM Inventory WHERE InventoryID = " + inventoryID;
                statement.executeUpdate(query);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(removeInventory.this, "Error removing inventory: " + e.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            JOptionPane.showMessageDialog(removeInventory.this, "Failed to establish connection to the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(removeInventory::new);
    }
}
