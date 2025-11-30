package com.example.train.repo;

import com.example.train.db.Database_Connection;
import com.example.train.model.Passenger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Passenger_DB_Repository implements Passenger_Repository 
{
    Database_Connection db;

    public Passenger_DB_Repository() 
    {
        this.db = new Database_Connection();
    }

    @Override
    public void save (Passenger psng) 
    {
        try {
            Connection conn = db.getConnection();
            String queryString = "INSERT INTO passengers (id, name, email, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement psst = conn.prepareStatement(queryString);

            psst.setString (1, psng.getId());
            psst.setString (2, psng.getName());
            psst.setString (3, psng.getEmail());
            psst.setString (4, psng.getPhone());

            psst.executeUpdate();
            conn.close();
        } catch (Exception e) 
        {
            throw new RuntimeException (e);
        }
    }

    @Override
    public Optional<Passenger> findById (String id) 
    {
        Passenger psng = null;
        try {
            Connection conn = db.getConnection();
            String queryString = "SELECT * FROM passengers WHERE id = ?";
            PreparedStatement psst = conn.prepareStatement(queryString);
            psst.setString (1,id);

            ResultSet results = psst.executeQuery();

            if (results.next() == true) 
            {
                String pid = results.getString ("id");
                String name = results.getString ("name");
                String email = results.getString ("email");
                String phone = results.getString ("phone");

                psng = new Passenger(pid,name,email,phone);
            }
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException (e);
        }

        if (psng != null) 
        {
            return Optional.of(psng);
        } 
        else 
        {
            return Optional.empty();
        }
    }

    @Override
    public List <Passenger> findAll () 
    {
        List <Passenger> list = new ArrayList<>();
        try 
        {
            Connection conn = db.getConnection ();
            Statement stmt = conn.createStatement ();
            String queryString = "SELECT * FROM passengers";

            ResultSet results = stmt.executeQuery (queryString);

            while (results.next() == true) 
            {
                String pid = results.getString ("id");
                String name = results.getString ("name");
                String email = results.getString ("email");
                String phone = results.getString ("phone");

                Passenger psng = new Passenger(pid, name, email, phone);
                list.add(psng);
            }
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException (e);
        }
        return list;
    }

    @Override
    public void delete(String id) 
    {
        try 
        {
            Connection conn = db.getConnection ();
            String queryString ="delete from passengers where id = ?";
            PreparedStatement psst = conn.prepareStatement(queryString);
            psst.setString(1,id);
            psst.executeUpdate();    
            conn.close();
        } 
        catch (Exception e) 
        {       
            throw new RuntimeException(e);
        }
    }
}
