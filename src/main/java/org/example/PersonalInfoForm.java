package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalInfoForm {
    private JFrame frame;
    private JTextField fullNameField, ageField, genderField, phoneField, emailField, addressField, emergencyContactNameField, emergencyContactRelationshipField, emergencyContactPhoneField;
    private String username;
    private DatabaseManager dbManager;

    public PersonalInfoForm(String username, DatabaseManager dbManager) {
        this.username = username;
        this.dbManager = dbManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Personal Information");
        frame.setBounds(100, 100, 450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblFullName = new JLabel("Full Name");
        lblFullName.setBounds(50, 50, 150, 30);
        frame.getContentPane().add(lblFullName);

        fullNameField = new JTextField();
        fullNameField.setBounds(200, 50, 200, 30);
        frame.getContentPane().add(fullNameField);

        JLabel lblAge = new JLabel("Age");
        lblAge.setBounds(50, 100, 150, 30);
        frame.getContentPane().add(lblAge);

        ageField = new JTextField();
        ageField.setBounds(200, 100, 200, 30);
        frame.getContentPane().add(ageField);

        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(50, 150, 150, 30);
        frame.getContentPane().add(lblGender);

        genderField = new JTextField();
        genderField.setBounds(200, 150, 200, 30);
        frame.getContentPane().add(genderField);

        JLabel lblPhone = new JLabel("Phone Number");
        lblPhone.setBounds(50, 200, 150, 30);
        frame.getContentPane().add(lblPhone);

        phoneField = new JTextField();
        phoneField.setBounds(200, 200, 200, 30);
        frame.getContentPane().add(phoneField);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(50, 250, 150, 30);
        frame.getContentPane().add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(200, 250, 200, 30);
        frame.getContentPane().add(emailField);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(50, 300, 150, 30);
        frame.getContentPane().add(lblAddress);

        addressField = new JTextField();
        addressField.setBounds(200, 300, 200, 30);
        frame.getContentPane().add(addressField);

        JLabel lblEmergencyContactName = new JLabel("Emergency Contact Name");
        lblEmergencyContactName.setBounds(50, 350, 150, 30);
        frame.getContentPane().add(lblEmergencyContactName);

        emergencyContactNameField = new JTextField();
        emergencyContactNameField.setBounds(200, 350, 200, 30);
        frame.getContentPane().add(emergencyContactNameField);

        JLabel lblEmergencyContactRelationship = new JLabel("Relationship");
        lblEmergencyContactRelationship.setBounds(50, 400, 150, 30);
        frame.getContentPane().add(lblEmergencyContactRelationship);

        emergencyContactRelationshipField = new JTextField();
        emergencyContactRelationshipField.setBounds(200, 400, 200, 30);
        frame.getContentPane().add(emergencyContactRelationshipField);

        JLabel lblEmergencyContactPhone = new JLabel("Emergency Contact Phone");
        lblEmergencyContactPhone.setBounds(50, 450, 150, 30);
        frame.getContentPane().add(lblEmergencyContactPhone);

        emergencyContactPhoneField = new JTextField();
        emergencyContactPhoneField.setBounds(200, 450, 200, 30);
        frame.getContentPane().add(emergencyContactPhoneField);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(150, 500, 100, 30);
        frame.getContentPane().add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText();
                String age = ageField.getText();
                String gender = genderField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                String emergencyContactName = emergencyContactNameField.getText();
                String emergencyContactRelationship = emergencyContactRelationshipField.getText();
                String emergencyContactPhone = emergencyContactPhoneField.getText();

                dbManager.storePersonalInfo(username, fullName, age, gender, phone, email, address, emergencyContactName, emergencyContactRelationship, emergencyContactPhone);
                new MedicalInfoForm(username, dbManager);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
