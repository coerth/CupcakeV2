package dat.startcode.model.persistence;

import dat.startcode.model.entities.Address;
import dat.startcode.model.entities.Customer;
import dat.startcode.model.entities.Zip;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerMapper implements ICustomerMapper {
    ConnectionPool connectionPool;

    public CustomerMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Customer login(String email, String password) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        Customer customer = null;

        String sql = "SELECT * FROM customer_view WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                System.out.println(email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String role = rs.getString("role_name");
                    int customerID = rs.getInt("customer_id");
                    int balance = rs.getInt("balance");
                    customer = new Customer(email, password, role, customerID, balance);
                } else {
                    throw new DatabaseException("Wrong username or password");
                }
            }
        } catch (SQLException ex) {
            //throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
            System.out.println(ex);
        }
        return customer;
    }

    public void updateBalance(int cost, int customerID) {
        String sql = "UPDATE customer SET balance = ? WHERE customer_id = ?";
        int balance = 0;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, cost);
                ps.setInt(2, customerID);

                int rowsAffected = ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();

                if(rowsAffected == 1)
                {
                    balance = rs.getInt(5);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    public Customer createCustomer(String name,String email, String password, String street, int streetNumber, int zipcode, String city) throws DatabaseException, SQLException {

        ZipMapper zipMapper = new ZipMapper(connectionPool);
        AddressMapper addressMapper = new AddressMapper(connectionPool);

        Zip zip = zipMapper.getSpecificZip(zipcode);
        if(zip == null) {
            zipMapper.createZip(zipcode, city);
            zip = new Zip(zipcode,city);
        }
        Address address = addressMapper.getSpecificAddress(street,streetNumber,zip.getZipcode());
        if(address == null) {
            address = addressMapper.createAddress(street,streetNumber,zip.getZipcode());

        }

        Logger.getLogger("web").log(Level.INFO, "");
        Customer customer = null;

        String sql = "insert into customer (name, email, password, address_id) values (?,?,?,?)";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))

            {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setInt(4, address.getAddressID());

                int rowsAffected = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rowsAffected == 1)
                {

                    rs.next();
                    int customerID = rs.getInt(1);
                    customer = new Customer(email, password, "kunde", customerID, 0);

                } else
                {
                    throw new DatabaseException("The user with username = " + email + " could not be inserted into the database");
                }
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
        return customer;
    }

    public int getCustomerID(String email, String password) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        Customer customer;
        String sql = "SELECT * FROM customer_view WHERE email = ? AND password = ?";
        int customerID = 0;

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    customerID = rs.getInt("customer_id");
                }
                if(customerID == 0)
                {
                    throw new DatabaseException("Kunne ikke finde customer_id");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert username into database");
        }
        return customerID;
    }

}
