package dat.startcode.model.persistence;

import dat.startcode.model.entities.Address;
import dat.startcode.model.entities.Zip;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressMapper implements IAddressMapper{

    ConnectionPool connectionPool;

    public AddressMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public ArrayList<Address> getAllAddresses() throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        ArrayList<Address> addressArrayList = new ArrayList<>();

        String sql = "SELECT * FROM address";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int addressID = rs.getInt("address_id");
                    String street = rs.getString("street");
                    int steetNumber = rs.getInt("street_number");
                    int zipcode = rs.getInt("zipcode");
                    Address newAddress = new Address(addressID, street, steetNumber, zipcode);
                    addressArrayList.add(newAddress);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af zipkoder fra databasen");
        }

        return addressArrayList;
    }
}
