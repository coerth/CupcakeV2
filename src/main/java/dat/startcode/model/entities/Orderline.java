package dat.startcode.model.entities;

import java.util.Objects;

public class Orderline {
    int orderline_id;
    int order_id;
    int amount;
    String t_name;
    String b_name;
    int b_price;
    int t_price;
    int total;

    @Override
    public String toString() {
        return "Orderline{" +
                "orderline_id=" + orderline_id +
                ", order_id=" + order_id +
                ", amount=" + amount +
                ", t_name='" + t_name + '\'' +
                ", b_name='" + b_name + '\'' +
                ", b_price=" + b_price +
                ", t_price=" + t_price +
                ", total=" + total +
                '}';
    }

    public Orderline(int amount, String t_name, String b_name, int b_price, int t_price, int total) {
        this.amount = amount;
        this.t_name = t_name;
        this.b_name = b_name;
        this.b_price = b_price;
        this.t_price = t_price;
        this.total = total;
    }

    public Orderline(int orderline_id, int order_id, int amount, String t_name, String b_name, int b_price, int t_price, int total) {
        this.orderline_id = orderline_id;
        this.order_id = order_id;
        this.amount = amount;
        this.t_name = t_name;
        this.b_name = b_name;
        this.b_price = b_price;
        this.t_price = t_price;
        this.total = total;
    }

    public int getOrderline_id() {
        return orderline_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orderline)) return false;
        Orderline orderline = (Orderline) o;
        return getOrderline_id() == orderline.getOrderline_id() && getOrder_id() == orderline.getOrder_id() && getAmount() == orderline.getAmount() && getB_price() == orderline.getB_price() && getT_price() == orderline.getT_price() && getTotal() == orderline.getTotal() && getT_name().equals(orderline.getT_name()) && getB_name().equals(orderline.getB_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderline_id(), getOrder_id(), getAmount(), getT_name(), getB_name(), getB_price(), getT_price(), getTotal());
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getT_name() {
        return t_name;
    }

    public String getB_name() {
        return b_name;
    }

    public int getB_price() {
        return b_price;
    }

    public int getT_price() {
        return t_price;
    }

    public int getTotal() {
        return total;
    }
}
