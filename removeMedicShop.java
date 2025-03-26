import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class removeMedicShop extends JFrame {

    private JLabel lblShopID;
    private JTextField txtShopID;
    private JButton btnRemove;

    public removeMedicShop() {
        setTitle("Remove Medical Shop");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        lblShopID = new JLabel("Shop ID:");
        txtShopID = new JTextField(10);
        btnRemove = new JButton("Remove");

        // Set layout
        setLayout(new GridLayout(2, 2));

        // Add components to the frame
        add(lblShopID);
        add(txtShopID);
        add(new JLabel()); // Placeholder for alignment
        add(btnRemove);

        // Add action listener to the Remove button
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMedicalShop();
            }
        });

        setVisible(true);
    }

    private void removeMedicalShop() {
        // Get the ShopID from the text field
        int shopID = Integer.parseInt(txtShopID.getText().trim());

        // Establish connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Prepare the delete statement
                String deleteQuery = "DELETE FROM MedicalShop WHERE ShopID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, shopID);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Medical Shop removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No Medical Shop found with the given ID.");
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
        SwingUtilities.invokeLater(removeMedicShop::new);
    }
}

