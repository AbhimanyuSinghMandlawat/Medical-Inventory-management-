import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class removeTransMedic extends JFrame {

    private JLabel lblTransactionMedicinID;
    private JTextField txtTransactionMedicinID;
    private JButton btnRemove;

    public removeTransMedic() {
        setTitle("Remove Transaction Medicin");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        lblTransactionMedicinID = new JLabel("TransactionMedicinID:");
        txtTransactionMedicinID = new JTextField(10);
        btnRemove = new JButton("Remove");

        // Set layout
        setLayout(new GridLayout(2, 2));

        // Add components to the frame
        add(lblTransactionMedicinID);
        add(txtTransactionMedicinID);
        add(new JLabel()); // Placeholder for alignment
        add(btnRemove);

        // Add action listener to the Remove button
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTransactionMedicin();
            }
        });

        setVisible(true);
    }

    private void removeTransactionMedicin() {
        // Get the TransactionMedicinID from the text field
        int transactionMedicinID = Integer.parseInt(txtTransactionMedicinID.getText().trim());

        // Establish connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Prepare the delete statement
                String deleteQuery = "DELETE FROM TransactionMedicin WHERE TransactionMedicinID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, transactionMedicinID);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Transaction Medicin removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No Transaction Medicin found with the given ID.");
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
        SwingUtilities.invokeLater(removeTransMedic::new);
    }
}
