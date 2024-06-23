package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmergencyPreferencesForm {
    private JFrame frame;
    private JTextField preferredHospitalField, preferredContactField, languagePreferencesField;
    private String username;
    private DatabaseManager dbManager;

    public EmergencyPreferencesForm(String username, DatabaseManager dbManager) {
        this.username = username;
        this.dbManager = dbManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Emergency Preferences");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblPreferredHospital = new JLabel("Preferred Hospital");
        lblPreferredHospital.setBounds(50, 50, 150, 30);
        frame.getContentPane().add(lblPreferredHospital);

        preferredHospitalField = new JTextField();
        preferredHospitalField.setBounds(200, 50, 200, 30);
        frame.getContentPane().add(preferredHospitalField);

        JLabel lblPreferredContact = new JLabel("Preferred Contact");
        lblPreferredContact.setBounds(50, 100, 150, 30);
        frame.getContentPane().add(lblPreferredContact);

        preferredContactField = new JTextField();
        preferredContactField.setBounds(200, 100, 200, 30);
        frame.getContentPane().add(preferredContactField);

        JLabel lblLanguagePreferences = new JLabel("Language Preferences");
        lblLanguagePreferences.setBounds(50, 150, 150, 30);
        frame.getContentPane().add(lblLanguagePreferences);

        languagePreferencesField = new JTextField();
        languagePreferencesField.setBounds(200, 150, 200, 30);
        frame.getContentPane().add(languagePreferencesField);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(150, 200, 100, 30);
        frame.getContentPane().add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String preferredHospital = preferredHospitalField.getText();
                String preferredContact = preferredContactField.getText();
                String languagePreferences = languagePreferencesField.getText();

                dbManager.storeEmergencyPreferences(username, preferredHospital, preferredContact, languagePreferences);
                new VehicleInfoForm(username, dbManager);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
