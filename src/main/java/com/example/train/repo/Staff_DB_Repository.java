package com.example.train.repo;

import com.example.train.db.Database_Connection;
import com.example.train.model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Staff_DB_Repository implements Staff_Repository {
    Database_Connection db;

    public Staff_DB_Repository() {
        this.db = new Database_Connection();
    }

    @Override
    public void save(Staff s) {
        try {
            Connection conn = db.getConnection();
            String q = "INSERT INTO staff (id, name, role, email, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(q);

            pst.setString(1, s.getId());
            pst.setString(2, s.getName());
            pst.setString(3, s.getRole());
            pst.setString(4, s.getEmail());
            pst.setString(5, "password123");

            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Staff s, String pw) {
        try {
            Connection conn = db.getConnection();
            String q = "INSERT INTO staff (id, name, role, email, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(q);

            pst.setString(1, s.getId());
            pst.setString(2, s.getName());
            pst.setString(3, s.getRole());
            pst.setString(4, s.getEmail());
            pst.setString(5, pw);

            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Staff> findById(String id) {
        Staff s = null;
        try {
            Connection conn = db.getConnection();
            String q = "SELECT * FROM staff WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next() == true) {
                String sid = rs.getString("id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String email = rs.getString("email");

                s = new Staff(sid, name, role, email);
            }
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (s != null) {
            return Optional.of(s);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Staff> findByEmail(String email) {
        Staff s = null;
        try {
            Connection conn = db.getConnection();
            String q = "SELECT * FROM staff WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();

            if (rs.next() == true) {
                String sid = rs.getString("id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String em = rs.getString("email");

                s = new Staff(sid, name, role, em);
            }
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (s != null) {
            return Optional.of(s);
        } else {
            return Optional.empty();
        }
    }

    public Optional<String> getPassword(String id) {
        String pw = null;
        try {
            Connection conn = db.getConnection();
            String q = "SELECT password FROM staff WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next() == true) {
                pw = rs.getString("password");
            }
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (pw != null) {
            return Optional.of(pw);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Staff> findAll() {
        List<Staff> list = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            Statement st = conn.createStatement();
            String q = "SELECT * FROM staff";

            ResultSet rs = st.executeQuery(q);

            while (rs.next() == true) {
                String sid = rs.getString("id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String email = rs.getString("email");

                Staff s = new Staff(sid, name, role, email);
                list.add(s);
            }
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void delete(String id) {
        try {
            Connection conn = db.getConnection();
            String q = "DELETE FROM staff WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, id);
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
