package entities;

public class Review {
    private final Customer customer;
    private final Shoe shoe;
    private final Grade grade;

    public Review(Customer customer, Shoe shoe, Grade grade) {
        this.customer = customer;
        this.shoe = shoe;
        this.grade = grade;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public Grade getGrade() {
        return grade;
    }
}
