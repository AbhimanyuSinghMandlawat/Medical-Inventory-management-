import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class removeDoc extends JFrame {

    private JTextField doctorIDField;
    private JButton removeButton;

    public removeDoc() {
        setTitle("Remove Doctor");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        JLabel doctorIDLabel = new JLabel("Doctor ID:");
        doctorIDField = new JTextField();
        removeButton = new JButton("Remove");

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeDoctor();
            }
        });

        panel.add(doctorIDLabel);
        panel.add(doctorIDField);
        panel.add(new JLabel()); // Placeholder for alignment
        panel.add(removeButton);

        add(panel);
        setVisible(true);
    }

    private void removeDoctor() {
        String doctorIDText = doctorIDField.getText().trim();
        if (doctorIDText.isEmpty()) {
            JOptionPane.showMessageDialog(removeDoc.this, "Please enter Doctor ID.");
            return;
        }

        int doctorID;
        try {
            doctorID = Integer.parseInt(doctorIDText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(removeDoc.this, "Invalid Doctor ID. Please enter a valid integer value.");
            return;
        }

        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to remove doctor
                String query = "DELETE FROM Doctor WHERE DoctorID = " + doctorID;
                int rowsAffected = statement.executeUpdate(query);

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(removeDoc.this, "Doctor with ID " + doctorID + " removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(removeDoc.this, "Doctor with ID " + doctorID + " not found.");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(removeDoc.this, "Error removing doctor: " + e.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            JOptionPane.showMessageDialog(removeDoc.this, "Failed to establish connection to the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(removeDoc::new);
    }
}

