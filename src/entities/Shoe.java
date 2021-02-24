package entities;

import java.util.ArrayList;
import java.util.List;

public class Shoe {

    private final String artNr;
    private final String brand;
    private final double price;
    private final int stock;
    private final List<Category> categories = new ArrayList<>();

    public Shoe(String artNr, String brand, double price, int stock) {
        this.artNr = artNr;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public String getArtNr() {
        return artNr;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}
