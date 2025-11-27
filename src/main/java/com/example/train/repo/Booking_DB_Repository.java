package com.example.train.repo;

import com.example.train.db.Database_Connection;
import com.example.train.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Booking_DB_Repository implements Booking_Repository 
{
    Database_Connection db;

    public Booking_DB_Repository () 
    {
        this.db = new Database_Connection ();
    }

    @Override
    public void save (Booking b) 
    {
        try 
        {
            // get connection
            Connection connection = db.getConnection ();

            // sql query
            String query = "INSERT INTO bookings (id, train_id, passenger_id, seat_number) VALUES (?, ?, ?, ?)";

            // prepare statement
            PreparedStatement prepstate = connection.prepareStatement (query);

            // set values
            prepstate.setString (1,b.getId ());
            prepstate.setString (2,b.getTrainId ());
            prepstate.setString (3,b.getPassengerId ());
            prepstate.setInt (4,b.getSeatNumber ());

            // execute
            prepstate.executeUpdate();

            // close
            connection.close();
        } catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Booking> findById (String id) 
    {
        Booking b = null;
        try 
        {
            Connection connection = db.getConnection ();
            String query = "select * from bookings where id = ?";
            PreparedStatement prepstate = connection.prepareStatement (query);
            prepstate.setString (1,id);

            ResultSet results = prepstate.executeQuery();

            // check if result exists
            if (results.next () == true) 
            {
                // get data from db
                String bid = results.getString ("id");
                String tid = results.getString ("train_id");
                String pid = results.getString ("passenger_id");
                int seat = results.getInt ("seat_number");

                // create object
                b = new Booking(bid,tid,pid,seat);
            }
            connection.close();
        } catch (Exception e) 
        {
            throw new RuntimeException(e);
        }

        // return optional
        if (b != null) 
        {
            return Optional.of(b);
        } 
        
        else 
        {
            return Optional.empty();
        }
    }

    @Override
    public List <Booking> findAll () 
    {
        List <Booking> newlist = new ArrayList<>();
        try 
        {
            Connection connection = db.getConnection ();
            Statement st = connection.createStatement ();

            // get all bookings
            ResultSet results = st.executeQuery ("select * from bookings");

            // loop through results
            while (results.next () == true) 
            {
                // get data
                String bid = results.getString ("id");
                String tid = results.getString ("train_id");
                String pid = results.getString ("passenger_id");
                int seat = results.getInt ("seat_number");

                // make new booking
                Booking b = new Booking(bid, tid, pid, seat);

                // add to list
                newlist.add(b);
            }
            connection.close();
        } catch (Exception e) 
        {
            throw new RuntimeException  (e);
        }
        return newlist;
    }

    @Override
    public void delete (String id) 
    {
        try 
        {
            Connection connection = db.getConnection();
            String query = "DELETE FROM bookings WHERE id = ?";
            PreparedStatement prepstate = connection.prepareStatement (query);
            prepstate.setString (1,id);
            prepstate.executeUpdate();
            connection.close();
        } catch (Exception e) 
        {
            throw new RuntimeException(e);
        }   
    }

    @Override
    public boolean seatOccupied(String tid,int seat) 
    {
        boolean taken = false;
        try 
        {
            Connection connection = db.getConnection();
            String query = "SELECT COUNT(*) FROM bookings WHERE train_id = ? AND seat_number = ?";
            PreparedStatement prepstate = connection.prepareStatement (query);
            prepstate.setString (1,tid);
            prepstate.setInt (2,seat);

            ResultSet results = prepstate.executeQuery ();
            if (results.next () == true) 
            {
                int count = results.getInt (1);
                if (count >= 0) 
                {
                    taken = true;
                }
            }
            connection.close();
        } catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
        return taken;
    }
}
