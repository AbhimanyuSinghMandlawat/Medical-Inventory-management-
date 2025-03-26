import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMedic extends JFrame {

    private JLabel idLabel, nameLabel, companyLabel, purchaseRateLabel, expiryDateLabel;
    private JTextField idField, nameField, companyField, purchaseRateField, expiryDateField;
    private JButton addButton;

    public AddMedic() {
        setTitle("Add Medicine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        idLabel = new JLabel("Medicine ID:");
        nameLabel = new JLabel("Medicine Name:");
        companyLabel = new JLabel("Company Name:");
        purchaseRateLabel = new JLabel("Purchase Rate:");
        expiryDateLabel = new JLabel("Expiry Date (YYYY-MM-DD):");

        idField = new JTextField(5); // Assuming Medicine ID is an integer
        nameField = new JTextField(20);
        companyField = new JTextField(20);
        purchaseRateField = new JTextField(10);
        expiryDateField = new JTextField(10);

        addButton = new JButton("Add Medicine");

        // Add action listener to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMedicineToDatabase();
            }
        });

        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(companyLabel);
        panel.add(companyField);
        panel.add(purchaseRateLabel);
        panel.add(purchaseRateField);
        panel.add(expiryDateLabel);
        panel.add(expiryDateField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(addButton);

        // Add panel to the frame
        add(panel);
        setVisible(true);
    }

    private void addMedicineToDatabase() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String company = companyField.getText();
        double purchaseRate = Double.parseDouble(purchaseRateField.getText());
        Date expiryDate = null;
        try {
            expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDateField.getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating SQL query to insert medicine data
                String query = "INSERT INTO Medicine (MedicineID, MedicineName, CompanyName, PurchaseRate, ExpiryDate) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, company);
                preparedStatement.setDouble(4, purchaseRate);
                preparedStatement.setDate(5, new java.sql.Date(expiryDate.getTime()));

                // Executing the query
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Medicine added successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add medicine!");
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
        companyField.setText("");
        purchaseRateField.setText("");
        expiryDateField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddMedic::new);
    }
}
