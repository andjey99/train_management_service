package com.example.train.ui;

import javax.swing.*;
import com.example.train.service.TrainService;
import com.example.train.model.Train;
import java.awt.*;
import java.time.LocalTime;

import java.util.UUID;

public class TrainPanel extends JPanel 
{
    private final TrainService trainService;
    private JTextField nameField, capacityField, sourceField, destinationField, timeField;
    private JTable trainTable;

    public TrainPanel(TrainService trainService) 
    {
        this.trainService = trainService;
        initComponents();
        refresh();
    }

    private void initComponents() 
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Train Management");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.WEST);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add Train"));
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        JLabel nameLabel = new JLabel("Train Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        // Capacity
        JLabel capacityLabel = new JLabel("Capacity:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(capacityLabel, gbc);

        capacityField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(capacityField, gbc);

        // Source
        JLabel sourceLabel = new JLabel("Source:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(sourceLabel, gbc);

        sourceField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(sourceField, gbc);

        // Destination
        JLabel destLabel = new JLabel("Destination:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(destLabel, gbc);

        destinationField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(destinationField, gbc);

        // Time
        JLabel timeLabel = new JLabel("Time (HH:mm):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(timeLabel, gbc);

        timeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(timeField, gbc);

        // Add Button
        JButton addButton = new JButton("Add Train");
        addButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addButton.setBackground(new Color(0, 120, 215));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> addTrain());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;
        panel.add(addButton, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Train Schedule"));

        String[] columns = { "ID", "Name", "Route", "Time", "Capacity" };
        Object[][] data = new Object[0][5];

        trainTable = new JTable(data, columns);
        trainTable.setDefaultEditor(Object.class, null);
        trainTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadFormFromTable();
            }
        }
    )
;
        JScrollPane scrollPane = new JScrollPane(trainTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadFormFromTable() 
    {
        int row = trainTable.getSelectedRow();
        if (row >= 0) 
        {
            // Optional: load data back into form
        }
    }

    private void addTrain() 
    {
        try {
            // Gather input data
            String name = nameField.getText().trim();
            String capacityStr = capacityField.getText().trim();
            String source = sourceField.getText().trim();
            String destination = destinationField.getText().trim();
            String timeStr = timeField.getText().trim();

            // Basic validation
            if (name.isEmpty() || source.isEmpty() || destination.isEmpty()) 
            {
                throw new IllegalArgumentException("All fields are required");
            }

            // Parse capacity
            int capacity = Integer.parseInt(capacityStr);

            // Parse time - this will throw DateTimeParseException if invalid
            LocalTime time = LocalTime.parse(timeStr);

            String id = UUID.randomUUID().toString();
            Train train = new Train(id, name, capacity, source, destination, time);
            trainService.addTrain(train);

            nameField.setText("");
            capacityField.setText("");
            sourceField.setText("");
            destinationField.setText("");
            timeField.setText("");

            refresh();
            JOptionPane.showMessageDialog(this,"Train added successfully","Success",JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (java.time.format.DateTimeParseException dtpe) 
        {
            JOptionPane.showMessageDialog(this, "Invalid! Please use HH:mm.","Validation Error",JOptionPane.WARNING_MESSAGE);
        } 
        catch (NumberFormatException nfe) 
        {
            JOptionPane.showMessageDialog(this, "Must be a valid number.", "Validation Error",JOptionPane.WARNING_MESSAGE);
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Something went wrong",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refresh() 
    {
        java.util.List<Train> trains = trainService.listTrains();
        Object[][] data = new Object[trains.size()][6];

        for (int i = 0; i < trains.size(); i++) 
        {
            Train train = trains.get(i);
            data[i][0] = train.getId();
            data[i][1] = train.getName();
            data[i][2] = train.getSource() + " -> " + train.getDestination();
            data[i][3] = train.getDepartureTime();
            data[i][4] = train.getCapacity();
            data[i][5] = train.getStatus();
        }

        trainTable.setModel(new javax.swing.table.DefaultTableModel(data,
                new String[] { "ID", "Name", "Route", "Time", "Capacity", "Status" }));
    }
}
