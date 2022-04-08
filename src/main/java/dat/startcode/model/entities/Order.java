package dat.startcode.model.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Order {

    int order_id;
    String customerName;
    LocalDateTime localDateTime;
    ArrayList<Orderline> orderlineArrayList;

    public Order(int order_id, String customerName, LocalDateTime localDateTime, ArrayList<Orderline> orderlineArrayList)
    {
        this.order_id = order_id;
        this.localDateTime = localDateTime;
        this.orderlineArrayList = orderlineArrayList;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getOrder_id() {
        return order_id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public ArrayList<Orderline> getOrderlineArrayList() {
        return orderlineArrayList;
    }
}
