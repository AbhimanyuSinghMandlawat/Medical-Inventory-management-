import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDetails extends JFrame {

    private JTable table;

    public OrderDetails() {
        setTitle("Order Details");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchOrderDetails();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addOrderButton = new JButton("Add Order");
        JButton removeOrderButton = new JButton("Remove Order");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the AddOrder class
                new AddOrder();
            }
        });

        removeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open removeOrder when remove Order button is clicked
                new removeOrder();
                dispose(); // Close the current window
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
        buttonPanel.add(addOrderButton);
        buttonPanel.add(removeOrderButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchOrderDetails() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch order details
                String query = "SELECT * FROM OrderDetails";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Order ID");
                model.addColumn("Shop ID");
                model.addColumn("Staff ID");
                model.addColumn("Order Date");
                model.addColumn("Order Time");
                model.addColumn("Total Amount");
                model.addColumn("Medicine ID");
                model.addColumn("Quantity");
                model.addColumn("Selling MRP");

                while (resultSet.next()) {
                    int orderID = resultSet.getInt("OrderID");
                    int shopID = resultSet.getInt("ShopID");
                    int staffID = resultSet.getInt("staffID");
                    String orderDate = resultSet.getString("OrderDate");
                    String orderTime = resultSet.getString("OrderTime");
                    double totalAmount = resultSet.getDouble("TotalAmount");
                    int medicineID = resultSet.getInt("MedicineID");
                    int quantity = resultSet.getInt("Quantity");
                    double sellingMRP = resultSet.getDouble("SellingMRP");

                    model.addRow(new Object[]{orderID, shopID, staffID, orderDate, orderTime,
                            totalAmount, medicineID, quantity, sellingMRP});
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
        SwingUtilities.invokeLater(OrderDetails::new);
    }
}
