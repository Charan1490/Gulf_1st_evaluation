package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicalInfoForm {
    private JFrame frame;
    private JTextField allergiesField, medicalConditionsField, medicationsField, bloodTypeField, disabilitiesField;
    private String username;
    private DatabaseManager dbManager;

    public MedicalInfoForm(String username, DatabaseManager dbManager) {
        this.username = username;
        this.dbManager = dbManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Medical Information");
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblAllergies = new JLabel("Allergies");
        lblAllergies.setBounds(50, 50, 150, 30);
        frame.getContentPane().add(lblAllergies);

        allergiesField = new JTextField();
        allergiesField.setBounds(200, 50, 200, 30);
        frame.getContentPane().add(allergiesField);

        JLabel lblMedicalConditions = new JLabel("Medical Conditions");
        lblMedicalConditions.setBounds(50, 100, 150, 30);
        frame.getContentPane().add(lblMedicalConditions);

        medicalConditionsField = new JTextField();
        medicalConditionsField.setBounds(200, 100, 200, 30);
        frame.getContentPane().add(medicalConditionsField);

        JLabel lblMedications = new JLabel("Medications");
        lblMedications.setBounds(50, 150, 150, 30);
        frame.getContentPane().add(lblMedications);

        medicationsField = new JTextField();
        medicationsField.setBounds(200, 150, 200, 30);
        frame.getContentPane().add(medicationsField);

        JLabel lblBloodType = new JLabel("Blood Type");
        lblBloodType.setBounds(50, 200, 150, 30);
        frame.getContentPane().add(lblBloodType);

        bloodTypeField = new JTextField();
        bloodTypeField.setBounds(200, 200, 200, 30);
        frame.getContentPane().add(bloodTypeField);

        JLabel lblDisabilities = new JLabel("Disabilities/Special Needs");
        lblDisabilities.setBounds(50, 250, 150, 30);
        frame.getContentPane().add(lblDisabilities);

        disabilitiesField = new JTextField();
        disabilitiesField.setBounds(200, 250, 200, 30);
        frame.getContentPane().add(disabilitiesField);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(150, 300, 100, 30);
        frame.getContentPane().add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String allergies = allergiesField.getText();
                String medicalConditions = medicalConditionsField.getText();
                String medications = medicationsField.getText();
                String bloodType = bloodTypeField.getText();
                String disabilities = disabilitiesField.getText();

                dbManager.storeMedicalInfo(username, allergies, medicalConditions, medications, bloodType, disabilities);
                new LocationInfoForm(username, dbManager);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
