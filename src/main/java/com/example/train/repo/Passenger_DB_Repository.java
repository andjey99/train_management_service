package com.example.train.repo;

import com.example.train.db.Database_Connection;
import com.example.train.model.Passenger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Passenger_DB_Repository implements Passenger_Repository {
    Database_Connection db;

    public Passenger_DB_Repository() {
        this.db = new Database_Connection();
    }

    @Override
    public void save(Passenger p) {
        try {
            Connection conn = db.getConnection();
            String q = "INSERT INTO passengers (id, name, email, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(q);

            pst.setString(1, p.getId());
            pst.setString(2, p.getName());
            pst.setString(3, p.getEmail());
            pst.setString(4, p.getPhone());

            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Passenger> findById(String id) {
        Passenger p = null;
        try {
            Connection conn = db.getConnection();
            String q = "SELECT * FROM passengers WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next() == true) {
                String pid = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                p = new Passenger(pid, name, email, phone);
            }
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (p != null) {
            return Optional.of(p);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Passenger> findAll() {
        List<Passenger> list = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            Statement st = conn.createStatement();
            String q = "SELECT * FROM passengers";

            ResultSet rs = st.executeQuery(q);

            while (rs.next() == true) {
                String pid = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                Passenger p = new Passenger(pid, name, email, phone);
                list.add(p);
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
            String q = "DELETE FROM passengers WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, id);
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
