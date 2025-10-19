Name: Parag Harish Gulve
Roll No: CO2178

Q.Develop a Java program that implements a simple hotel room booking system using two dimensional arrays. The system allows users to: View available and booked rooms, Book a room by selecting a floor and room number and exit the system when finished

import java.util.Scanner;

public class hotel_management {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final int FLOORS = 3;
        final int ROOMS_PER_FLOOR = 5;

        // 2D array to store room status: null = empty, otherwise booked by name
        String[][] rooms = new String[FLOORS][ROOMS_PER_FLOOR];

        int choice;

        do {
            System.out.println("\n***Hotel Management Menu***");
            System.out.println("1. Show Empty Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View All Rooms");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    showEmptyRooms(rooms);
                    break;

                case 2:
                    bookRoom(rooms, sc);
                    break;

                case 3:
                    viewAllRooms(rooms);
                    break;

                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1 to 4.");
            }
        } while (choice != 4);

        sc.close();
    }

    // Show empty rooms
    public static void showEmptyRooms(String[][] rooms) {
        System.out.println("\nEmpty Rooms:");
        boolean anyEmpty = false;
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] == null) {
                    System.out.println("Floor " + (i + 1) + " Room " + (j + 1) + " is EMPTY");
                    anyEmpty = true;
                }
            }
        }
        if (!anyEmpty) {
            System.out.println("All rooms are booked.");
        }
    }

    // Book a room
    public static void bookRoom(String[][] rooms, Scanner sc) {
        System.out.print("\nEnter floor number (1-" + rooms.length + "): ");
        int floor = sc.nextInt();
        System.out.print("Enter room number (1-" + rooms[0].length + "): ");
        int room = sc.nextInt();

        if (floor < 1 || floor > rooms.length || room < 1 || room > rooms[0].length) {
            System.out.println("Invalid floor or room number.");
            return;
        }

        if (rooms[floor - 1][room - 1] == null) {
            sc.nextLine();  // consume leftover newline
            System.out.print("Enter name for booking: ");
            String name = sc.nextLine();
            rooms[floor - 1][room - 1] = name;
            System.out.println("Room booked successfully for " + name + ".");
        } else {
            System.out.println("Room already booked by " + rooms[floor - 1][room - 1] + ".");
        }
    }

    // View all rooms and their status
    public static void viewAllRooms(String[][] rooms) {
        System.out.println("\nAll Rooms Status:");
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                String status = (rooms[i][j] == null) ? "EMPTY" : "Booked by " + rooms[i][j];
                System.out.println("Floor " + (i + 1) + " Room " + (j + 1) + ": " + status);
            }
        }
    }
}

O/P:

***Hotel Management Menu***
1. Show Empty Rooms
2. Book a Room
3. View All Rooms
4. Exit
Enter your choice (1-4): 






