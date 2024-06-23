package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LoginForm {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private DatabaseManager dbManager;

    public LoginForm(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Login");
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

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 150, 100, 30);
        frame.getContentPane().add(btnLogin);

        JButton btnAutoLogin = new JButton("Auto Login");
        btnAutoLogin.setBounds(150, 190, 100, 30);
        frame.getContentPane().add(btnAutoLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (dbManager.authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    new SOSPage(username, dbManager);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "User not found. Please sign up first.");
                }
            }
        });

        btnAutoLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String macAddress = getMacAddress();
                if (macAddress != null) {
                    String[] credentials = dbManager.getCredentialsByMacAddress(macAddress);
                    if (credentials != null) {
                        String username = credentials[0];
                        String password = credentials[1];
                        if (dbManager.authenticateUser(username, password)) {
                            JOptionPane.showMessageDialog(frame, "Auto Login successful!");
                            new SOSPage(username, dbManager);
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Auto Login failed. Please try manual login.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No user associated with this device. Please sign up first.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to retrieve MAC address. Please try manual login.");
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
