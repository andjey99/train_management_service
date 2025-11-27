package com.example.train.service;

import com.example.train.model.Passenger;

import java.util.List;
import java.util.Optional;

public class PassengerService {
    private final com.example.train.repo.Passenger_Repository repository;

    public PassengerService(com.example.train.repo.Passenger_Repository repository) {
        this.repository = repository;
    }

    public void addPassenger(Passenger passenger) {
        repository.save(passenger);
    }

    public Optional<Passenger> getPassenger(String id) {
        return repository.findById(id);
    }

    public List<Passenger> listPassengers() {
        return repository.findAll();
    }
}
