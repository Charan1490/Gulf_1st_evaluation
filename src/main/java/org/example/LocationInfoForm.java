package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LocationInfoForm {
    private JFrame frame;
    private JTextField currentLocationField, homeAddressField, workAddressField;
    private String username;
    private DatabaseManager dbManager;

    public LocationInfoForm(String username, DatabaseManager dbManager) {
        this.username = username;
        this.dbManager = dbManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Location Information");
        frame.setBounds(100, 100, 450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblCurrentLocation = new JLabel("Current Location");
        lblCurrentLocation.setBounds(50, 50, 150, 30);
        frame.getContentPane().add(lblCurrentLocation);

        currentLocationField = new JTextField();
        currentLocationField.setBounds(200, 50, 200, 30);
        frame.getContentPane().add(currentLocationField);

        JLabel lblHomeAddress = new JLabel("Home Address");
        lblHomeAddress.setBounds(50, 100, 150, 30);
        frame.getContentPane().add(lblHomeAddress);

        homeAddressField = new JTextField();
        homeAddressField.setBounds(200, 100, 200, 30);
        frame.getContentPane().add(homeAddressField);

        JLabel lblWorkAddress = new JLabel("Work/School Address");
        lblWorkAddress.setBounds(50, 150, 150, 30);
        frame.getContentPane().add(lblWorkAddress);

        workAddressField = new JTextField();
        workAddressField.setBounds(200, 150, 200, 30);
        frame.getContentPane().add(workAddressField);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(150, 200, 100, 30);
        frame.getContentPane().add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String currentLocation = currentLocationField.getText();
                String homeAddress = homeAddressField.getText();
                String workAddress = workAddressField.getText();

                dbManager.storeLocationInfo(username, currentLocation, homeAddress, workAddress);
                new EmergencyPreferencesForm(username, dbManager);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
