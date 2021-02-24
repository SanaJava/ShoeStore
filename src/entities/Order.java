package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private final int orderNr;
    private final Date date;
    private final Customer customer;
    private final List<OrderItem> orderItems;

    public Order(int orderNr, Date date, Customer customer) {
        this.orderNr = orderNr;
        this.date = date;
        this.customer = customer;
        orderItems = new ArrayList<>();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }
}
