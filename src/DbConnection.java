import entities.Grade;
import entities.OrderItem;
import entities.Shoe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ShoeShop?serverTimezone=UTC&useSSL=false";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Java1234";

    public List<Shoe> getAvailableShoes() {
        List<Shoe> shoes = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Shoes");

            addShoesToList(rs, true, shoes);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return shoes;
    }

    public List<Shoe> getAllShoes() {
        List<Shoe> shoes = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Shoes");

            addShoesToList(rs, false, shoes);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return shoes;
    }

    public List<Grade> getAvailableGrades() {
        List<Grade> grades = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Name FROM Grades");

            while (rs.next()) {
                grades.add(new Grade(rs.getString("Name")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return grades;
    }

    private void addShoesToList(ResultSet rs, boolean inStockOnly, List<Shoe> shoeList) throws SQLException {
        while (rs.next()) {
            String artNr = rs.getString("ArtNr");
            String brand = rs.getString("Brand");
            double price = rs.getDouble("Price");
            int stock = rs.getInt("Stock");
            if (inStockOnly) {
                if (stock > 0) {
                    shoeList.add(new Shoe(artNr, brand, price, stock));
                }
            } else {
                shoeList.add(new Shoe(artNr, brand, price, stock));
            }

        }
    }

    public void addToCart(int customerId, int orderNr, String articleNr, int size) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            CallableStatement statement = con.prepareCall("CALL AddToCart(?, ?, ?, ?)");

            statement.setInt(1, customerId);
            if (orderNr >= 0) {
                statement.setInt(2, orderNr);
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setString(3, articleNr);
            statement.setInt(4, size);
            statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<OrderItem> getCurrentOrder(int userId) {
        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement statement = con.prepareStatement("SELECT Shoes.Brand, Size FROM ShoeShop.Shoes INNER JOIN ShoeShop.OrderItems ON Shoes.ArtNr = OrderItems.ArtNr INNER JOIN ShoeShop.Orders ON OrderItems.OrderNr = Orders.OrderNr WHERE Orders.Customer_ID = ?");
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orderItems.add(new OrderItem(rs.getString("Brand"), rs.getInt("Size")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return orderItems;
    }

    public int login(String customerName, String password) {
        int id = -1;
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement statement = con.prepareStatement("SELECT Customer_ID FROM Customers WHERE NAME = ? AND Password = ?");
            statement.setString(1, customerName);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("Customer_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public void addReview(int customerId, int grade, String articleNr, String comment) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            CallableStatement statement = con.prepareCall("CALL AddReview(?, ?, ?, ?)");

            statement.setInt(1, customerId);
            statement.setInt(2, grade);
            statement.setString(3, articleNr);
            statement.setString(4, comment);
            statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public double getAverageGrade(String articleNr) {
        double average = 0.0;
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement statement = con.prepareStatement("SELECT ShoeShop.AVERAGE_GRADE(ArtNr) AS Average FROM ShoeShop.Shoes WHERE ArtNr = ?");
            statement.setString(1, articleNr);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                average = rs.getDouble("Average");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return average;
    }
}
