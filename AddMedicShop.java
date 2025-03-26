import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddMedicShop extends JFrame {

    private JLabel idLabel, nameLabel, locationLabel, contactLabel, staffIDLabel, supplierIDLabel;
    private JTextField idField, nameField, locationField, contactField, staffIDField, supplierIDField;
    private JButton addButton;

    public AddMedicShop() {
        setTitle("Add Medical Shop");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        idLabel = new JLabel("Shop ID:");
        nameLabel = new JLabel("Shop Name:");
        locationLabel = new JLabel("Location:");
        contactLabel = new JLabel("Contact Info:");
        staffIDLabel = new JLabel("Staff ID:");
        supplierIDLabel = new JLabel("Supplier ID:");

        idField = new JTextField(5);
        nameField = new JTextField(20);
        locationField = new JTextField(20);
        contactField = new JTextField(20);
        staffIDField = new JTextField(10);
        supplierIDField = new JTextField(10);

        addButton = new JButton("Add Medical Shop");

        // Add action listener to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMedicalShopToDatabase();
            }
        });

        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(contactLabel);
        panel.add(contactField);
        panel.add(staffIDLabel);
        panel.add(staffIDField);
        panel.add(supplierIDLabel);
        panel.add(supplierIDField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(addButton);

        // Add panel to the frame
        add(panel);
        setVisible(true);
    }

    private void addMedicalShopToDatabase() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String location = locationField.getText();
        String contactInfo = contactField.getText();
        int staffID = Integer.parseInt(staffIDField.getText());
        int supplierID = Integer.parseInt(supplierIDField.getText());

        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating SQL query to insert medical shop data
                String query = "INSERT INTO MedicalShop (ShopID, ShopName, Location, ContactInfo, staffID, SupplierID) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, contactInfo);
                preparedStatement.setInt(5, staffID);
                preparedStatement.setInt(6, supplierID);

                // Executing the query
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Medical Shop added successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add medical shop!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
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
        locationField.setText("");
        contactField.setText("");
        staffIDField.setText("");
        supplierIDField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddMedicShop::new);
    }
}
