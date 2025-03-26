import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RemoveSup extends JFrame {

    private JTextField supplierIDField;

    public RemoveSup() {
        setTitle("Remove Supplier");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create label and text field for Supplier ID
        JLabel supplierIDLabel = new JLabel("Supplier ID:");
        supplierIDField = new JTextField(10);

        // Create button to remove supplier
        JButton removeButton = new JButton("Remove");

        // Add action listener to remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSupplierFromDatabase();
            }
        });

        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(supplierIDLabel);
        panel.add(supplierIDField);
        panel.add(new JLabel()); // Placeholder
        panel.add(removeButton);

        // Add panel to frame
        add(panel);

        setVisible(true);
    }

    private void removeSupplierFromDatabase() {
        // Get the Supplier ID from the text field
        String supplierIDText = supplierIDField.getText().trim();
        if (!supplierIDText.isEmpty()) {
            try {
                int supplierID = Integer.parseInt(supplierIDText);

                // Establishing connection to the database
                Connection connection = Connect.getConnection();
                if (connection != null) {
                    try {
                        // Creating statement
                        Statement statement = connection.createStatement();

                        // Executing delete query
                        String query = "DELETE FROM Supplier WHERE SupplierID = " + supplierID;
                        int rowsAffected = statement.executeUpdate(query);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(RemoveSup.this, "Supplier removed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(RemoveSup.this, "No supplier found with the provided Supplier ID.");
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(RemoveSup.this, "Error executing SQL query: " + ex.getMessage());
                    } finally {
                        // Closing connection
                        Connect.closeConnection(connection);
                    }
                } else {
                    JOptionPane.showMessageDialog(RemoveSup.this, "Failed to establish connection to the database.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(RemoveSup.this, "Invalid Supplier ID. Please enter a valid integer.");
            }
        } else {
            JOptionPane.showMessageDialog(RemoveSup.this, "Please enter a Supplier ID.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RemoveSup::new);
    }
}
