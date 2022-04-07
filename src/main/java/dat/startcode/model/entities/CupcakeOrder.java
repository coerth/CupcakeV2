package dat.startcode.model.entities;

public class CupcakeOrder {

    int amount;
    Bottom bottom;
    Topping topping;
    int total;


    public CupcakeOrder(int amount, Bottom bottom, Topping topping) {
        this.amount = amount;
        this.bottom = bottom;
        this.topping = topping;
        total = (bottom.getPrice() + topping.getPrice()) * amount;

    }

    public int getTotal() {
        return total;
    }

    public Bottom getBottom() {
        return bottom;
    }

    public void setBottom(Bottom bottom) {
        this.bottom = bottom;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CupcakeOrder{" +
                "amount=" + amount +
                ", bottom=" + bottom +
                ", topping=" + topping +
                ", total=" + total +
                '}';
    }
}
