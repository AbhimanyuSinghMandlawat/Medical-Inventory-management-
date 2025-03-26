import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stock extends JFrame {

    private JTable table;

    public Stock() {
        setTitle("Stock Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchStockData();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addStockButton = new JButton("Add Stock");
        JButton removeStockButton = new JButton("Remove Stock");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open AddStock window
                dispose();
                new AddStock().setVisible(true);
            }
        });

        removeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open RemoveStock window
                new removeStock().setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to HomeGUI
                dispose(); // Close the current window
                new HomeGUI().setVisible(true); // Show HomeGUI window
            }
        });

        // Add buttons to button panel
        buttonPanel.add(addStockButton);
        buttonPanel.add(removeStockButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchStockData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch stock information
                String query = "SELECT * FROM Stock";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Stock ID");
                model.addColumn("Transaction ID");
                model.addColumn("Quantity Stock");
                model.addColumn("Expiry Date");
                model.addColumn("Action");
                model.addColumn("Date");
                model.addColumn("Time");
                model.addColumn("Medicine For");
                model.addColumn("Type Of Medicine");
                model.addColumn("Stock Company Name");
                model.addColumn("Selling MRP");

                while (resultSet.next()) {
                    int stockID = resultSet.getInt("StockID");
                    int transactionID = resultSet.getInt("TransactionID");
                    int quantityStock = resultSet.getInt("QuantityStock");
                    String expiryDate = resultSet.getString("ExpiryDate");
                    String action = resultSet.getString("Action");
                    String date = resultSet.getString("Date");
                    String time = resultSet.getString("Time");
                    String medicineFor = resultSet.getString("MedicinFor");
                    String typeOfMedicine = resultSet.getString("TypeOfMedicin");
                    String stockCompanyName = resultSet.getString("StockCompanyName");
                    double sellingMRP = resultSet.getDouble("SellingMRP");

                    model.addRow(new Object[]{stockID, transactionID, quantityStock, expiryDate, action,
                            date, time, medicineFor, typeOfMedicine, stockCompanyName, sellingMRP});
                }

                // Set table model
                table.setModel(model);

            } catch (SQLException e) {
                System.err.println("Error executing SQL query: " + e.getMessage());
            } finally {
                // Closing connection
                Connect.closeConnection(connection);
            }
        } else {
            System.out.println("Failed to establish connection to the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Stock::new);
    }
}
