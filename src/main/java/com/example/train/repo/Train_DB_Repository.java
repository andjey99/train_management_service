package com.example.train.repo;

import com.example.train.db.Database_Connection;
import com.example.train.model.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Train_DB_Repository implements Train_Repository 
{
    Database_Connection db;

    public Train_DB_Repository () 
    {
        this.db = new Database_Connection ();
    }

    public void save (Train train) 
    {
        try 
        {
            Connection connection = db.getConnection();

            // try update first
            String newUpdateString = "Update trains set name=?, capacity=?, source=?, destination=?, departure_time=?, status=? where id=?";
            PreparedStatement statement = connection.prepareStatement(newUpdateString);

            statement.setString(1, train.getName());
            statement.setInt(2, train.getCapacity());
            statement.setString(3, train.getSource());
            statement.setString(4, train.getDestination());
            statement.setTime(5, Time.valueOf(train.getDepartureTime()));
            statement.setString(6, train.getStatus());
            statement.setString(7, train.getId());

            int counter = statement.executeUpdate();

            if (counter == 0) 
            {
                // if update failed, insert
                String insertString = "INSERT INTO trains (id, name, capacity, source, destination, departure_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement2 = connection.prepareStatement(insertString);

                statement2.setString(1, train.getId());
                statement2.setString(2, train.getName());
                statement2.setInt(3, train.getCapacity());
                statement2.setString(4, train.getSource());
                statement2.setString(5, train.getDestination());
                statement2.setTime(6, Time.valueOf(train.getDepartureTime()));
                statement2.setString(7, train.getStatus());

                statement2.executeUpdate();
            }

            connection.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
    }

    public Optional<Train> findById (String id) 
    {
        Train train = null;
        try 
        {
            Connection connection = db.getConnection();
            String selectString = "select * from trains where id = ?";
            PreparedStatement statement = connection.prepareStatement(selectString);
            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next() == true) 
            {
                String tidString = result.getString("id");
                String nameString = result.getString("name");
                int capString = result.getInt("capacity");
                String srcString = result.getString("source");
                String destString = result.getString("destination");
                Time timeString = result.getTime("departure_time");
                String statusString = result.getString("status");

                if (statusString == null) 
                {
                    statusString = "Scheduled";
                }

                train = new Train(tidString,nameString,capString,srcString,destString,timeString.toLocalTime(),statusString);
            }
            connection.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }

        if (train != null) 
        {
            return Optional.of(train);
        } 
        else 
        {
            return Optional.empty();
        }
    }

    public List<Train> findAll() 
    {
        List<Train> list = new ArrayList<>();
        try 
        {
            Connection conn = db.getConnection ();
            Statement stat = conn.createStatement ();
            String querie = "select * from trains";

            ResultSet result = stat.executeQuery(querie);

            while (result.next() == true) 
            {
                String tidsString = result.getString ("id");
                String nameString = result.getString ("name");
                int capString = result.getInt ("capacity");
                String sourceString = result.getString ("source");
                String destinationString = result.getString ("destination");
                Time timeString = result.getTime ("departure_time");
                String statusString = result.getString("status");

                if (statusString == null) 
                {
                    statusString = "Scheduled";
                }   

                Train t = new Train(tidsString,nameString,capString,sourceString,destinationString,timeString.toLocalTime(),statusString);
                list.add(t);
            }
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void delete(String id) 
    {
        try 
        {
            Connection conn = db.getConnection ();
            String quey = "delete from trains where id = ?";
            PreparedStatement statment  = conn.prepareStatement (quey);
            statment.setString (1,id);
            statment.executeUpdate ();
            conn.close ();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException (e);
        }
    }
}
