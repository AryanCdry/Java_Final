package org.example.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static org.example.models.RentCar.displayCars;

public class DeleteCar {

    public static void deleteCar(Connection connection, Scanner sc) {
        displayCars(connection);
        System.out.print("Enter Car ID to delete: ");
        int carId = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM cars WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, carId);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Car deleted successfully.");
            } else {
                System.out.println("No car found with ID " + carId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
