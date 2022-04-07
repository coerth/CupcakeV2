package dat.startcode.model.entities;

import java.util.Objects;

public class Topping {

    private int toppingID;
    private String name;
    private int price;

    public Topping(int toppingID, String name, int price) {
        this.toppingID = toppingID;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topping)) return false;
        Topping topping = (Topping) o;
        return getToppingID() == topping.getToppingID() && getPrice() == topping.getPrice() && getName().equals(topping.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToppingID(), getName(), getPrice());
    }

    public int getToppingID() {
        return toppingID;
    }

    public void setToppingID(int toppingID) {
        this.toppingID = toppingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Topping{" +
                "topping_id=" + toppingID +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
