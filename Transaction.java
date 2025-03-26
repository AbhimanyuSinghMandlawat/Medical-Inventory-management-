import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction extends JFrame {

    private JTable table;

    public Transaction() {
        setTitle("Transaction Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchTransactionData();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addTransactionButton = new JButton("Add Transaction");
        JButton removeTransactionButton = new JButton("Remove Transaction");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add transaction action
                new AddTran().setVisible(true);
            }
        });

        removeTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open RemoveTrans class when "Remove Transaction" button is clicked
                new RemoveTrans().setVisible(true);
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
        buttonPanel.add(addTransactionButton);
        buttonPanel.add(removeTransactionButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchTransactionData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch transaction information
                String query = "SELECT * FROM Transaction";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Transaction ID");
                model.addColumn("Staff ID");
                model.addColumn("Shop ID");
                model.addColumn("Transaction Detail");
                model.addColumn("Debit");
                model.addColumn("Credit");
                model.addColumn("Transaction Date");
                model.addColumn("Transaction Time");
                model.addColumn("Supplier ID");
                model.addColumn("Customer ID");
                model.addColumn("Mode Of Payment");

                while (resultSet.next()) {
                    int transactionID = resultSet.getInt("TransactionID");
                    int staffID = resultSet.getInt("staffID");
                    int shopID = resultSet.getInt("ShopID");
                    String transactionDetail = resultSet.getString("TransactionDetail");
                    double debit = resultSet.getDouble("Debit");
                    double credit = resultSet.getDouble("Credit");
                    String transactionDate = resultSet.getString("TransactionDate");
                    String transactionTime = resultSet.getString("TransactionTime");
                    int supplierID = resultSet.getInt("SupplierID");
                    int customerID = resultSet.getInt("CustomerID");
                    String modeOfPayment = resultSet.getString("ModeOfPayment");

                    model.addRow(new Object[]{transactionID, staffID, shopID, transactionDetail,
                            debit, credit, transactionDate, transactionTime, supplierID, customerID, modeOfPayment});
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
        SwingUtilities.invokeLater(Transaction::new);
    }
}
