package com.example.train.service;

import com.example.train.model.Staff;

import java.util.List;
import java.util.Optional;

public class StaffService {
    private com.example.train.repo.Staff_Repository repo;

    public StaffService(com.example.train.repo.Staff_Repository r) {
        this.repo = r;
    }

    public void addStaff(Staff s) {
        repo.save(s);
    }

    public void addStaff(Staff s, String pw) {
        if (repo instanceof com.example.train.repo.Staff_DB_Repository) {
            com.example.train.repo.Staff_DB_Repository dbRepo = (com.example.train.repo.Staff_DB_Repository) repo;
            dbRepo.save(s, pw);
        } else {
            repo.save(s);
        }
    }

    public Optional<Staff> getStaff(String id) {
        return repo.findById(id);
    }

    public List<Staff> listStaff() {
        return repo.findAll();
    }

    public Optional<String> getPassword(String id) {
        if (repo instanceof com.example.train.repo.Staff_DB_Repository) {
            com.example.train.repo.Staff_DB_Repository dbRepo = (com.example.train.repo.Staff_DB_Repository) repo;
            return dbRepo.getPassword(id);
        }
        return Optional.empty();
    }
}
