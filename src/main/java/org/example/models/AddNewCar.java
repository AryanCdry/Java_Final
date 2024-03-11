package org.example.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewCar {

    public static void addNewCar(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter car details:");
            System.out.print("Brand: ");
            String brand = sc.nextLine();
            System.out.print("Price per day: ");
            double pricePerDay = sc.nextDouble();
            sc.nextLine();
            System.out.print("Available (true/false): ");
            boolean available = sc.nextBoolean();
            sc.nextLine();

            String sql = "INSERT INTO cars (brand, price_per_day, available) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, brand);
                preparedStatement.setDouble(2, pricePerDay);
                preparedStatement.setBoolean(3, available);
                preparedStatement.executeUpdate();
                System.out.println("New car added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
