package dat.startcode.model.entities;

import java.util.Objects;

public class Customer
{
    private String email;
    private String password;
    private String role;
    private int customerID;
    private int balance;

    public Customer(String email, String password, String role, int customerID, int balance)
    {
        this.email = email;
        this.password = password;
        this.role = role;
        this.customerID = customerID;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", customerID=" + customerID +
                ", balance=" + balance +
                '}';
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerID == customer.customerID && email.equals(customer.email) && password.equals(customer.password) && role.equals(customer.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role, customerID);
    }
}
