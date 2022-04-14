package dat.startcode.model.persistence;

import dat.startcode.model.entities.Customer;
import dat.startcode.model.exceptions.DatabaseException;

public interface ICustomerMapper
{
    public Customer login(String email, String kodeord) throws DatabaseException;

    Customer createCustomer(String name, String email, String password, int address_id, int role, int balance) throws DatabaseException;
}
