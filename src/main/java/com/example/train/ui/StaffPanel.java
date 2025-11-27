package com.example.train.ui;

import javax.swing.*;
import com.example.train.service.StaffService;
import com.example.train.model.Staff;
import java.awt.*;

public class StaffPanel extends JPanel {
    private final StaffService staffService;
    private JTable staffTable;

    public StaffPanel(StaffService staffService) {
        this.staffService = staffService;
        initComponents();
        refresh();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Staff Directory");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Staff Members"));

        String[] columns = { "ID", "Name", "Role", "Email" };
        Object[][] data = new Object[0][4];

        staffTable = new JTable(data, columns);
        staffTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(staffTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public void refresh() {
        java.util.List<Staff> staffList = staffService.listStaff();
        Object[][] data = new Object[staffList.size()][4];

        for (int i = 0; i < staffList.size(); i++) {
            Staff staff = staffList.get(i);
            data[i][0] = staff.getId();
            data[i][1] = staff.getName();
            data[i][2] = staff.getRole();
            data[i][3] = staff.getEmail();
        }

        staffTable.setModel(
                new javax.swing.table.DefaultTableModel(data, new String[] { "ID", "Name", "Role", "Email" }));
    }
}
