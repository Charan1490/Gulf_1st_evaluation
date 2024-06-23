package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VehicleInfoForm {
    private JFrame frame;
    private JTextField vehicleTypeField, vehicleModelField, licensePlateField, insuranceInfoField;
    private String username;
    private DatabaseManager dbManager;

    public VehicleInfoForm(String username, DatabaseManager dbManager) {
        this.username = username;
        this.dbManager = dbManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Vehicle Information");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblVehicleType = new JLabel("Vehicle Type");
        lblVehicleType.setBounds(50, 50, 150, 30);
        frame.getContentPane().add(lblVehicleType);

        vehicleTypeField = new JTextField();
        vehicleTypeField.setBounds(200, 50, 200, 30);
        frame.getContentPane().add(vehicleTypeField);

        JLabel lblVehicleModel = new JLabel("Vehicle Model");
        lblVehicleModel.setBounds(50, 100, 150, 30);
        frame.getContentPane().add(lblVehicleModel);

        vehicleModelField = new JTextField();
        vehicleModelField.setBounds(200, 100, 200, 30);
        frame.getContentPane().add(vehicleModelField);

        JLabel lblLicensePlate = new JLabel("License Plate Number");
        lblLicensePlate.setBounds(50, 150, 150, 30);
        frame.getContentPane().add(lblLicensePlate);

        licensePlateField = new JTextField();
        licensePlateField.setBounds(200, 150, 200, 30);
        frame.getContentPane().add(licensePlateField);

        JLabel lblInsuranceInfo = new JLabel("Insurance Information");
        lblInsuranceInfo.setBounds(50, 200, 150, 30);
        frame.getContentPane().add(lblInsuranceInfo);

        insuranceInfoField = new JTextField();
        insuranceInfoField.setBounds(200, 200, 200, 30);
        frame.getContentPane().add(insuranceInfoField);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(150, 250, 100, 30);
        frame.getContentPane().add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vehicleType = vehicleTypeField.getText();
                String vehicleModel = vehicleModelField.getText();
                String licensePlate = licensePlateField.getText();
                String insuranceInfo = insuranceInfoField.getText();

                dbManager.storeVehicleInfo(username, vehicleType, vehicleModel, licensePlate, insuranceInfo);
                new SOSPage(username, dbManager);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}

