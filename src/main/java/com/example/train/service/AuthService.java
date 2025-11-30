package com.example.train.service;

import com.example.train.model.Staff;
import java.util.Optional;

public class AuthService 
{
    StaffService service;

    public AuthService (StaffService staffService) 
    {
        this.service = staffService;
    }

    public Optional <Staff> authenticate (String id,String pw) 
    {
        Optional<Staff> staff = service.getStaff (id);

        if (staff.isPresent() == true) 
        {
            Optional <String> isPassword = service.getPassword (id);

            if (isPassword.isPresent() == true && isPassword.get().equals(pw) == true) 
            {
                return staff;
            }
        }
        return Optional.empty();
    }

    public Staff getAuthenticatedStaff (String id,String pw) 
    {
        Optional<Staff> workers = authenticate (id,pw);

        if (workers.isPresent() == true) 
        {
            return workers.get();
        }   
        else 
        {
        throw new IllegalArgumentException ("Fault staff ID or password, please try again");
        }
    }
}
