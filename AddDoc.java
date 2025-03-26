import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDoc extends JFrame {

    private JLabel idLabel, nameLabel, specLabel;
    private JTextField idField, nameField, specField;
    private JButton addButton;

    public AddDoc() {
        setTitle("Add Doctor");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        idLabel = new JLabel("Doctor ID:");
        nameLabel = new JLabel("Name:");
        specLabel = new JLabel("Specialization:");

        idField = new JTextField(5);
        nameField = new JTextField(20);
        specField = new JTextField(20);

        addButton = new JButton("Add Doctor");

        // Add action listener to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctorToDatabase();
            }
        });

        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(specLabel);
        panel.add(specField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(addButton);

        // Add panel to the frame
        add(panel);
        setVisible(true);
    }

    private void addDoctorToDatabase() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String specialization = specField.getText();

        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating SQL query to insert doctor data
                String query = "INSERT INTO Doctor (DoctorID, DoctorName, Specialization) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, specialization);

                // Executing the query
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(AddDoc.this, "Doctor added successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(AddDoc.this, "Failed to add doctor!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(AddDoc.this, "Error: " + ex.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            System.out.println("Failed to establish connection to the database.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        specField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddDoc::new);
    }
}
