package com.example.train;

import com.example.train.model.*;

import com.example.train.service.*;
import com.example.train.ui.MainFrame;
import com.example.train.ui.LoginDialog;
import javax.swing.SwingUtilities;
import java.time.LocalTime;

public class Main
{
    public static void main(String[] args)
    {
        // Set Look and Feel to Nimbus
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // If Nimbus is not available, fall back to default
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Initialize Database
        initializeDatabase();

        // Initialize Repositories
        com.example.train.repo.Train_Repository trainRepo = new com.example.train.repo.Train_DB_Repository();
        com.example.train.repo.Passenger_Repository passengerRepo = new com.example.train.repo.Passenger_DB_Repository();
        com.example.train.repo.Staff_Repository staffRepo = new com.example.train.repo.Staff_DB_Repository();
        com.example.train.repo.Booking_Repository bookingRepo = new com.example.train.repo.Booking_DB_Repository();

        // Initialize Services
        TrainService trainService = new TrainService(trainRepo);
        PassengerService passengerService = new PassengerService(passengerRepo);
        StaffService staffService = new StaffService(staffRepo);
        BookingService bookingService = new BookingService(bookingRepo);
        AuthService authService = new AuthService(staffService);

        // Seed Data
        if (trainService.listTrains().isEmpty())
        {
            // Realistic Trains
            trainService.addTrain(new Train("ICE-101", "ICE Berlin-Munich", 400, "Berlin", "Munich", LocalTime.of(8, 0)));
            trainService.addTrain(new Train("RE-5", "Regional Express", 150, "Cologne", "Dusseldorf", LocalTime.of(9, 30)));
            trainService.addTrain(new Train("IC-2020", "InterCity Hamburg", 250, "Frankfurt", "Hamburg", LocalTime.of(14, 15)));

            // LIVE DEMO TRAIN: Departs in 1 minute
            LocalTime demoTime = LocalTime.now().plusMinutes(1);
            trainService.addTrain(new Train("DEMO-LIVE", "Live Presentation Train", 100, "Classroom", "Success", demoTime));
        } 
        else
        {
            // Ensure Demo Train always exists for the pitch, even if DB is not empty
            LocalTime demoTime = LocalTime.now().plusMinutes(1);
            trainService.addTrain(new Train("DEMO-LIVE", "Live Presentation Train", 100, "Classroom", "Success", demoTime));
        }

        if (passengerService.listPassengers().isEmpty())
        {
            passengerService.addPassenger(new Passenger("P1", "Max Mustermann", "max@muster.de", "0171-1234567"));
            passengerService.addPassenger(new Passenger("P2", "Erika Musterfrau", "erika@muster.de", "0172-9876543"));
            passengerService.addPassenger(new Passenger("P3", "John Wick", "john@continental.com", "0111-0000000"));
        }

        if (staffService.listStaff().isEmpty())
        {
            // Add staff with passwords
            staffService.addStaff(new Staff("S1", "Admin User", "Admin", "admin@train.com"), "admin123");
            staffService.addStaff(new Staff("S2", "Ticket Inspector", "Staff", "staff@train.com"), "staff123");
        }

        // Launch UI
        SwingUtilities.invokeLater(() -> {
            LoginDialog loginDialog = new LoginDialog(null, authService);
            loginDialog.setVisible(true);

            if (loginDialog.isLoginSuccessful())
            {
                Staff currentStaff = loginDialog.getAuthenticatedStaff();
                MainFrame mainFrame = new MainFrame(currentStaff, trainService, passengerService, bookingService,
                        staffService);
                mainFrame.setVisible(true);
            } 
            else
            {
                System.exit(0);
            }
        });
    }

    private static void initializeDatabase() 
    {
        try (java.sql.Connection connection = new com.example.train.db.Database_Connection().getConnection();
                java.sql.Statement statement = connection.createStatement()) {

            String setupScript = java.nio.file.Files.readString(java.nio.file.Paths.get("setup.sql"));
            String[] commands = setupScript.split(";");

            for (String command : commands)
            {
                if (!command.trim().isEmpty())
                {
                    statement.execute(command);
                }
            }

            // Schema Migration: Add status column if it doesn't exist
            try
            {
                statement.execute("alter table trains add column status VARCHAR(20) default 'Scheduled'");
                System.out.println("Migrated 'trains' table: Added 'status' column.");
            } 
            catch (java.sql.SQLException e)
            {
                // Column likely already exists, ignore
            }

            System.out.println("Database initialized successfully.");
        } catch (Exception e) 
        {
            System.err.println("Error initializing database: " + e.getMessage());
            // Continue anyway, maybe tables already exist or connection failed but we want
            // to try running
        }
    }
}
