import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction_Medicine extends JFrame {

    private JTable table;

    public Transaction_Medicine() {
        setTitle("Transaction Medicine Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchTransactionMedicineData();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addTransactionMedicineButton = new JButton("Add Transaction Medicine");
        JButton removeTransactionMedicineButton = new JButton("Remove Transaction Medicine");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addTransactionMedicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add transaction medicine action
                JOptionPane.showMessageDialog(Transaction_Medicine.this, "Add Transaction Medicine functionality is not implemented yet.");
            }
        });

        removeTransactionMedicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open removeTransMedic window
                dispose(); // Close the current window
                new removeTransMedic().setVisible(true); // Show removeTransMedic window
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
        buttonPanel.add(addTransactionMedicineButton);
        buttonPanel.add(removeTransactionMedicineButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchTransactionMedicineData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch transaction medicine information
                String query = "SELECT * FROM TransactionMedicin";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Transaction Medicine ID");
                model.addColumn("Medicine ID");
                model.addColumn("Quantity");

                while (resultSet.next()) {
                    int transactionMedicineID = resultSet.getInt("TransactionMedicinID");
                    int medicineID = resultSet.getInt("MedicineID");
                    int quantity = resultSet.getInt("Quantity");

                    model.addRow(new Object[]{transactionMedicineID, medicineID, quantity});
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
        SwingUtilities.invokeLater(Transaction_Medicine::new);
    }
}
