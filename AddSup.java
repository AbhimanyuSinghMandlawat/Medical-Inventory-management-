import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddSup extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField companyField;
    private JTextField mobileField;
    private JTextField medicineField;
    private JTextField emailField;
    private JTextField transactionField;
    private JTextField modeField;
    private JTextField dateField;
    private JTextField timeField;

    public AddSup() {
        setTitle("Add Supplier");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(11, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Adding text fields for input
        idField = new JTextField();
        nameField = new JTextField();
        companyField = new JTextField();
        mobileField = new JTextField();
        medicineField = new JTextField();
        emailField = new JTextField();
        transactionField = new JTextField();
        modeField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();

        panel.add(new JLabel("Supplier ID:"));
        panel.add(idField);
        panel.add(new JLabel("Supplier Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Company Name:"));
        panel.add(companyField);
        panel.add(new JLabel("Mobile Number:"));
        panel.add(mobileField);
        panel.add(new JLabel("Medicine Info:"));
        panel.add(medicineField);
        panel.add(new JLabel("Email ID:"));
        panel.add(emailField);
        panel.add(new JLabel("Transaction Detail:"));
        panel.add(transactionField);
        panel.add(new JLabel("Transaction Mode:"));
        panel.add(modeField);
        panel.add(new JLabel("Delivery Date:"));
        panel.add(dateField);
        panel.add(new JLabel("Delivery Time:"));
        panel.add(timeField);

        JButton addButton = new JButton("Add Supplier");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });
        panel.add(addButton);

        add(panel);
        setVisible(true);
    }

    private void addSupplier() {
        // Retrieve input from text fields
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String company = companyField.getText();
        String mobile = mobileField.getText();
        String medicine = medicineField.getText();
        String email = emailField.getText();
        String transaction = transactionField.getText();
        String mode = modeField.getText();
        String date = dateField.getText();
        String time = timeField.getText();

        // Execute SQL query to add supplier to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String query = "INSERT INTO Supplier (SupplierID, SupplierName, SupplierCompanyName, MobileNumber, MedicinInfo, emailID, TransactionDetail, TransactionMode, DeliveryDate, DeliveryTime) VALUES (" +
                        id + ",'" + name + "','" + company + "','" + mobile + "','" + medicine + "','" + email + "','" + transaction + "','" + mode + "','" + date + "','" + time + "')";
                int rowsAffected = statement.executeUpdate(query);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Supplier added successfully!");
                    // Clear text fields after adding supplier
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add supplier!");
                }
            } catch (SQLException ex) {
                System.err.println("SQL Exception: " + ex.getMessage());
            } finally {
                Connect.closeConnection(connection);
            }
        } else {
            System.out.println("Failed to establish connection to the database.");
        }
    }

    private void clearFields() {
        // Clear all text fields
        idField.setText("");
        nameField.setText("");
        companyField.setText("");
        mobileField.setText("");
        medicineField.setText("");
        emailField.setText("");
        transactionField.setText("");
        modeField.setText("");
        dateField.setText("");
        timeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddSup::new);
    }
}
