import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class removeStaff extends JFrame {

    private JTextField staffIDField;

    public removeStaff() {
        setTitle("Remove Staff");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create label and text field for Staff ID
        JLabel staffIDLabel = new JLabel("Staff ID:");
        staffIDField = new JTextField(10);

        // Create button to remove staff
        JButton removeButton = new JButton("Remove");

        // Add action listener to remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStaffFromDatabase();
            }
        });

        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(staffIDLabel);
        panel.add(staffIDField);
        panel.add(new JLabel()); // Placeholder
        panel.add(removeButton);

        // Add panel to frame
        add(panel);

        setVisible(true);
    }

    private void removeStaffFromDatabase() {
        // Get the Staff ID from the text field
        String staffIDText = staffIDField.getText().trim();
        if (!staffIDText.isEmpty()) {
            try {
                int staffID = Integer.parseInt(staffIDText);

                // Establishing connection to the database
                Connection connection = Connect.getConnection();
                if (connection != null) {
                    try {
                        // Creating statement
                        Statement statement = connection.createStatement();

                        // Executing delete query
                        String query = "DELETE FROM Staff WHERE StaffID = " + staffID;
                        int rowsAffected = statement.executeUpdate(query);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(removeStaff.this, "Staff removed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(removeStaff.this, "No staff found with the provided Staff ID.");
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(removeStaff.this, "Error executing SQL query: " + ex.getMessage());
                    } finally {
                        // Closing connection
                        Connect.closeConnection(connection);
                    }
                } else {
                    JOptionPane.showMessageDialog(removeStaff.this, "Failed to establish connection to the database.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(removeStaff.this, "Invalid Staff ID. Please enter a valid integer.");
            }
        } else {
            JOptionPane.showMessageDialog(removeStaff.this, "Please enter a Staff ID.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(removeStaff::new);
    }
}
