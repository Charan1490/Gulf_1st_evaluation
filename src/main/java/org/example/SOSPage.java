package org.example;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SOSPage {
    private JFrame frame;
    private String username;
    private DatabaseManager dbManager;
    private Map<String, Integer> locations;
    private Map<String, Integer> hospitals;
    private Map<String, Integer> policeStations;
    private int[][] graph;

    // Twilio credentials
    public static final String ACCOUNT_SID = "AC6880467f0a28653c9e8db32bb96c1498";
    public static final String AUTH_TOKEN = "16c16c15f2b76abadc755dfde8b7411b";
    public static final String TWILIO_PHONE_NUMBER = "+12292673571";

    public SOSPage(String username, DatabaseManager dbManager) {
        this.username = username;
        this.dbManager = dbManager;
        initializeMaps();
        initializeGraph();
        initialize();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN); // Initialize Twilio
    }

    private void initialize() {
        frame = new JFrame("Welcome to Women Safety App");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnSOS = new JButton("SOS");
        btnSOS.setBounds(175, 125, 100, 50); // Center the button
        frame.getContentPane().add(btnSOS);

        btnSOS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLocationOptions();
            }
        });

        frame.setVisible(true);
    }

    private void showLocationOptions() {
        String[] options = {"Location 1", "Location 2", "Location 3", "Location 4"};
        int locationIndex = JOptionPane.showOptionDialog(frame, "Select your location:", "Location",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (locationIndex >= 0) {
            String location = options[locationIndex];
            findAndSendEmergencyContacts(location);
        }
    }

    private void findAndSendEmergencyContacts(String location) {
        int locationIndex = locations.get(location);

        // Find nearest hospital
        String nearestHospital = findNearest(hospitals, locationIndex);
        String hospitalPhone = getHospitalPhone(nearestHospital);
        String medicalDetails = dbManager.getMedicalInfo(username);
        sendSMS(hospitalPhone, "Medical Emergency for " + username + ": " + medicalDetails);

        // Find nearest police station
        String nearestPoliceStation = findNearest(policeStations, locationIndex);
        String policePhone = getPolicePhone(nearestPoliceStation);
        String personalDetails = dbManager.getPersonalInfo(username);
        sendSMS(policePhone, "Emergency for " + username + " at " + location + ": " + personalDetails);
    }

    private void initializeMaps() {
        // Initialize locations, hospitals, and police stations with indexes
        locations = new HashMap<>();
        locations.put("Location 1", 0);
        locations.put("Location 2", 1);
        locations.put("Location 3", 2);
        locations.put("Location 4", 3);

        hospitals = new HashMap<>();
        hospitals.put("Hospital A", 4);
        hospitals.put("Hospital B", 5);
        hospitals.put("Hospital C", 6);
        hospitals.put("Hospital D", 7);

        policeStations = new HashMap<>();
        policeStations.put("Police Station 1", 8);
        policeStations.put("Police Station 2", 9);
        policeStations.put("Police Station 3", 10);
        policeStations.put("Police Station 4", 11);
    }

    private void initializeGraph() {
        // Initialize the graph with distances between locations, hospitals, and police stations
        graph = new int[12][12];
        for (int[] row : graph) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Example distances (fill these with actual data)
        graph[0][1] = 10; // Location 1 to Location 2
        graph[0][2] = 15; // Location 1 to Location 3
        graph[0][3] = 20; // Location 1 to Location 4
        graph[0][4] = 5;  // Location 1 to Hospital A
        graph[0][8] = 7;  // Location 1 to Police Station 1

        graph[1][0] = 10; // Location 2 to Location 1
        graph[1][2] = 10; // Location 2 to Location 3
        graph[1][3] = 25; // Location 2 to Location 4
        graph[1][5] = 5;  // Location 2 to Hospital B
        graph[1][9] = 7;  // Location 2 to Police Station 2

        graph[2][0] = 15; // Location 3 to Location 1
        graph[2][1] = 10; // Location 3 to Location 2
        graph[2][3] = 10; // Location 3 to Location 4
        graph[2][6] = 5;  // Location 3 to Hospital C
        graph[2][10] = 7; // Location 3 to Police Station 3

        graph[3][0] = 20; // Location 4 to Location 1
        graph[3][1] = 25; // Location 4 to Location 2
        graph[3][2] = 10; // Location 4 to Location 3
        graph[3][7] = 5;  // Location 4 to Hospital D
        graph[3][11] = 7; // Location 4 to Police Station 4

        // Symmetric distances
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] != Integer.MAX_VALUE) {
                    graph[j][i] = graph[i][j];
                }
            }
        }
    }

    private String findNearest(Map<String, Integer> targetLocations, int start) {
        int[] distances = dijkstra(start);
        int minDistance = Integer.MAX_VALUE;
        String nearestLocation = null;

        for (Map.Entry<String, Integer> entry : targetLocations.entrySet()) {
            int distance = distances[entry.getValue()];
            if (distance < minDistance) {
                minDistance = distance;
                nearestLocation = entry.getKey();
            }
        }

        return nearestLocation;
    }

    private int[] dijkstra(int start) {
        int n = graph.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != Integer.MAX_VALUE && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        return dist;
    }

    private int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    private void sendSMS(String phoneNumber, String message) {
        Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(TWILIO_PHONE_NUMBER), message).create();
    }

    private String getHospitalPhone(String hospital) {
        switch (hospital) {
            case "Hospital A":
                return "+918310330149";
            case "Hospital B":
                return "+917676808714";
            case "Hospital C":
                return "+917259241340";
            case "Hospital D":
                return "+919731045724";
            default:
                return "";
        }
    }

    private String getPolicePhone(String policeStation) {
        switch (policeStation) {
            case "Police Station 1":
                return "+918310330149";
            case "Police Station 2":
                return "+917676808714";
            case "Police Station 3":
                return "+917259241340";
            case "Police Station 4":
                return "+919731045724";
            default:
                return "";
        }
    }
}
