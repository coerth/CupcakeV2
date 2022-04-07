package dat.startcode.model.persistence;

import dat.startcode.model.entities.Customer;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerMapper implements ICustomerMapper
{
    ConnectionPool connectionPool;

    public CustomerMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public Customer login(String email, String password) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        Customer customer = null;

        String sql = "SELECT * FROM customer_view WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                System.out.println(email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    String role = rs.getString("role_name");
                    int customerID = rs.getInt("customer_id");
                    int balance = rs.getInt("balance");
                    customer = new Customer(email, password, role, customerID, balance);
                } else
                {
                    throw new DatabaseException("Wrong username or password");
                }
            }
        } catch (SQLException ex)
        {
            //throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
            System.out.println(ex);
        }
        return customer;
    }

    @Override
    public Customer createCustomer(String email, String password, String role, int balance) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        Customer customer;
        String sql = "insert into user (username, password, role, balance) values (?,?,?, ?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, role);
                ps.setInt(4, balance);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1)
                {
                    int customerID = getCustomerID(email, password);
                    customer = new Customer(email, password, role, customerID, balance);
                } else
                {
                    throw new DatabaseException("The user with username = " + email + " could not be inserted into the database");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert username into database");
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
