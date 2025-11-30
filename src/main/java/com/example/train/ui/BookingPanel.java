package com.example.train.ui;

import javax.swing.*;
import com.example.train.service.*;
import com.example.train.model.*;
import java.awt.*;
import java.util.UUID;

public class BookingPanel extends JPanel
{
    private final TrainService trainService;
    private final PassengerService passengerService;
    private final BookingService bookingService;

    private JComboBox<Train> trainCombo;
    private JSpinner seatSpinner;
    private JComboBox<Passenger> passengerCombo;
    private JTable bookingTable;
    private JButton bookButton, refreshButton;
    private JLabel priceLabel;

    public BookingPanel(TrainService trainService, PassengerService passengerService, BookingService bookingService) {
        this.trainService = trainService;
        this.passengerService = passengerService;
        this.bookingService = bookingService;
        initComponents();
        refresh();
    }

    private void initComponents() 
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Booking Management");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Booking form
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.WEST);

        // Bookings table
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel () 
    {
        JPanel panel = new JPanel();
        panel.setLayout (new GridBagLayout());
        panel.setBorder (BorderFactory.createTitledBorder("New Booking"));
        
        // sizing

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Train selection
        JLabel trainLabel = new JLabel("Train:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(trainLabel, gbc);

        trainCombo = new JComboBox<>(trainService.listTrains().toArray(new Train[0]));
        trainCombo.addActionListener(e -> updatePrice());
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(trainCombo, gbc);

        // Seat selection
        JLabel seatLabel = new JLabel("Seat Number:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(seatLabel, gbc);

        //spinner for seat selection
        seatSpinner = new JSpinner (new SpinnerNumberModel(1, 1, 200, 1));
        seatSpinner.setPreferredSize (new Dimension(150, 25)); // Make sure it's wide enough
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(seatSpinner, gbc);

        // Passenger selection
        JLabel passengerLabel = new JLabel("Passenger:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passengerLabel, gbc);

        passengerCombo = new JComboBox<>(passengerService.listPassengers().toArray(new Passenger[0]));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passengerCombo, gbc);

        // Price display
        JLabel priceHeaderLabel = new JLabel("Price:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(priceHeaderLabel, gbc);

        priceLabel = new JLabel("$0.00");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 18));   
        priceLabel.setForeground(new Color(0, 100, 0));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(priceLabel, gbc);

        // Book button
        bookButton = new JButton("Create Booking");
        bookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bookButton.setBackground(new Color(0, 120, 215));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.addActionListener(e -> createBooking());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10; // Make button taller
        panel.add(bookButton, gbc);

        // Refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refresh());
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(refreshButton, gbc);

        return panel;
    }

    private JPanel createTablePanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Current Bookings"));

        String[] columns = { "Booking ID", "Train", "Passenger", "Seat", "Status" };
        Object[][] data = new Object[0][5];
        
        bookingTable = new JTable(data, columns);
        bookingTable.setDefaultEditor(Object.class, null); // Read-only
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

   
    private void updatePrice() {
        Train selectedTrain = (Train) trainCombo.getSelectedItem();
        if (selectedTrain != null) {
            // Calculate a "dynamic" price based on the train's ID hash
            // This ensures the price is consistent for the same train but varies between
            // trains
            double basePrice = 50.0 + (Math.abs(selectedTrain.getId().hashCode()) % 50);
            priceLabel.setText(String.format("$%.2f", basePrice));
        } else {
            priceLabel.setText("$0.00");
        }
    }

    private void createBooking() 
    {
        try 
        {
            Train selectedTrain = (Train) trainCombo.getSelectedItem();
            Passenger selectedPassenger = (Passenger) passengerCombo.getSelectedItem();
            int seatNumber = (int) seatSpinner.getValue();

            if (selectedTrain == null || selectedPassenger == null) {
                JOptionPane.showMessageDialog(this, "Please select train and passenger.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String bookingId = UUID.randomUUID().toString();
            Booking booking = bookingService.createBooking(bookingId, selectedTrain.getId(), selectedPassenger.getId(),
                    seatNumber);
            JOptionPane.showMessageDialog(this, "Booking created successfully!\nID: " + booking.getId(), "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            refresh();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Booking Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refresh() {
        java.util.List<Booking> bookings = bookingService.listBookings();
        Object[][] data = new Object[bookings.size()][5];

        for (int i = 0; i < bookings.size(); i++) 
            {
            Booking booking = bookings.get(i);
            Train train = trainService.getTrain(booking.getTrainId()).orElse(null);
            Passenger passenger = passengerService.getPassenger(booking.getPassengerId()).orElse(null);

            data[i][0] = booking.getId().substring(0, 8) + "...";
            data[i][1] = train != null ? train.getName() : booking.getTrainId();
            data[i][2] = passenger != null ? passenger.getName() : booking.getPassengerId();
            data[i][3] = booking.getSeatNumber();
            data[i][4] = "Confirmed";
        }

        bookingTable.setModel(new javax.swing.table.DefaultTableModel(data,new String[] 
            { "Booking ID", "Train", "Passenger", "Seat", "Status" }));

        //refresh combos new trains/passengers were added
        if (trainCombo != null && passengerCombo != null) 
            {
            Object selectedTrain = trainCombo.getSelectedItem();
            Object selectedPassenger = passengerCombo.getSelectedItem();

            trainCombo.setModel(new DefaultComboBoxModel<>(trainService.listTrains().toArray(new Train[0])));
            passengerCombo
                    .setModel(new DefaultComboBoxModel<>(passengerService.listPassengers().toArray(new Passenger[0])));

            if (selectedTrain != null)
                trainCombo.setSelectedItem(selectedTrain);  
            if (selectedPassenger != null)
                passengerCombo.setSelectedItem(selectedPassenger);
            }
    }
}
