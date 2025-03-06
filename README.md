# Hotel Management System

This project is a hotel management tool developed in Java. It was created as part of my Cybersecurity Engineering studies, integrating fundamental concepts of object-oriented programming, exception handling, and data structures. The application allows you to securely and efficiently manage activities such as storing client data, reserving rooms of different types, managing room service, canceling reservations, and displaying the total bill.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)

## Features

- **Client Management:** Securely stores and handles client data using `ArrayList`.
- **Room Reservations:** Supports reservations for three types of rooms (luxury, matrimonial, and standard) with a display of their features and availability.
- **Room Service:** Allows adding additional charges for room service to reserved rooms.
- **Reservation Cancellation:** Option to cancel a room reservation, restoring its availability.
- **Total Bill Visualization:** Displays the total bill for a room, including the base price and any additional room service charges.
- **Exception Handling:** Implements a custom exception (`ReservationException`) to prevent reserving rooms that are already booked.
- **Interactive Menu:** The application is controlled by an interactive menu that runs until the user decides to exit.

## Technologies

- **Language:** Java
- **Concepts:** Object-oriented programming, exception handling, inheritance, ArrayList.
- **Development Environment:** Any Java-compatible IDE (e.g., Eclipse, IntelliJ IDEA, or Visual Studio Code).

## Installation

1. **Prerequisites:**
   - Ensure that Java JDK is installed (JDK 8 or higher is recommended).
   - Use an IDE or text editor of your choice.

2. **Clone the Repository:**
   ```bash
   git clone https://github.com/your_username/hotel-management-system.git
