package entities;

public class Customer {
    private final String name;
    private final String city;
    private final String password;

    public Customer(String name, String city, String password) {
        this.name = name;
        this.city = city;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() {
        return password;
    }
}
