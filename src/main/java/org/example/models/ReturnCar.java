package org.example.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReturnCar {

    public static void returnCar(Connection connection, Scanner sc) {
        System.out.println("\n== Return a Car ==\n");
        System.out.print("Enter the car ID you want to return: ");
        int carId = sc.nextInt();
        sc.nextLine();

        String customerSql = "SELECT name FROM customers WHERE id = (SELECT id FROM cars WHERE id = ?)";
        try (PreparedStatement customerpreparedStatement = connection.prepareStatement(customerSql)) {
            customerpreparedStatement.setInt(1, carId);
            try (ResultSet customerRs = customerpreparedStatement.executeQuery()) {
                if (customerRs.next()) {
                    String customerName = customerRs.getString("name");
                    System.out.println("Customer Name: " + customerName);
                } else {
                    System.out.println("No customer information found for the car.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String carSql = "SELECT brand, price_per_day, available FROM cars WHERE id = ?";
        try (PreparedStatement carpreparedstatement = connection.prepareStatement(carSql)) {
            carpreparedstatement.setInt(1, carId);
            try (ResultSet carRs = carpreparedstatement.executeQuery()) {
                if (carRs.next()) {
                    String carBrand = carRs.getString("brand");
                    double pricePerDay = carRs.getDouble("price_per_day");
                    boolean available = carRs.getBoolean("available");

                    System.out.println("Car Brand: " + carBrand);
                    System.out.println("Price per day: $" + pricePerDay);
                    System.out.println("Available: " + (available ? "Yes" : "No"));
                } else {
                    System.out.println("Car not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
