package dat.startcode.model.persistence;

import dat.startcode.model.entities.Address;
import dat.startcode.model.exceptions.DatabaseException;

import java.util.ArrayList;

public interface IAddressMapper {
    public ArrayList<Address> getAllAddresses() throws DatabaseException;
}
