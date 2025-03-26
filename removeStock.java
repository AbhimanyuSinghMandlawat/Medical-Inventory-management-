import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class removeStock extends JFrame {

    private JTextField stockIDField;

    public removeStock() {
        setTitle("Remove Stock");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create label and text field for Stock ID
        JLabel stockIDLabel = new JLabel("Stock ID:");
        stockIDField = new JTextField(10);

        // Create button to remove stock
        JButton removeButton = new JButton("Remove");

        // Add action listener to remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStockFromDatabase();
            }
        });

        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(stockIDLabel);
        panel.add(stockIDField);
        panel.add(new JLabel()); // Placeholder
        panel.add(removeButton);

        // Add panel to frame
        add(panel);

        setVisible(true);
    }

    private void removeStockFromDatabase() {
        // Get the Stock ID from the text field
        String stockIDText = stockIDField.getText().trim();
        if (!stockIDText.isEmpty()) {
            try {
                int stockID = Integer.parseInt(stockIDText);

                // Establishing connection to the database
                Connection connection = Connect.getConnection();
                if (connection != null) {
                    try {
                        // Creating statement
                        Statement statement = connection.createStatement();

                        // Executing delete query
                        String query = "DELETE FROM Stock WHERE StockID = " + stockID;
                        int rowsAffected = statement.executeUpdate(query);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(removeStock.this, "Stock removed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(removeStock.this, "No stock found with the provided Stock ID.");
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(removeStock.this, "Error executing SQL query: " + ex.getMessage());
                    } finally {
                        // Closing connection
                        Connect.closeConnection(connection);
                    }
                } else {
                    JOptionPane.showMessageDialog(removeStock.this, "Failed to establish connection to the database.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(removeStock.this, "Invalid Stock ID. Please enter a valid integer.");
            }
        } else {
            JOptionPane.showMessageDialog(removeStock.this, "Please enter a Stock ID.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(removeStock::new);
    }
}
