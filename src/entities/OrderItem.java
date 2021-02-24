package entities;

public class OrderItem {
    private String brand;
    private int size;

    public OrderItem(String brand, int size) {
        this.brand = brand;
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public int getSize() {
        return size;
    }
}
