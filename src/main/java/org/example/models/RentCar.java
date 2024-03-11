package org.example.models;

import java.sql.*;
import java.util.Scanner;

public class RentCar {
        public static void rentCar(Connection connection, Scanner sc) {
            displayCars(connection);

            System.out.print("Enter Car Id: ");
            int carId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter your name: ");
            String customerName = sc.nextLine();
            System.out.print("Enter the number of days for rental: ");
            int rentalDays = sc.nextInt();


            storeRentalRecord(connection, carId, rentalDays, customerName);

            double totalPrice = calculateBill(connection, carId, rentalDays);
            if (totalPrice == 0) {
                System.out.println("Cannot rent the car. Car not found or not available.");
                return;
            }

            String brand = getBrand(connection, carId);

            System.out.println("\n== Rental Information ==\n");
            System.out.println("Brand: " + brand);
            System.out.println("Customer Name: " + customerName);
            System.out.println("Rental Days: " + rentalDays);
            System.out.printf("Total Price: $%.2f%n", totalPrice);
            System.out.println("Car rented Successfully");
        }

    private static void storeRentalRecord(Connection conn, int carId, int rentalDays, String customerName) {
        String rentalSql = "INSERT INTO rentals (car_id, rental_days, customer_name) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(rentalSql)) {
            preparedStatement.setInt(1, carId);
            preparedStatement.setInt(2, rentalDays);
            preparedStatement.setString(3, customerName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayCars(Connection connection) {
        String sql = "SELECT * FROM cars";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            boolean carsFound = false;
            while (rs.next()) {
                carsFound = true;
                System.out.println("Car ID: " + rs.getInt("id"));
                System.out.println("Brand: " + rs.getString("brand"));
                System.out.println("Price per day: $" + rs.getDouble("price_per_day"));
                System.out.println("Available: " + (rs.getBoolean("available") ? "Yes" : "No"));
                System.out.println();
            }
            if (!carsFound) {
                System.out.println("No cars available for rent.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getBrand(Connection connection, int carId) {
        String sql = "SELECT brand FROM cars WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, carId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("brand");
                } else {
                    System.out.println("Car not found.");
                    return "";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static double calculateBill(Connection connection, int carId, int rentalDays) {
        String sql = "SELECT price_per_day FROM cars WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, carId);
            try (ResultSet r = preparedStatement.executeQuery()) {
                if (r.next()) {
                    double pricePerDay = r.getDouble("price_per_day");
                    return pricePerDay * rentalDays;
                } else {
                    System.out.println("Car not found.");
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
