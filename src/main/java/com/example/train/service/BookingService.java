package com.example.train.service;

import com.example.train.model.Booking;
import java.util.*;

public class BookingService {
    // speicher für buchungen
    private Map<String, Booking> map = new HashMap<>();
    private Map<String, Set<Integer>> seats = new HashMap<>();

    private com.example.train.repo.Booking_Repository repo;

    public BookingService() {
        this.repo = null;
    }

    public BookingService(com.example.train.repo.Booking_Repository r) {
        this.repo = r;
        if (this.repo != null) {
            // alles laden
            for (Booking b : repo.findAll()) {
                map.put(b.getId(), b);

                // check if set exists
                if (seats.containsKey(b.getTrainId()) == false) {
                    seats.put(b.getTrainId(), new HashSet<>());
                }
                seats.get(b.getTrainId()).add(b.getSeatNumber());
            }
        }
    }

    public synchronized Booking createBooking(String id, String trainId, String passengerId, int seatNumber) {
        // erst schauen ob liste da ist
        if (seats.containsKey(trainId) == false) {
            seats.put(trainId, new HashSet<>());
        }

        Set<Integer> occupied = seats.get(trainId);

        boolean isTaken = false;
        if (occupied.contains(seatNumber) == true) {
            isTaken = true;
        }

        if (repo != null) {
            if (repo.seatOccupied(trainId, seatNumber) == true) {
                isTaken = true;
            }
        }

        if (isTaken == true) {
            throw new IllegalStateException("Seat " + seatNumber + " on train " + trainId + " is already booked");
        }

        Booking b = new Booking(id, trainId, passengerId, seatNumber);
        map.put(id, b);
        occupied.add(seatNumber);

        if (repo != null) {
            repo.save(b);
        }

        return b;
    }

    public Optional<Booking> getBooking(String id) {
        Booking b = map.get(id);
        if (b == null) {
            return Optional.empty();
        } else {
            return Optional.of(b);
        }
    }

    public List<Booking> listBookings() {
        List<Booking> list = new ArrayList<>();
        for (Booking b : map.values()) {
            list.add(b);
        }
        return list;
    }

    public synchronized void cancelBooking(String id) {
        Booking b = map.remove(id);
        if (b != null) {
            Set<Integer> set = seats.get(b.getTrainId());
            if (set != null) {
                set.remove(b.getSeatNumber());
            }

            if (repo != null) {
                repo.delete(id);
            }
        }
    }
}
