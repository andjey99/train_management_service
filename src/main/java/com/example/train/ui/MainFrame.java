package com.example.train.ui;

import javax.swing.*;
import com.example.train.service.*;
import com.example.train.model.Staff;
import java.awt.*;

public class MainFrame extends JFrame {
    private final Staff currentStaff;
    private final TrainService trainService;
    private final PassengerService passengerService;
    private final BookingService bookingService;
    private final StaffService staffService;

    private JPanel contentPanel;
    private JLabel welcomeLabel;
    private CardLayout cardLayout;

    // Panels
    private BookingPanel bookingPanel;
    private TrainPanel trainPanel;
    private PassengerPanel passengerPanel;
    private StaffPanel staffPanel;

    // Dashboard Stats
    private JLabel trainCountLabel;
    private JLabel bookingCountLabel;
    private JLabel passengerCountLabel;
    private JLabel staffCountLabel;

    // Timer for real-time updates
    private Timer updateTimer;

    public MainFrame(Staff staff, TrainService trainService, PassengerService passengerService,
            BookingService bookingService, StaffService staffService) {
        this.currentStaff = staff;
        this.trainService = trainService;
        this.passengerService = passengerService;
        this.bookingService = bookingService;
        this.staffService = staffService;
        initComponents();
        startRealTimeUpdates();
    }

    private void initComponents() {
        setTitle("Train Management System - " + currentStaff.getName());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start in full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(70, 130, 180));
        topPanel.setPreferredSize(new Dimension(1200, 60));

        welcomeLabel = new JLabel("Welcome, " + currentStaff.getName() + " (" + currentStaff.getRole() + ")");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton, BorderLayout.EAST);

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Create views
        JPanel dashboardPanel = createDashboardPanel();
        bookingPanel = new BookingPanel(trainService, passengerService, bookingService);
        trainPanel = new TrainPanel(trainService);
        passengerPanel = new PassengerPanel(passengerService);
        staffPanel = new StaffPanel(staffService);

        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(bookingPanel, "Booking");
        contentPanel.add(trainPanel, "Trains");
        contentPanel.add(passengerPanel, "Passengers");
        contentPanel.add(staffPanel, "Staff");

        JPanel navigationPanel = createNavigationPanel();

        add(topPanel, BorderLayout.NORTH);
        add(navigationPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void startRealTimeUpdates() {
        // Poll every 2 seconds
        updateTimer = new Timer(2000, e -> {
            trainService.updateTrainStatuses(); // Check for completed trains
            updateDashboardStats();
            refreshCurrentPanel();
        });
        updateTimer.start();
    }

    private void updateDashboardStats() {
        if (trainCountLabel != null) {
            trainCountLabel.setText(String.valueOf(trainService.listTrains().size()));
        }
        if (bookingCountLabel != null) {
            bookingCountLabel.setText(String.valueOf(bookingService.listBookings().size()));
        }
        if (passengerCountLabel != null) {
            passengerCountLabel.setText(String.valueOf(passengerService.listPassengers().size()));
        }
        if (staffCountLabel != null) {
            staffCountLabel.setText(String.valueOf(staffService.listStaff().size()));
        }
    }

    private void refreshCurrentPanel() {
        // Determine which panel is visible and refresh it
        // Note: CardLayout doesn't easily tell us the current card name,
        // so we can track it or check visibility.

        if (bookingPanel.isVisible()) {
            bookingPanel.refresh();
        } else if (trainPanel.isVisible()) {
            trainPanel.refresh();
        } else if (passengerPanel.isVisible()) {
            passengerPanel.refresh();
        } else if (staffPanel.isVisible()) {
            staffPanel.refresh();
        }
    }

    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 700));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        JLabel navTitle = new JLabel("Navigation");
        navTitle.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(navTitle);
        panel.add(Box.createVerticalStrut(15));

        JButton dashboardBtn = createNavButton("Dashboard");
        dashboardBtn.addActionListener(e -> showPanel("Dashboard"));
        panel.add(dashboardBtn);

        panel.add(Box.createVerticalStrut(5));

        JButton bookingBtn = createNavButton("Bookings");
        bookingBtn.addActionListener(e -> showPanel("Booking"));
        panel.add(bookingBtn);

        panel.add(Box.createVerticalStrut(5));

        JButton trainBtn = createNavButton("Trains");
        trainBtn.addActionListener(e -> showPanel("Trains"));
        panel.add(trainBtn);

        panel.add(Box.createVerticalStrut(5));

        JButton passengerBtn = createNavButton("Passengers");
        passengerBtn.addActionListener(e -> showPanel("Passengers"));
        panel.add(passengerBtn);

        panel.add(Box.createVerticalStrut(5));

        JButton staffBtn = createNavButton("Staff");
        staffBtn.addActionListener(e -> showPanel("Staff"));
        panel.add(staffBtn);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(140, 40));
        btn.setFont(new Font("Arial", Font.PLAIN, 11));
        return btn;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(title, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize stat labels
        trainCountLabel = new JLabel("0");
        trainCountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        trainCountLabel.setHorizontalAlignment(JLabel.CENTER);

        bookingCountLabel = new JLabel("0");
        bookingCountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bookingCountLabel.setHorizontalAlignment(JLabel.CENTER);

        passengerCountLabel = new JLabel("0");
        passengerCountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passengerCountLabel.setHorizontalAlignment(JLabel.CENTER);

        staffCountLabel = new JLabel("0");
        staffCountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        staffCountLabel.setHorizontalAlignment(JLabel.CENTER);

        // Initial update
        updateDashboardStats();

        statsPanel.add(createStatCard("Total Trains", trainCountLabel));
        statsPanel.add(createStatCard("Total Bookings", bookingCountLabel));
        statsPanel.add(createStatCard("Total Passengers", passengerCountLabel));
        statsPanel.add(createStatCard("Staff Members", staffCountLabel));

        panel.add(statsPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }

    private void logout() {
        if (updateTimer != null) {
            updateTimer.stop();
        }
        dispose();
        System.exit(0);
    }
}
