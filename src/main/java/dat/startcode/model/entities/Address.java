package dat.startcode.model.entities;

import java.util.Objects;

public class Address {
    int addressID;
    String street;
    int streetNumber;
    int zipcode;

    public Address(int addressID, String street, int streetNumber, int zipcode) {
        this.addressID = addressID;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getAddressID() == address.getAddressID() && getStreetNumber() == address.getStreetNumber() && getZipcode() == address.getZipcode() && getStreet().equals(address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddressID(), getStreet(), getStreetNumber(), getZipcode());
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressID=" + addressID +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", zipcode=" + zipcode +
                '}';
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
