# Train Management System

A Java-based system for managing train connections, passengers, and bookings. The project follows a **3-Tier Architecture** and uses **Java Swing** for the user interface as well as **MySQL** for data storage.

## Architecture

The project is clearly structured:

1.  **UI Layer (`ui`)**: The graphical user interface (Swing Panels) that the user sees.
2.  **Service Layer (`service`)**: The business logic that mediates between the UI and the database.
3.  **Data Layer (`repo`, `db`, `model`)**: The data models and direct access to the MySQL database.

## Prerequisites

*   **Java 17** or higher
*   **MySQL Server** (running locally on port 3306)

## Installation & Setup

1.  **Setup Database:**
    *   Ensure your MySQL server is running.
    *   Execute the `setup.sql` script in your MySQL client to create the `train_system` database and tables.
    *   Check the credentials in `src/main/java/com/example/train/db/DatabaseConnection.java` (Default: user=`root`, password=`banmi`).

2.  **Start:**
    The project contains a PowerShell script for easy compiling and starting.

    ```powershell
    ./run.ps1
    ```

    The script takes care of:
    *   Cleaning old compiled files (`out` folder)
    *   Compiling all `.java` files
    *   Including the MySQL driver (`lib/mysql-connector-j.jar`)
    *   Starting the application

## Project Structure

*   `src/main/java/com/example/train`: The source code
    *   `ui`: Windows and Dialogs
    *   `service`: Logic (e.g. `BookingService`)
    *   `repo`: Database access (Repositories)
    *   `model`: Data classes (e.g. `Train`, `Booking`)
    *   `db`: Database connection
*   `lib`: External libraries (MySQL Connector)
*   `setup.sql`: SQL script for creating the database
