package dat.startcode.model.persistence;

import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Customer;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.Orderline;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class OrderMapper implements IOrderMapper{

    ConnectionPool connectionPool;

    public OrderMapper (ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public ArrayList<Orderline> getAllOrderlines(int orderID) {
        ArrayList<Orderline> orderlineArrayList = new ArrayList<>();

        Orderline orderline = null;
        String sql = "SELECT * FROM orderline_view_with_total WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderID);

                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int orderline_id = rs.getInt("orderline_id");
                    int order_id = rs.getInt("order_id");
                    int amount = rs.getInt("amount");
                    String t_name = rs.getString("t_name");
                    String b_name = rs.getString("b_name");
                    int t_price = rs.getInt("t_price");
                    int b_price = rs.getInt("b_price");
                    int total = rs.getInt("total");

                    orderline = new Orderline(orderline_id,order_id,amount,t_name,b_name,t_price,b_price,total);

                    orderlineArrayList.add(orderline);
                }
            }
        } catch (SQLException ex)
        {
            //throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
            System.out.println(ex);
        } return orderlineArrayList;
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        String sql = "Select * FROM `order`INNER JOIN customer USING(customer_id)";

        ArrayList<Order> orderArrayList = new ArrayList<>();

        try (Connection  connection= connectionPool.getConnection() ) {

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int orderID = rs.getInt("order_id");
                    Timestamp timestamp = rs.getTimestamp("date");
                    String customerName = rs.getString("name");
                    ArrayList<Orderline> orderlineArrayList = getAllOrderlines(orderID);
                    Order newOrder = new Order(orderID, customerName, timestamp, orderlineArrayList);
                    orderArrayList.add(newOrder);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderArrayList;
    }

    public ArrayList<Order> getOrdersWithSpecificCustomerID(int customerID) {
        String sql = "Select * FROM `order`INNER JOIN customer USING(customer_id) WHERE customer_id = ?";

        ArrayList<Order> orderArrayList = new ArrayList<>();

        try (Connection  connection= connectionPool.getConnection() ) {

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, customerID);

                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int orderID = rs.getInt("order_id");
                    Timestamp timestamp = rs.getTimestamp("date");
                    String customerName = rs.getString("name");
                    ArrayList<Orderline> orderlineArrayList = getAllOrderlines(orderID);
                    Order newOrder = new Order(orderID, customerName, timestamp, orderlineArrayList);
                    orderArrayList.add(newOrder);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderArrayList;
    }
}
