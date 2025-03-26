import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class removeCus extends JFrame {

    private JLabel lblCustomerId;
    private JTextField txtCustomerId;
    private JButton btnRemove;

    public removeCus() {
        setTitle("Remove Customer");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        addComponents();

        setVisible(true);
    }

    private void initComponents() {
        lblCustomerId = new JLabel("Customer ID:");
        txtCustomerId = new JTextField(10);
        btnRemove = new JButton("Remove");

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCustomer();
            }
        });
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(lblCustomerId);
        inputPanel.add(txtCustomerId);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnRemove);

        panel.add(inputPanel);
        panel.add(btnPanel);

        add(panel, BorderLayout.CENTER);
    }

    private void removeCustomer() {
        int customerID = Integer.parseInt(txtCustomerId.getText());
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Connect.getConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM Customer WHERE CustomerID = " + customerID;
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Customer removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No customer found with ID: " + customerID);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing customer: " + ex.getMessage());
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
        SwingUtilities.invokeLater(removeCus::new);
    }
}
