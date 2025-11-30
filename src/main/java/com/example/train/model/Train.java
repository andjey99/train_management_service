    package com.example.train.model;

    import java.time.LocalTime;
    import java.util.Objects;

    public class Train 
    {
        private final String id;
        private final String name;
        private final int capacity;
        private final String source;
        private final String destination;
        private final LocalTime departureTime;
        private String status;

        public Train (String id,String name,int capacity,String source,String destination,LocalTime departureTime) 
        {
            this.id = id;
            this.name = name;
            this.capacity = capacity;
            this.source = source;
            this.destination = destination;
            this.departureTime = departureTime;
            this.status = "scheduled"; // Our default status
        }

        public Train (String id,String name,int capacity,String source,String destination,LocalTime departureTime,String status) 
        {
            this.id = id;
            this.name = name;
            this.capacity = capacity;
            this.source = source;
            this.destination = destination;
            this.departureTime = departureTime;
            this.status = status;
        }

        public String getId() 
        {
            return id;
        }

        public String getName() 
        {
            return name;
        }

        public int getCapacity() 
        {
            return capacity;
        }

        public String getSource() 
        {
            return source;
        }

        public String getDestination() 
        {
            return destination;
        }

        public LocalTime getDepartureTime() 
        {
            return departureTime;
        }

        public String getStatus() 
        {
            return status;
        }

        public void setStatus(String status) 
        {
            this.status = status;
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
            Train otherTrain = (Train) object;
            return Objects.equals(id, otherTrain.id);
        }

        @Override
        public int hashCode()   
        {
            return Objects.hash(id);
        }

        @Override
        public String toString() 
        {
            return name + " (" +source + " - " +destination + ")";
        }
    }
