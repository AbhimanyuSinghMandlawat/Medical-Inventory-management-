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

public class AddTran extends JFrame {

    private JTextField[] fields;
    private String[] labels = {"TransactionID:", "Staff ID:", "Shop ID:", "Transaction Detail:",
            "Debit:", "Credit:", "Transaction Date (YYYY-MM-DD):", "Transaction Time (HH:MM:SS):",
            "Supplier ID:", "Customer ID:", "Mode of Payment:"};

    public AddTran() {
        setTitle("Add Transaction");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Determine number of information fields
        int numOfFields = labels.length;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Adding text fields for input
        fields = new JTextField[numOfFields];
        for (int i = 0; i < numOfFields; i++) {
            JLabel label = new JLabel(labels[i]);
            gbc.gridx = 0;
            panel.add(label, gbc);

            JTextField textField = new JTextField(15);
            gbc.gridx = 1;
            fields[i] = textField;
            panel.add(textField, gbc);
            gbc.gridy++;
        }

        JButton addButton = new JButton("Add Transaction");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransaction();
            }
        });

        add(panel);
        pack();
        setVisible(true);
    }

    private void addTransaction() {
        // Retrieve input from text fields
        int transactionID = Integer.parseInt(fields[0].getText());
        int staffID = Integer.parseInt(fields[1].getText());
        int shopID = Integer.parseInt(fields[2].getText());
        String transactionDetail = fields[3].getText();
        double debit = Double.parseDouble(fields[4].getText());
        double credit = Double.parseDouble(fields[5].getText());
        Date transactionDate = null;
        try {
            transactionDate = new SimpleDateFormat("yyyy-MM-dd").parse(fields[6].getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }
        String transactionTime = fields[7].getText();
        int supplierID = Integer.parseInt(fields[8].getText());
        int customerID = Integer.parseInt(fields[9].getText());
        String modeOfPayment = fields[10].getText();

        // Execute SQL query to add transaction to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Transaction (TransactionID, staffID, ShopID, TransactionDetail, Debit, Credit, TransactionDate, TransactionTime, SupplierID, CustomerID, ModeOfPayment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                preparedStatement.setInt(1, transactionID);
                preparedStatement.setInt(2, staffID);
                preparedStatement.setInt(3, shopID);
                preparedStatement.setString(4, transactionDetail);
                preparedStatement.setDouble(5, debit);
                preparedStatement.setDouble(6, credit);
                preparedStatement.setDate(7, new java.sql.Date(transactionDate.getTime()));
                preparedStatement.setString(8, transactionTime);
                preparedStatement.setInt(9, supplierID);
                preparedStatement.setInt(10, customerID);
                preparedStatement.setString(11, modeOfPayment);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Transaction added successfully!");
                    // Clear text fields after adding transaction
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add transaction!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            } finally {
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
        SwingUtilities.invokeLater(AddTran::new);
    }
}

