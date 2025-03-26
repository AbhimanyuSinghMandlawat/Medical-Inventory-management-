import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddTransMedic extends JFrame {

    // Define your GUI components here
    private JLabel lblTransactionMedicinID;
    private JTextField txtTransactionMedicinID;
    private JLabel lblMedicineID;
    private JTextField txtMedicineID;
    private JLabel lblQuantity;
    private JTextField txtQuantity;
    private JButton btnAdd;

    public AddTransMedic() {
        setTitle("Add Transaction Medicine");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize GUI components
        initComponents();

        // Add components to the frame
        addComponents();

        setVisible(true);
    }

    private void initComponents() {
        lblTransactionMedicinID = new JLabel("TransactionMedicin ID:");
        txtTransactionMedicinID = new JTextField(10);
        lblMedicineID = new JLabel("Medicine ID:");
        txtMedicineID = new JTextField(10);
        lblQuantity = new JLabel("Quantity:");
        txtQuantity = new JTextField(10);
        btnAdd = new JButton("Add");

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add transaction medicine to the database
                addTransactionMedicine();
            }
        });
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(lblTransactionMedicinID);
        panel.add(txtTransactionMedicinID);
        panel.add(lblMedicineID);
        panel.add(txtMedicineID);
        panel.add(lblQuantity);
        panel.add(txtQuantity);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnAdd);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void addTransactionMedicine() {
        // Get data from text fields
        int transactionMedicinID = Integer.parseInt(txtTransactionMedicinID.getText());
        int medicineID = Integer.parseInt(txtMedicineID.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());

        // Insert data into the database
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Connect.getConnection();
            statement = connection.createStatement();
            String query = "INSERT INTO TransactionMedicin (TransactionMedicinID, MedicineID, Quantity) " +
                    "VALUES (" + transactionMedicinID + ", " + medicineID + ", " + quantity + ")";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(this, "Transaction Medicine added successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding Transaction Medicine: " + ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddTransMedic::new);
    }
}
