import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MedicalShop extends JFrame {

    private JTable table;

    public MedicalShop() {
        setTitle("Medical Shop Information");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table with DefaultTableModel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from database and populate table
        fetchMedicalShopData();

        // Create button panel and buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addMedicalShopButton = new JButton("Add MedicalShop");
        JButton removeMedicalShopButton = new JButton("Remove MedicalShop");
        JButton backButton = new JButton("Back to Home");

        // Add action listeners to buttons
        addMedicalShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open AddMedicShop window
                new AddMedicShop().setVisible(true);
                dispose(); // Close the current window
            }
        });

        removeMedicalShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open removeMedicShop window
                new removeMedicShop().setVisible(true);
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
        buttonPanel.add(addMedicalShopButton);
        buttonPanel.add(removeMedicalShopButton);
        buttonPanel.add(backButton);

        // Add button panel to the frame's content pane at the SOUTH position
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchMedicalShopData() {
        // Establishing connection to the database
        Connection connection = Connect.getConnection();
        if (connection != null) {
            try {
                // Creating statement
                Statement statement = connection.createStatement();

                // Executing query to fetch medical shop information
                String query = "SELECT * FROM MedicalShop";
                ResultSet resultSet = statement.executeQuery(query);

                // Populating table model with data
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Shop ID");
                model.addColumn("Shop Name");
                model.addColumn("Location");
                model.addColumn("Contact Info");
                model.addColumn("Staff ID");
                model.addColumn("Supplier ID");
                model.addColumn("Stock ID");

                while (resultSet.next()) {
                    int shopID = resultSet.getInt("ShopID");
                    String shopName = resultSet.getString("ShopName");
                    String location = resultSet.getString("Location");
                    String contactInfo = resultSet.getString("ContactInfo");
                    int staffID = resultSet.getInt("staffID");
                    int supplierID = resultSet.getInt("SupplierID");
                    int stockID = resultSet.getInt("StockID");

                    model.addRow(new Object[]{shopID, shopName, location, contactInfo, staffID, supplierID, stockID});
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
        SwingUtilities.invokeLater(MedicalShop::new);
    }
}
