package dat.startcode.persistence;

import dat.startcode.model.entities.Customer;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.CustomerMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {
    private final static String USER = "cupcake";
    private final static String PASSWORD = "cupcake";
    private final static String URL = "jdbc:mysql://localhost:3306/startcode_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;
    private static CustomerMapper userMapper;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
        userMapper = new CustomerMapper(connectionPool);
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables
                stmt.execute("delete from user");
                // Indsæt et par brugere
                stmt.execute("insert into user (username, password, role) " +
                        "values ('user','1234','user'),('admin','1234','admin'), ('ben','1234','user')");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void login() throws DatabaseException {
        Customer expectedCustomer = new Customer("user", "1234", "user", 1, 0);
        Customer actualCustomer = userMapper.login("user", "1234");
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void invalidPasswordLogin() throws DatabaseException {
        assertThrows(DatabaseException.class, () -> userMapper.login("user", "123"));
    }

    @Test
    void invalidUserNameLogin() throws DatabaseException {
        assertThrows(DatabaseException.class, () -> userMapper.login("bob", "1234"));
    }

    @Test
    void createUser() throws DatabaseException {
        /*Customer newCustomer = userMapper.createCustomer("jill", "1234", "user", 0);
        Customer logInCustomer = userMapper.login("jill","1234");
        Customer expectedCustomer = new Customer("jill", "1234", "user", 2, 0);
        assertEquals(expectedCustomer, newCustomer);
        assertEquals(expectedCustomer, logInCustomer);

    }*/
    }
}