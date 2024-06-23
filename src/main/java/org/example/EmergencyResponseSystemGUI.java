package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmergencyResponseSystemGUI {
    private JFrame frame;
    private DatabaseManager dbManager;

    public EmergencyResponseSystemGUI(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Women Safety App");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnSignup = new JButton("Signup");
        btnSignup.setBounds(150, 50, 150, 30);
        frame.getContentPane().add(btnSignup);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 100, 150, 30);
        frame.getContentPane().add(btnLogin);

        JButton btnDeleteUser = new JButton("Delete User");
        btnDeleteUser.setBounds(150, 150, 150, 30);
        frame.getContentPane().add(btnDeleteUser);

        JButton btnShowUsername = new JButton("Show Username");
        btnShowUsername.setBounds(150, 200, 150, 30);
        frame.getContentPane().add(btnShowUsername);

        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignupForm(dbManager);
                frame.dispose();
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginForm(dbManager);
                frame.dispose();
            }
        });

        btnDeleteUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter Username to Delete:");
                if (username != null && !username.isEmpty()) {
                    if (dbManager.deleteUser(username)) {
                        JOptionPane.showMessageDialog(frame, "User deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "User not found.");
                    }
                }
            }
        });

        btnShowUsername.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] usernames = dbManager.getAllUsernames();
                JOptionPane.showMessageDialog(frame, "Usernames: " + String.join(", ", usernames));
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        new EmergencyResponseSystemGUI(dbManager);
    }
}
