package entities;

public class Comment {
    private final String comment;
    private final Customer customer;

    public Comment(String comment, Customer customer) {
        this.comment = comment;
        this.customer = customer;
    }

    public String getComment() {
        return comment;
    }

    public Customer getCustomer() {
        return customer;
    }
}
