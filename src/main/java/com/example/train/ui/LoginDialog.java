package com.example.train.ui;

import javax.swing.*;
import com.example.train.service.AuthService;
import com.example.train.model.Staff;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog 
{
    private JTextField staffIdField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;
    private AuthService authService;
    private Staff authenticatedStaff = null;
    private boolean success = false;

    public LoginDialog (JFrame parent, AuthService authService) 
    {
        super(parent, "Train Management System - Login", true);
        this.authService = authService;
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() 
    {
        setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        setSize (350, 200);
        JPanel panel = new JPanel();
        panel.setLayout (new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets (5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont (new Font ("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel staffIdLabel = new JLabel("Staff ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(staffIdLabel, gbc);

        staffIdField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(staffIdField, gbc);

        JLabel passwordLabel = new JLabel ("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        loginButton.addActionListener(this::handleLogin);
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        // Enable "Enter" key to trigger login
        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin (ActionEvent e) 
    {
        String staffId = staffIdField.getText().trim();
        String password = new String(passwordField.getPassword());

        try {
            authenticatedStaff = authService.getAuthenticatedStaff(staffId, password);
            success = true;
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid staff ID or password.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    public boolean isLoginSuccessful()   
    {
        return success;
    }

    public Staff getAuthenticatedStaff() 
    {
        return authenticatedStaff;
    }
}
