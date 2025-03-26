import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class removeOrder extends JFrame {

    private JLabel lblOrderID;
    private JTextField txtOrderID;
    private JButton btnRemove;

    public removeOrder() {
        setTitle("Remove Order");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        lblOrderID = new JLabel("Order ID:");
        txtOrderID = new JTextField(10);
        btnRemove = new JButton("Remove");

        // Set layout
        setLayout(new GridLayout(2, 2));

        // Add components to the frame
        add(lblOrderID);
        add(txtOrderID);
        add(new JLabel()); // Placeholder for alignment
        add(btnRemove);

        // Add action listener to the Remove button
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeOrder();
            }
        });

        setVisible(true);
    }

    private void removeOrder() {
        // Get the OrderID from the text field
        int orderID = Integer.parseInt(txtOrderID.getText().trim());

        // Establish connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Prepare the delete statement
                String deleteQuery = "DELETE FROM OrderDetails WHERE OrderID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, orderID);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Order removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No order found with the given ID.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to establish connection to the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(removeOrder::new);
    }
}
