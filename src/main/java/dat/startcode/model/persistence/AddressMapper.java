package dat.startcode.model.persistence;

import dat.startcode.model.entities.Address;
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
            throw new DatabaseException(ex, "Fejl under indlæsning af postnumre fra databasen");
        }

        return addressArrayList;
    }


    public Address getSpecificAddress(String street, int streetNumber, int zipcode) throws DatabaseException, SQLException {
        Logger.getLogger("web").log(Level.INFO, "");
        Address address = null;
        String sql = "SELECT * FROM address WHERE street = ? AND street_number= ? AND zipcode = ?";


        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1,street);
                ps.setInt(2,streetNumber);
                ps.setInt(3, zipcode);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int addressID= rs.getInt("address_id");
                    String returnedStreet = rs.getString("street");
                    int returnedStreetNumber = rs.getInt("street_number");
                    int returnedZip = rs.getInt("zipcode");
                    address = new Address(addressID,returnedStreet,returnedStreetNumber,returnedZip);


                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            return address;
        }
    }


    public Address createAddress(String street, int streetNumber, int zipcode) throws DatabaseException, SQLException {
        boolean result = false;

        Address address = null;

        String sql = "insert into `address` (street, street_number, zipcode) values (?,?, ?)";


        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, street);
                ps.setInt(2, streetNumber);
                ps.setInt(3,zipcode);

                int rowsAffected = ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();



                System.out.println(rowsAffected);
                //            System.out.println(rs);

                if (rowsAffected == 1) {
                    rs.next();
                    int addressID = rs.getInt(1);

                        System.out.println("Så blev der indsat en adresse");
                        address = new Address(addressID,street,streetNumber,zipcode);
                    }
                } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return address;
    }



}
