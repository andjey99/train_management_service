package com.example.train.repo;

import com.example.train.model.Booking;

import java.util.List;
import java.util.Optional;

public interface Booking_Repository
 {
    void save (Booking booking);

    Optional<Booking> findById (String id);

    List<Booking> findAll ();

    void delete(String id);

    boolean seatOccupied(String trainId, int seatNumber);
}

