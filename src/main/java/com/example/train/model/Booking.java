package com.example.train.model;

import java.util.Objects;

public class Booking {
    private final String id;
    private final String trainId;
    private final String passengerId;
    private final int seatNumber;

    public Booking(String bookingId, String trainId, String passengerId, int seatNumber) {
        this.id = bookingId;
        this.trainId = trainId;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
    }

    public String getId() {
        return id;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        Booking otherBooking = (Booking) object;
        return Objects.equals(id, otherBooking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Booking{" + id + ", train=" + trainId + ", passenger=" + passengerId + ", seat=" + seatNumber + '}';
    }
}
