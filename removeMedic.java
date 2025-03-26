import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class removeMedic extends JFrame {

    private JLabel lblMedicineID;
    private JTextField txtMedicineID;
    private JButton btnRemove;

    public removeMedic() {
        setTitle("Remove Medicine");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        lblMedicineID = new JLabel("MedicineID:");
        txtMedicineID = new JTextField(10);
        btnRemove = new JButton("Remove");

        // Set layout
        setLayout(new GridLayout(2, 2));

        // Add components to the frame
        add(lblMedicineID);
        add(txtMedicineID);
        add(new JLabel()); // Placeholder for alignment
        add(btnRemove);

        // Add action listener to the Remove button
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMedicine();
            }
        });

        setVisible(true);
    }

    private void removeMedicine() {
        // Get the MedicineID from the text field
        int medicineID = Integer.parseInt(txtMedicineID.getText().trim());

        // Establish connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Prepare the delete statement
                String deleteQuery = "DELETE FROM Medicine WHERE MedicineID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, medicineID);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Medicine removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No medicine found with the given ID.");
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
        SwingUtilities.invokeLater(removeMedic::new);
    }
}
