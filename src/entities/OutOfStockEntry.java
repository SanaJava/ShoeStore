package entities;

import java.util.Date;

public class OutOfStockEntry {
    private final Date date;
    private final Shoe shoe;

    public OutOfStockEntry(Date date, Shoe shoe) {
        this.date = date;
        this.shoe = shoe;
    }

    public Date getDate() {
        return date;
    }

    public Shoe getShoe() {
        return shoe;
    }
}
