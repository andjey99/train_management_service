package com.example.train.repo;

import com.example.train.db.Database_Connection;
import com.example.train.model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Staff_DB_Repository implements Staff_Repository
 {
    Database_Connection db;

    public Staff_DB_Repository() 
    {
        this.db = new Database_Connection();
    }

    @Override
    public void save    (Staff staff) 
    {
        try 
        {
            Connection conn = db.getConnection ();
            String queryString = "INSERT INTO staff (id, name, role, email, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement (queryString);

            pst.setString (1,staff.getId());
            pst.setString (2,staff.getName());
            pst.setString (3,staff.getRole());
            pst.setString (4,staff.getEmail());
            pst.setString (5,"password123");

            pst.executeUpdate ();
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException (e);
        }
    }

    public void save (Staff staff, String pw) 
    {
        try 
        {
            Connection conn = db.getConnection();
            String queryString = "INSERT INTO staff (id, name, role, email, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(queryString);

            pst.setString (1,staff.getId());
            pst.setString (2,staff.getName());
            pst.setString (3,staff.getRole());
            pst.setString (4,staff.getEmail());
            pst.setString (5,pw);

            pst.executeUpdate();
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException (e);
        }
    }

    @Override
    public Optional <Staff> findById (String id) 
    {
        Staff staff = null;
        try 
        {
            Connection conn = db.getConnection();
            String queryString = "SELECT * FROM staff WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement (queryString);
            pst.setString (1, id);

            ResultSet result = pst.executeQuery();

            if (result.next() == true) {
                String sid = result.getString("id");
                String name = result.getString("name");
                String role = result.getString("role");
                String email = result.getString("email");

                staff = new Staff(sid, name, role, email);
            }
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException (e);
        }

        if (staff != null) 
        {
            return Optional.of(staff);
        } 
        else 
        {
            return Optional.empty();
        }
    }

    @Override
    public Optional <Staff> findByEmail (String email) 
    {
        Staff staff = null;
        try 
        {
            Connection conn = db.getConnection();
            String queryString = "SELECT * FROM staff WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement (queryString);
            pst.setString (1, email);

            ResultSet result = pst.executeQuery();

            if (result.next() == true) 
            {
                String sid = result.getString ("id");
                String name = result.getString ("name");
                String role = result.getString ("role");
                String em = result.getString ("email");

                staff = new Staff (sid,name,role,em);
            }
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }

        if (staff != null) 
        {
            return Optional.of(staff);
        } 
        else 
        {
            return Optional.empty();
        }
    }

    public Optional<String> getPassword (String id) 
    {
        String pw = null;
        try 
        {
            Connection conn = db.getConnection();
            String queryString = "SELECT password FROM staff WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement (queryString);
            pst.setString (1, id);

            ResultSet result = pst.executeQuery();

            if (result.next() == true) 
            {
                pw = result.getString("password");
            }
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }

        if (pw != null) 
        {
            return Optional.of(pw);
        } 
        else 
        {
            return Optional.empty();
        }
    }

    @Override
    public List<Staff> findAll() 
    {
        List<Staff> list = new ArrayList<>();
        try 
        {
            Connection conn = db.getConnection();
            Statement st = conn.createStatement();
            String queryString = "SELECT * FROM staff";

            ResultSet result = st.executeQuery(queryString);

            while (result.next() == true) 
            {
                String sid = result.getString ("id");
                String name = result.getString ("name");
                String role = result.getString ("role");
                String email = result.getString ("email");

                Staff s = new Staff (sid,name,role,email);
                list.add(s);
            }
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void delete(String id) 
    {
        try 
        {
            Connection conn = db.getConnection();
            String queryString = "DELETE FROM staff WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(queryString);
            pst.setString(1, id);
            pst.executeUpdate();
            conn.close();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
    }
}
