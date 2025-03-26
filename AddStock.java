import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddStock extends JFrame {

    private JLabel stockIDLabel, transactionIDLabel, quantityStockLabel, expiryDateLabel, actionLabel,
            dateLabel, timeLabel, medicinForLabel, typeOfMedicinLabel, stockCompanyNameLabel, sellingMRPLabel;
    private JTextField stockIDField, transactionIDField, quantityStockField, expiryDateField, actionField,
            dateField, timeField, medicinForField, typeOfMedicinField, stockCompanyNameField, sellingMRPField;
    private JButton addButton;

    public AddStock() {
        setTitle("Add Stock");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(12, 2));

        // Initialize components
        stockIDLabel = new JLabel("Stock ID:");
        stockIDField = new JTextField();
        transactionIDLabel = new JLabel("Transaction ID:");
        transactionIDField = new JTextField();
        quantityStockLabel = new JLabel("Quantity in Stock:");
        quantityStockField = new JTextField();
        expiryDateLabel = new JLabel("Expiry Date:");
        expiryDateField = new JTextField();
        actionLabel = new JLabel("Action (StockIn/StockOut):");
        actionField = new JTextField();
        dateLabel = new JLabel("Date:");
        dateField = new JTextField();
        timeLabel = new JLabel("Time:");
        timeField = new JTextField();
        medicinForLabel = new JLabel("Medicine For (Veterinary/Human):");
        medicinForField = new JTextField();
        typeOfMedicinLabel = new JLabel("Type of Medicine:");
        typeOfMedicinField = new JTextField();
        stockCompanyNameLabel = new JLabel("Stock Company Name:");
        stockCompanyNameField = new JTextField();
        sellingMRPLabel = new JLabel("Selling MRP:");
        sellingMRPField = new JTextField();
        addButton = new JButton("Add");

        // Add components to the frame
        add(stockIDLabel);
        add(stockIDField);
        add(transactionIDLabel);
        add(transactionIDField);
        add(quantityStockLabel);
        add(quantityStockField);
        add(expiryDateLabel);
        add(expiryDateField);
        add(actionLabel);
        add(actionField);
        add(dateLabel);
        add(dateField);
        add(timeLabel);
        add(timeField);
        add(medicinForLabel);
        add(medicinForField);
        add(typeOfMedicinLabel);
        add(typeOfMedicinField);
        add(stockCompanyNameLabel);
        add(stockCompanyNameField);
        add(sellingMRPLabel);
        add(sellingMRPField);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStockToDatabase();
            }
        });

        setVisible(true);
    }

    private void addStockToDatabase() {
        // Retrieve input values
        int stockID = Integer.parseInt(stockIDField.getText());
        int transactionID = Integer.parseInt(transactionIDField.getText());
        int quantityStock = Integer.parseInt(quantityStockField.getText());
        String expiryDate = expiryDateField.getText();
        String action = actionField.getText();
        String date = dateField.getText();
        String time = timeField.getText();
        String medicinFor = medicinForField.getText();
        String typeOfMedicin = typeOfMedicinField.getText();
        String stockCompanyName = stockCompanyNameField.getText();
        double sellingMRP = Double.parseDouble(sellingMRPField.getText());

        // Insert into database
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Connect.getConnection();
            if (connection != null) {
                statement = connection.createStatement();
                String query = "INSERT INTO Stock (StockID, TransactionID, QuantityStock, ExpiryDate, Action, Date, Time, MedicinFor, TypeOfMedicin, StockCompanyName, SellingMRP) " +
                        "VALUES (" + stockID + ", " + transactionID + ", " + quantityStock + ", '" + expiryDate + "', '" + action + "', '" + date + "', '" + time + "', '" + medicinFor + "', '" + typeOfMedicin + "', '" + stockCompanyName + "', " + sellingMRP + ")";
                statement.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "Stock added successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to establish connection to the database.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddStock::new);
    }
}

