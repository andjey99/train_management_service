CREATE DATABASE IF NOT EXISTS train_system;
USE train_system;

CREATE TABLE IF NOT EXISTS bookings 
(
    id VARCHAR(50) PRIMARY KEY,
    train_id VARCHAR(50) NOT NULL,
    passenger_id VARCHAR(50) NOT NULL,
    seat_number INT NOT NULL
);

CREATE TABLE IF NOT EXISTS trains 
(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Scheduled'
);

CREATE TABLE IF NOT EXISTS passengers 
(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS staff 
(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
)
;
