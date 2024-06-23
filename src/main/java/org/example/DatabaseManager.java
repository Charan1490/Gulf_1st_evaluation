package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        connectToDatabase();
        createTables();
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/emergency_response";
            String username = "root";
            String password = "Charan@123";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }
    public String[] getCredentialsByMacAddress(String macAddress) {
        String sql = "SELECT username, password FROM users WHERE mac_address = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, macAddress);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                return new String[]{username, password};
            }
        } catch (SQLException e) {
            System.err.println("Failed to get credentials by MAC address: " + e.getMessage());
        }
        return null;
    }


    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "username VARCHAR(50) PRIMARY KEY, " +
                    "password VARCHAR(50), " +
                    "mac_address VARCHAR(50))";
            stmt.executeUpdate(createUsersTable);

            String createPersonalInfoTable = "CREATE TABLE IF NOT EXISTS personal_info (" +
                    "username VARCHAR(50) PRIMARY KEY, " +
                    "full_name VARCHAR(100), " +
                    "age VARCHAR(3), " +
                    "gender VARCHAR(10), " +
                    "phone VARCHAR(15), " +
                    "email VARCHAR(100), " +
                    "address VARCHAR(255), " +
                    "emergency_contact_name VARCHAR(100), " +
                    "emergency_contact_relationship VARCHAR(50), " +
                    "emergency_contact_phone VARCHAR(15))";
            stmt.executeUpdate(createPersonalInfoTable);

            String createMedicalInfoTable = "CREATE TABLE IF NOT EXISTS medical_info (" +
                    "username VARCHAR(50) PRIMARY KEY, " +
                    "allergies VARCHAR(255), " +
                    "medical_conditions VARCHAR(255), " +
                    "medications VARCHAR(255), " +
                    "blood_type VARCHAR(5), " +
                    "disabilities VARCHAR(255))";
            stmt.executeUpdate(createMedicalInfoTable);

            String createLocationInfoTable = "CREATE TABLE IF NOT EXISTS location_info (" +
                    "username VARCHAR(50) PRIMARY KEY, " +
                    "current_location VARCHAR(255), " +
                    "home_address VARCHAR(255), " +
                    "work_address VARCHAR(255))";
            stmt.executeUpdate(createLocationInfoTable);

            String createEmergencyPreferencesTable = "CREATE TABLE IF NOT EXISTS emergency_preferences (" +
                    "username VARCHAR(50) PRIMARY KEY, " +
                    "preferred_hospital VARCHAR(255), " +
                    "preferred_contact VARCHAR(255), " +
                    "language_preferences VARCHAR(255))";
            stmt.executeUpdate(createEmergencyPreferencesTable);

            String createVehicleInfoTable = "CREATE TABLE IF NOT EXISTS vehicle_info (" +
                    "username VARCHAR(50) PRIMARY KEY, " +
                    "vehicle_type VARCHAR(50), " +
                    "vehicle_model VARCHAR(50), " +
                    "license_plate VARCHAR(20), " +
                    "insurance_info VARCHAR(255))";
            stmt.executeUpdate(createVehicleInfoTable);

        } catch (SQLException e) {
            System.err.println("Failed to create tables: " + e.getMessage());
        }
    }

    public String getPersonalInfo(String username) {
        String sql = "SELECT * FROM personal_info WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("full_name");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String emergencyContactName = rs.getString("emergency_contact_name");
                String emergencyContactRelationship = rs.getString("emergency_contact_relationship");
                String emergencyContactPhone = rs.getString("emergency_contact_phone");
                return "Full Name: " + fullName +
                        "\nAge: " + age +
                        "\nGender: " + gender +
                        "\nPhone: " + phone +
                        "\nEmail: " + email +
                        "\nAddress: " + address +
                        "\nEmergency Contact Name: " + emergencyContactName +
                        "\nEmergency Contact Relationship: " + emergencyContactRelationship +
                        "\nEmergency Contact Phone: " + emergencyContactPhone;
            }
        } catch (SQLException e) {
            System.err.println("Failed to get personal info: " + e.getMessage());
        }
        return null;
    }

    public String getMedicalInfo(String username) {
        String sql = "SELECT * FROM medical_info WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String allergies = rs.getString("allergies");
                String medicalConditions = rs.getString("medical_conditions");
                String medications = rs.getString("medications");
                String bloodType = rs.getString("blood_type");
                String disabilities = rs.getString("disabilities");
                return "Allergies: " + allergies +
                        "\nMedical Conditions: " + medicalConditions +
                        "\nMedications: " + medications +
                        "\nBlood Type: " + bloodType +
                        "\nDisabilities: " + disabilities;
            }
        } catch (SQLException e) {
            System.err.println("Failed to get medical info: " + e.getMessage());
        }
        return null;
    }
    public boolean createUser(String username, String password, String macAddress) {
        String sql = "INSERT INTO users (username, password, mac_address) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, macAddress);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Failed to create user: " + e.getMessage());
            return false;
        }
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return password.equals(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to authenticate user: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String username) {
        String deleteUserSql = "DELETE FROM users WHERE username = ?";
        String deletePersonalInfoSql = "DELETE FROM personal_info WHERE username = ?";
        String deleteMedicalInfoSql = "DELETE FROM medical_info WHERE username = ?";
        String deleteLocationInfoSql = "DELETE FROM location_info WHERE username = ?";
        String deleteEmergencyPreferencesSql = "DELETE FROM emergency_preferences WHERE username = ?";
        String deleteVehicleInfoSql = "DELETE FROM vehicle_info WHERE username = ?";

        try (PreparedStatement pstmt1 = connection.prepareStatement(deleteUserSql);
             PreparedStatement pstmt2 = connection.prepareStatement(deletePersonalInfoSql);
             PreparedStatement pstmt3 = connection.prepareStatement(deleteMedicalInfoSql);
             PreparedStatement pstmt4 = connection.prepareStatement(deleteLocationInfoSql);
             PreparedStatement pstmt5 = connection.prepareStatement(deleteEmergencyPreferencesSql);
             PreparedStatement pstmt6 = connection.prepareStatement(deleteVehicleInfoSql)) {

            connection.setAutoCommit(false);

            pstmt1.setString(1, username);
            pstmt2.setString(1, username);
            pstmt3.setString(1, username);
            pstmt4.setString(1, username);
            pstmt5.setString(1, username);
            pstmt6.setString(1, username);

            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            pstmt3.executeUpdate();
            pstmt4.executeUpdate();
            pstmt5.executeUpdate();
            pstmt6.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to delete user: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Failed to rollback transaction: " + rollbackEx.getMessage());
            }
            return false;
        }
    }

    public String[] getAllUsernames() {
        String sql = "SELECT username FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<String> usernameList = new ArrayList<>();
            while (rs.next()) {
                usernameList.add(rs.getString("username"));
            }
            return usernameList.toArray(new String[0]);
        } catch (SQLException e) {
            System.err.println("Failed to get usernames: " + e.getMessage());
            return new String[0];
        }
    }

    // Store methods for each form
    public void storePersonalInfo(String username, String fullName, String age, String gender, String phone, String email, String address, String emergencyContactName, String emergencyContactRelationship, String emergencyContactPhone) {
        String sql = "INSERT INTO personal_info (username, full_name, age, gender, phone, email, address, emergency_contact_name, emergency_contact_relationship, emergency_contact_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, fullName);
            pstmt.setString(3, age);
            pstmt.setString(4, gender);
            pstmt.setString(5, phone);
            pstmt.setString(6, email);
            pstmt.setString(7, address);
            pstmt.setString(8, emergencyContactName);
            pstmt.setString(9, emergencyContactRelationship);
            pstmt.setString(10, emergencyContactPhone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to store personal info: " + e.getMessage());
        }
    }

    public void storeMedicalInfo(String username, String allergies, String medicalConditions, String medications, String bloodType, String disabilities) {
        String sql = "INSERT INTO medical_info (username, allergies, medical_conditions, medications, blood_type, disabilities) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, allergies);
            pstmt.setString(3, medicalConditions);
            pstmt.setString(4, medications);
            pstmt.setString(5, bloodType);
            pstmt.setString(6, disabilities);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to store medical info: " + e.getMessage());
        }
    }

    public void storeLocationInfo(String username, String currentLocation, String homeAddress, String workAddress) {
        String sql = "INSERT INTO location_info (username, current_location, home_address, work_address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, currentLocation);
            pstmt.setString(3, homeAddress);
            pstmt.setString(4, workAddress);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to store location info: " + e.getMessage());
        }
    }

    public void storeEmergencyPreferences(String username, String preferredHospital, String preferredContact, String languagePreferences) {
        String sql = "INSERT INTO emergency_preferences (username, preferred_hospital, preferred_contact, language_preferences) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, preferredHospital);
            pstmt.setString(3, preferredContact);
            pstmt.setString(4, languagePreferences);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to store emergency preferences: " + e.getMessage());
        }
    }

    public void storeVehicleInfo(String username, String vehicleType, String vehicleModel, String licensePlate, String insuranceInfo) {
        String sql = "INSERT INTO vehicle_info (username, vehicle_type, vehicle_model, license_plate, insurance_info) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, vehicleType);
            pstmt.setString(3, vehicleModel);
            pstmt.setString(4, licensePlate);
            pstmt.setString(5, insuranceInfo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to store vehicle info: " + e.getMessage());
        }
    }

    public boolean autoLogin(String macAddress) {
        String sql = "SELECT username, password FROM users WHERE mac_address = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, macAddress);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                return authenticateUser(username, password);
            }
        } catch (SQLException e) {
            System.err.println("Failed to auto-login: " + e.getMessage());
        }
        return false;
    }
}
