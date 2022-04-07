package dat.startcode.model.entities;

import java.util.Objects;

public class Bottom
{
    private int bottomID;
    private String name;
    private int price;


    public Bottom(int bottomID, String name, int price)
    {
        this.bottomID = bottomID;
        this.name = name;
        this.price = price;
    }


    public int getBottomID() {
        return bottomID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Bottom{" +
                "bottomID=" + bottomID +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bottom)) return false;
        Bottom bottom = (Bottom) o;
        return getBottomID() == bottom.getBottomID() && getPrice() == bottom.getPrice() && Objects.equals(getName(), bottom.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBottomID(), getName(), getPrice());
    }
}
