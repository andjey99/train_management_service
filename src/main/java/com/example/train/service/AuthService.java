package com.example.train.service;

import com.example.train.model.Staff;
import java.util.Optional;

public class AuthService {
    StaffService s; // service

    public AuthService(StaffService staffService) {
        this.s = staffService;
    }

    public Optional<Staff> authenticate(String id, String pw) {
        Optional<Staff> staff = s.getStaff(id);

        if (staff.isPresent() == true) {
            Optional<String> stored = s.getPassword(id);

            if (stored.isPresent() == true && stored.get().equals(pw) == true) {
                return staff;
            }
        }
        return Optional.empty();
    }

    public Staff getAuthenticatedStaff(String id, String pw) throws IllegalArgumentException {
        Optional<Staff> s = authenticate(id, pw);

        if (s.isPresent() == true) {
            return s.get();
        } else {
            throw new IllegalArgumentException("Invalid staff ID or password");
        }
    }
}
