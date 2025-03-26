import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileGUI extends JFrame {

    private String authID;

    public ProfileGUI(String authID) {
        this.authID = authID;

        setTitle("Profile");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel nameLabel = new JLabel("Welcome, " + authID + "!");
        panel.add(nameLabel);

        JButton manageButton = new JButton("Manage Work");
        manageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your work management logic here
                JOptionPane.showMessageDialog(ProfileGUI.this, "Work management feature will be implemented here.", "Work Management", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(manageButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close profile window
                new LoginGUI(); // Open login window
            }
        });
        panel.add(logoutButton);

        add(panel);

        setVisible(true);
    }
}
