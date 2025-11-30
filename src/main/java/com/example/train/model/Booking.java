package com.example.train.model;

import java.util.Objects;
import java.io.Serializable; 


public class Booking implements Serializable 
{

    private static final long serialVersionUID = 1L;

    
    private final String buchungsKennung; // id
    private final String zugKennung;      // trainId
    private final String reisenderKennung; // passengerId
    private final int sitzNummer;

    public Booking (String bookingId, String trainRef, String passengerTag, int seatPos) 
    {
        this.buchungsKennung = bookingId;
        this.zugKennung = trainRef;
        this.reisenderKennung = passengerTag;
        this.sitzNummer = seatPos;
    }

    public String getId() 
    {
        return this.buchungsKennung;
    }

    public String getTrainId() 
    {
        return zugKennung;
    }

    public String getPassengerId() 
    {
        return reisenderKennung;
    }

    public int getSeatNumber() 
    {
        return sitzNummer;
    }

    

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) 
        {
            return false;
        }
        
        Booking that = (Booking) obj;

        return Objects.equals(buchungsKennung, that.buchungsKennung);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(buchungsKennung);
    }

    @Override
    public String toString() 
    {
        return "Booking-Eintrag [ID: " + buchungsKennung
                + ", Zug: " + zugKennung
                + ", Passagier: " + reisenderKennung
                + ", Platz: " + sitzNummer + "]";
    }
}