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

public class AddOrder extends JFrame {

    private JTextField[] fields;
    private String[] labels = {"OrderID:", "Shop ID:", "Staff ID:", "Order Date (YYYY-MM-DD):",
            "Order Time (HH:MM:SS):", "Total Amount:", "Medicine ID:", "Quantity:",
            "Selling MRP:"};

    public AddOrder() {
        setTitle("Add Order");
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

        JButton addButton = new JButton("Add Order");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        add(panel);
        pack();
        setVisible(true);
    }

    private void addOrder() {
        // Retrieve input from text fields
        int OrderID = Integer.parseInt(fields[0].getText());
        int shopID = Integer.parseInt(fields[1].getText());
        int staffID = Integer.parseInt(fields[2].getText());
        Date orderDate = null;
        try {
            orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(fields[3].getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }
        String orderTime = fields[4].getText();
        double totalAmount = Double.parseDouble(fields[5].getText());
        int medicineID = Integer.parseInt(fields[6].getText());
        int quantity = Integer.parseInt(fields[7].getText());
        double sellingMRP = Double.parseDouble(fields[8].getText());

        // Execute SQL query to add order to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO OrderDetails (OrderID, ShopID, staffID, OrderDate, OrderTime, TotalAmount, MedicineID, Quantity, SellingMRP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

                preparedStatement.setInt(1, OrderID );
                preparedStatement.setInt(2, shopID);
                preparedStatement.setInt(3, staffID);
                preparedStatement.setDate(4, new java.sql.Date(orderDate.getTime()));
                preparedStatement.setString(5, orderTime);
                preparedStatement.setDouble(6, totalAmount);
                preparedStatement.setInt(7, medicineID);
                preparedStatement.setInt(8, quantity);
                preparedStatement.setDouble(9, sellingMRP);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Order added successfully!");
                    // Clear text fields after adding order
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add order!");
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
        SwingUtilities.invokeLater(AddOrder::new);
    }
}
