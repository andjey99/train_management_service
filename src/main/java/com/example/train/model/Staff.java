package com.example.train.model;

import java.util.Objects;

public class Staff
{
    private final String id;
    private final String name;
    private final String role;
    private final String email;

    public Staff (String id,String name,String role,String email) 
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    public String getId() 
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public String getRole() 
    {
        return role;
    }

    public String getEmail() 
    {
        return email;
    }

    @Override
    public boolean equals(Object object) 
    {
        if (this == object) 
        {
            return true;
        }
        if (object == null) 
        {
            return false;
        }
        if (getClass() != object.getClass()) 
        {
            return false;
        }
        Staff otherStaff = (Staff) object;
        return Objects.equals(id, otherStaff.id);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        String s = "";
        s = s + "Staff: ";
        s = s + name;
        s = s + " (";
        s = s + role;
        s = s + ")";
        return s;
    }
}
