package dat.startcode.model.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Order {

    int order_id;
    String customerName;
    Timestamp timestamp;
    ArrayList<Orderline> orderlineArrayList;

    public Order(int order_id, String customerName, Timestamp timestamp, ArrayList<Orderline> orderlineArrayList)
    {
        this.order_id = order_id;
        this.timestamp = timestamp;
        this.orderlineArrayList = orderlineArrayList;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getOrder_id() {
        return order_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public ArrayList<Orderline> getOrderlineArrayList() {
        return orderlineArrayList;
    }
}
