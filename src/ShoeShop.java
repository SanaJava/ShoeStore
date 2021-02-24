import entities.Grade;
import entities.OrderItem;
import entities.Shoe;

import java.util.List;
import java.util.Scanner;

public class ShoeShop {

    public static void main(String[] args) {
        DbConnection db = new DbConnection();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to ShoeShop. Please login to browse");

        String userName = "";
        int userId = -1;
        while (userId == -1) {
            System.out.println("Enter user name:");
            userName = sc.nextLine();
            System.out.println("Enter password:");
            String password = sc.nextLine();

            userId = db.login(userName, password);

            if (userId < 0) {
                System.out.println("Wrong credentials, please try again");
            }
        }

        System.out.println("Welcome " + userName + "!");

        try {
            int selection = 0;
            do {
                System.out.println("[1] To place an order");
                System.out.println("[2] To view current order");
                System.out.println("[3] To add review and comment for a shoe");
                System.out.println("[4] To see average grade for a shoe");
                System.out.println("[0] To leave the store");
                selection = Integer.parseInt(sc.nextLine());

                if (selection == 1) {
                    addToBasket(db, sc, userId);
                } else if (selection == 2) {
                    printCurrentOrder(db, userId);
                } else if (selection == 3) {
                    addReview(db, sc, userId);
                } else if (selection == 4) {
                    printAverageGrade(db, sc);
                }
            } while (selection > 0);

            System.out.println("Thanks for stopping by. Please come again");
        } catch (NumberFormatException e) {
            System.out.println("Nope, that's a bad value. Please restart the application");
        }
    }

    private static void printCurrentOrder(DbConnection db, int userId) {
        List<OrderItem> orderItems = db.getCurrentOrder(userId);

        System.out.println("--------------------------");
        if (orderItems.isEmpty()) {
            System.out.println("Empty basket");
        } else {
            System.out.printf("%-22s%-10s\n", "Brand", "Size");
        }

        System.out.println("--------------------------");

        for (OrderItem orderItem : orderItems) {
            System.out.printf("%-22s%-10s\n", orderItem.getBrand(), orderItem.getSize());
        }
        System.out.println("--------------------------\n");
    }

    private static void addToBasket(DbConnection db, Scanner sc, int userId) {
        List<Shoe> availableShoes = db.getAvailableShoes();
        printAllShoes(availableShoes, true);

        System.out.println("Enter the Index of the shoe you want to add to your basket.");
        int shoeId = Integer.parseInt(sc.nextLine());

        System.out.println("Enter the size you like to order");
        int shoeSize = Integer.parseInt(sc.nextLine());

        Shoe shoe = availableShoes.get(shoeId - 1);
        db.addToCart(userId, -1, shoe.getArtNr(), shoeSize);
        System.out.println(shoe.getBrand() + " has been added to your basket\n");
    }

    private static void printAllShoes(List<Shoe> shoes, boolean printInStock) {
        String format;

        System.out.println("-------------------------------------");
        if (printInStock) {
            System.out.println("Shoes in stock");
            format = "%-7s%-22s%-10s%-5s\n";
        } else {
            System.out.println("All shoes");
            format = "%-7s%-22s%-10s\n";
        }

        System.out.println("-------------------------------------");
        if (printInStock) {
            System.out.printf(format, "Index", "Brand", "Price", "Stock");
        } else {
            System.out.printf(format, "Index", "Brand", "Price");
        }
        System.out.println("-------------------------------------");

        for (int i = 0; i < shoes.size(); i++) {
            Shoe shoe = shoes.get(i);
            if (printInStock) {
                System.out.printf(format, i + 1, shoe.getBrand(), shoe.getPrice(), shoe.getStock());
            } else {
                System.out.printf(format, i + 1, shoe.getBrand(), shoe.getPrice());
            }
        }
    }

    private static void addReview(DbConnection db, Scanner sc, int userId) {
        List<Shoe> allShoes = db.getAllShoes();
        printAllShoes(allShoes, false);

        System.out.println("Enter the ID of the shoe you want to add a review on");
        int shoeId = Integer.parseInt(sc.nextLine());

        System.out.println("Select a grade from these available options:");
        List<Grade> grades = db.getAvailableGrades();

        System.out.println("--------------------------");
        System.out.printf("%-7s%-10s\n", "Index", "Grade");
        for (int i = 0; i < grades.size(); i++) {
            System.out.printf("%-7d%-10s\n", i+1, grades.get(i).getDescription());
        }
        System.out.println("--------------------------\n");

        int selectedGrade = Integer.parseInt(sc.nextLine());
        System.out.println("Please write a comment for the shoe:");
        String comment = sc.nextLine();

        db.addReview(userId, selectedGrade, allShoes.get(shoeId - 1).getArtNr(), comment);
        System.out.println("Thanks for your comment!\n");
    }

    private static void printAverageGrade(DbConnection db, Scanner sc) {
        List<Shoe> allShoes = db.getAllShoes();
        printAllShoes(allShoes, true);

        System.out.println("Enter the ID of the shoe you want to add a review on");
        int shoeId = Integer.parseInt(sc.nextLine());

        Shoe shoe = allShoes.get(shoeId - 1);

        double averageGrade = db.getAverageGrade(shoe.getArtNr());
        System.out.println("The average grade for " + shoe.getBrand() + " is: " + averageGrade + "\n");
    }
}
