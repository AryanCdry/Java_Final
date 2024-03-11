package org.example;

import org.example.models.AddNewCar;
import org.example.models.DeleteCar;
import org.example.models.RentCar;
import org.example.models.ReturnCar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String carTableSql = "CREATE TABLE IF NOT EXISTS cars (" +
                "id INTEGER PRIMARY KEY," +
                "brand TEXT ," +
                "price_per_day INTEGER," +
                "available BOOLEAN )";

        String customerTableSql = "CREATE TABLE IF NOT EXISTS customers (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL)";

        String rentalTableSql = "CREATE TABLE IF NOT EXISTS rentals (" +
                "id INTEGER PRIMARY KEY," +
                "car_id INTEGER ," +  "rental_days INTEGER," + "customer_name TEXT)";


        String DB_URL = "jdbc:sqlite:Car_rental.db";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {

            Statement statement = connection.createStatement();
            statement.execute(carTableSql);
            statement.execute(customerTableSql);
            statement.execute(rentalTableSql);
            menu(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void menu(Connection connection) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Delete a Car");
            System.out.println("4. Add a New Car");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                RentCar rentCar = new RentCar();
                rentCar.rentCar(connection, sc);
            } else if (choice == 2) {
                ReturnCar returnCar = new ReturnCar();
                returnCar.returnCar(connection, sc);
            } else if (choice == 3) {
                DeleteCar deleteCar = new DeleteCar();
                deleteCar.deleteCar(connection, sc);
            } else if (choice == 4) {
                AddNewCar addNewCar = new AddNewCar();
                addNewCar.addNewCar(connection, sc);
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("Thank you for using the Car Rental System!");
    }

}
