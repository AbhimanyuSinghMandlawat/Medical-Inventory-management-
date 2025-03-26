import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RemoveTrans extends JFrame {

    private JTextField transactionIDField;

    public RemoveTrans() {
        setTitle("Remove Transaction");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create label and text field for Transaction ID
        JLabel transactionIDLabel = new JLabel("Transaction ID:");
        transactionIDField = new JTextField(10);

        // Create button to remove transaction
        JButton removeButton = new JButton("Remove");

        // Add action listener to remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTransactionFromDatabase();
            }
        });

        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(transactionIDLabel);
        panel.add(transactionIDField);
        panel.add(new JLabel()); // Placeholder
        panel.add(removeButton);

        // Add panel to frame
        add(panel);

        setVisible(true);
    }

    private void removeTransactionFromDatabase() {
        // Get the Transaction ID from the text field
        String transactionIDText = transactionIDField.getText().trim();
        if (!transactionIDText.isEmpty()) {
            try {
                int transactionID = Integer.parseInt(transactionIDText);

                // Establishing connection to the database
                Connection connection = Connect.getConnection();
                if (connection != null) {
                    try {
                        // Creating statement
                        Statement statement = connection.createStatement();

                        // Executing delete query
                        String query = "DELETE FROM Transaction WHERE TransactionID = " + transactionID;
                        int rowsAffected = statement.executeUpdate(query);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(RemoveTrans.this, "Transaction removed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(RemoveTrans.this, "No transaction found with the provided Transaction ID.");
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(RemoveTrans.this, "Error executing SQL query: " + ex.getMessage());
                    } finally {
                        // Closing connection
                        Connect.closeConnection(connection);
                    }
                } else {
                    JOptionPane.showMessageDialog(RemoveTrans.this, "Failed to establish connection to the database.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(RemoveTrans.this, "Invalid Transaction ID. Please enter a valid integer.");
            }
        } else {
            JOptionPane.showMessageDialog(RemoveTrans.this, "Please enter a Transaction ID.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RemoveTrans::new);
    }
}
