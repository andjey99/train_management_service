package com.example.train.ui;

import javax.swing.*;
import com.example.train.service.PassengerService;
import com.example.train.model.Passenger;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

// my passenger panel
public class PassengerPanel extends JPanel {

    PassengerService service; // no private final
    JTextField nameField;
    JTextField emailField;
    JTextField phoneField;
    JTable table;

    public PassengerPanel(PassengerService s) {
        this.service = s;

        // layout
        this.setLayout(new BorderLayout());

        // title
        JLabel titleLabel = new JLabel("Passenger Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(titleLabel, BorderLayout.NORTH);

        // make form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Passenger"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // name stuff
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nameField, gbc);

        // email stuff
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(emailField, gbc);

        // phone stuff
        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(phoneField, gbc);

        // button
        JButton addButton = new JButton("Add Passenger");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get text
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();

                // check empty
                if (name.equals("") || email.equals("")) {
                    JOptionPane.showMessageDialog(null, "Name and Email are required.");
                    return;
                }

                // check email
                if (email.indexOf("@") == -1) {
                    JOptionPane.showMessageDialog(null, "Bad email.");
                    return;
                }

                // save
                String id = UUID.randomUUID().toString();
                Passenger p = new Passenger(id, name, email, phone);
                service.addPassenger(p);

                // clear
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");

                // update table
                refresh();
                JOptionPane.showMessageDialog(null, "Added!");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        this.add(formPanel, BorderLayout.WEST);

        // make table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Passenger List"));

        String[] cols = { "ID", "Name", "Email", "Phone" };
        Object[][] d = new Object[0][4];

        table = new JTable(d, cols);
        JScrollPane sp = new JScrollPane(table);
        tablePanel.add(sp, BorderLayout.CENTER);

        this.add(tablePanel, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        java.util.List<Passenger> list = service.listPassengers();
        Object[][] d = new Object[list.size()][4];

        for (int i = 0; i < list.size(); i++) {
            Passenger p = list.get(i);
            d[i][0] = p.getId();
            d[i][1] = p.getName();
            d[i][2] = p.getEmail();
            d[i][3] = p.getPhone();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(d, new String[] { "ID", "Name", "Email", "Phone" }));
    }
}
