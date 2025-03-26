import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeGUI extends JFrame {

    public HomeGUI() {
        setTitle("Pharmacy Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create buttons for different functionalities
        String[] buttonLabels = {"Staff", "Supplier", "Medical Shop", "Customer", "Transaction", "Medicine",
                "Prescription", "Stock", "Transaction Medicine", "Doctor", "Order Detail", "Inventory"};

        for (String label : buttonLabels) {
            JButton button = createButton(label);
            panel.add(button);
        }

        add(panel);

        setVisible(true);
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204)); // Blue color
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redirectToClass(label);
            }
        });
        return button;
    }

    private void redirectToClass(String label) {
        // Implement redirection logic based on button label
        switch (label) {
            case "Staff":
                // Open StaffGUI.java class
                new Staff();
                break;
            case "Supplier":
                // Open SupplierInfo.java class
                new SupplierInfo();
                break;
            case "Medical Shop":
                // Open MedicalShop.java class
                new MedicalShop();
                break;
            case "Customer":
                // Open Customer.java class
                new Customer();
                break;
            case "Transaction":
                // Open Transaction.java class
                new Transaction();
                break;
            case "Medicine":
                // Open Medicine.java class
                new Medicine();
                break;
            case "Prescription":
                // Open Prescription.java class
                new Prescription();
                break;
            case "Stock":
                // Open Stock.java class
                new Stock();
                break;
            case "Transaction Medicine":
                // Open TransactionMedicine.java class
                new Transaction_Medicine();
                break;
            case "Doctor":
                // Open Doctor.java class
                new Doctor();
                break;
            case "Order Detail":
                // Open OrderDetail.java class
                new OrderDetails();
                break;
            case "Inventory":
                // Open Inventory.java class
                new Inventory();
                break;
            default:
                System.out.println("Feature not implemented yet: " + label);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeGUI());
    }
}
