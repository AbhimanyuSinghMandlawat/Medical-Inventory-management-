import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPrescription extends JFrame {

    private JLabel[] labels;
    private JTextField[] fields;
    private String[] labelTexts = {"PrescriptionID:", "TransactionID:", "DoctorID:", "CustomerID:", "PrescriptionData:"};
    private int numOfFields;

    private JButton addButton;

    public AddPrescription() {
        setTitle("Add Prescription");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        numOfFields = labelTexts.length;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Adding labels and text fields
        labels = new JLabel[numOfFields];
        fields = new JTextField[numOfFields];
        for (int i = 0; i < numOfFields; i++) {
            labels[i] = new JLabel(labelTexts[i]);
            gbc.gridx = 0;
            panel.add(labels[i], gbc);

            fields[i] = new JTextField(15);
            gbc.gridx = 1;
            panel.add(fields[i], gbc);
            gbc.gridy++;
        }

        // Add button
        addButton = new JButton("Add Prescription");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        // Add action listener to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPrescriptionToDatabase();
            }
        });

        add(panel);
        pack();
        setVisible(true);
    }

    private void addPrescriptionToDatabase() {
        // Retrieve input from text fields
        int prescriptionID = Integer.parseInt(fields[0].getText());
        int transactionID = Integer.parseInt(fields[1].getText());
        int doctorID = Integer.parseInt(fields[2].getText());
        int customerID = Integer.parseInt(fields[3].getText());
        String prescriptionData = fields[4].getText();

        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating SQL query to insert prescription data
                String query = "INSERT INTO Prescription (PrescriptionID, TransactionID, DoctorID, CustomerID, PrescriptionData) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, prescriptionID);
                preparedStatement.setInt(2, transactionID);
                preparedStatement.setInt(3, doctorID);
                preparedStatement.setInt(4, customerID);
                preparedStatement.setString(5, prescriptionData);

                // Executing the query
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(AddPrescription.this, "Prescription added successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(AddPrescription.this, "Failed to add prescription!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(AddPrescription.this, "Error: " + ex.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            System.out.println("Failed to establish connection to the database.");
        }
    }

    private void clearFields() {
        // Clear all text fields
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddPrescription::new);
    }
}

