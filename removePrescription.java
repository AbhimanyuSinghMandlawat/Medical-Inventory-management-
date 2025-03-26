import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class removePrescription extends JFrame {

    private JLabel lblPrescriptionID;
    private JTextField txtPrescriptionID;
    private JButton btnRemove;

    public removePrescription() {
        setTitle("Remove Prescription");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        lblPrescriptionID = new JLabel("Prescription ID:");
        txtPrescriptionID = new JTextField(10);
        btnRemove = new JButton("Remove");

        // Set layout
        setLayout(new GridLayout(2, 2));

        // Add components to the frame
        add(lblPrescriptionID);
        add(txtPrescriptionID);
        add(new JLabel()); // Placeholder for alignment
        add(btnRemove);

        // Add action listener to the Remove button
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePrescription();
            }
        });

        setVisible(true);
    }

    private void removePrescription() {
        // Get the PrescriptionID from the text field
        int prescriptionID = Integer.parseInt(txtPrescriptionID.getText().trim());

        // Establish connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Prepare the delete statement
                String deleteQuery = "DELETE FROM Prescription WHERE PrescriptionID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, prescriptionID);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Prescription removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No prescription found with the given ID.");
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
        SwingUtilities.invokeLater(removePrescription::new);
    }
}
