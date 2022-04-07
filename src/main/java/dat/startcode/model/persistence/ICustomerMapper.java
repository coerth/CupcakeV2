package dat.startcode.model.persistence;

import dat.startcode.model.entities.Customer;
import dat.startcode.model.exceptions.DatabaseException;

public interface ICustomerMapper
{
    public Customer login(String email, String kodeord) throws DatabaseException;
    public Customer createCustomer(String username, String password, String role, int balance) throws DatabaseException;
}
