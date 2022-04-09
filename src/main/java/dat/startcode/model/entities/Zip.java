package dat.startcode.model.entities;

import java.util.Objects;

public class Zip {
    int zipcode;
    String city;

    public Zip(int zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Zip{" +
                "zip=" + zipcode +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zip)) return false;
        Zip zip1 = (Zip) o;
        return getZipcode() == zip1.getZipcode() && getCity().equals(zip1.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZipcode(), getCity());
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
