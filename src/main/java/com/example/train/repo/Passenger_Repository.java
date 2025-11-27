package com.example.train.repo;

import com.example.train.model.Passenger;
import java.util.List;
import java.util.Optional;

public interface Passenger_Repository {
    void save(Passenger passenger);

    Optional<Passenger> findById(String id);

    List<Passenger> findAll();

    void delete(String id);
}
