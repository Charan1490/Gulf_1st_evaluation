package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SignupForm {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private DatabaseManager dbManager;

    public SignupForm(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Signup");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(50, 50, 150, 30);
        frame.getContentPane().add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(200, 50, 200, 30);
        frame.getContentPane().add(usernameField);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(50, 100, 150, 30);
        frame.getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 100, 200, 30);
        frame.getContentPane().add(passwordField);

        JButton btnSignup = new JButton("Signup");
        btnSignup.setBounds(150, 150, 100, 30);
        frame.getContentPane().add(btnSignup);

        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String macAddress = getMacAddress();

                if (macAddress == null) {
                    JOptionPane.showMessageDialog(frame, "Failed to retrieve MAC address. Please try again.");
                    return;
                }

                if (dbManager.createUser(username, password, macAddress)) {
                    JOptionPane.showMessageDialog(frame, "Signup successful! Please fill in your details.");
                    new PersonalInfoForm(username, dbManager);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists. Please choose a different username.");
                }
            }
        });

        frame.setVisible(true);
    }

    private String getMacAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            if (mac == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (UnknownHostException | SocketException e) {
            System.err.println("Failed to get MAC address: " + e.getMessage());
            return null;
        }
    }
}
