package dat.startcode.model.persistence;

import dat.startcode.model.entities.*;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderMapper implements IOrderMapper {

    ConnectionPool connectionPool;

    public OrderMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public ArrayList<Orderline> getAllOrderlines(int orderID) {
        ArrayList<Orderline> orderlineArrayList = new ArrayList<>();

        Orderline orderline = null;
        String sql = "SELECT * FROM orderline_view_with_total WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderID);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderline_id = rs.getInt("orderline_id");
                    int order_id = rs.getInt("order_id");
                    int amount = rs.getInt("amount");
                    String t_name = rs.getString("t_name");
                    String b_name = rs.getString("b_name");
                    int t_price = rs.getInt("t_price");
                    int b_price = rs.getInt("b_price");
                    int total = rs.getInt("total");

                    orderline = new Orderline(orderline_id, order_id, amount, t_name, b_name, t_price, b_price, total);

                    orderlineArrayList.add(orderline);
                }
            }
        } catch (SQLException ex) {
            //throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
            System.out.println(ex);
        }
        return orderlineArrayList;
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        String sql = "Select * FROM `order`INNER JOIN customer USING(customer_id)";

        ArrayList<Order> orderArrayList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderID = rs.getInt("order_id");
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime localDateTime = timestamp.toLocalDateTime();
                    String customerName = rs.getString("name");
                    ArrayList<Orderline> orderlineArrayList = getAllOrderlines(orderID);
                    Order newOrder = new Order(orderID, customerName, localDateTime, orderlineArrayList);
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

        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, customerID);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderID = rs.getInt("order_id");
                    Timestamp time = rs.getTimestamp("date");
                    LocalDateTime localDateTime = time.toLocalDateTime();
                    String customerName = rs.getString("name");
                    ArrayList<Orderline> orderlineArrayList = getAllOrderlines(orderID);
                    Order newOrder = new Order(orderID, customerName, localDateTime, orderlineArrayList);
                    orderArrayList.add(newOrder);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderArrayList;
    }

    public Order getOrderWithSpecificCustomerIDAndDate(int customerID, LocalDateTime orderTime) {
        String sql = "Select * FROM `order` WHERE customer_id = ? and date = ?";

        Order order = null;

        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, customerID);
                ps.setTimestamp(2, Timestamp.valueOf(orderTime));

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int orderID = rs.getInt("order_id");
                    Timestamp time = rs.getTimestamp("date");
                    LocalDateTime localDateTime = time.toLocalDateTime();
                    String customerName = rs.getString("name");
                    ArrayList<Orderline> orderlineArrayList = getAllOrderlines(orderID);
                    order = new Order(orderID, customerName, localDateTime, orderlineArrayList);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public int getOrderID(int customerID, LocalDateTime localDateTime) throws SQLException {

        {
            try (Connection connection = connectionPool.getConnection()) {

                String sql = "SELECT order_id FROM `order` WHERE customer_id = ? and `date` = ?";
                int orderID = 2;

                try {
                    PreparedStatement ps = connection.prepareStatement(sql);

                    ps.setInt(1, customerID);
                    ps.setTimestamp(2, Timestamp.valueOf(localDateTime));
//                    int rowsAffected = ps.executeUpdate();
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        orderID = rs.getInt("order_id");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return orderID;
            }
        }
    }

    public boolean createrOrderline(CupcakeOrder cupcakeOrder, int orderID) throws SQLException {
        Orderline newOrderline;
        int orderlineID = 0;
        boolean result = false;
        try (Connection connection = connectionPool.getConnection()) {

            String sql = "INSERT into orderline (amount, topping_id, bottom_id, order_id) values  (?, ?, ? ,?)";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);

                ps.setInt(1, cupcakeOrder.getAmount());
                ps.setInt(2, cupcakeOrder.getTopping().getToppingID());
                ps.setInt(3, cupcakeOrder.getBottom().getBottomID());
                ps.setInt(4, orderID);

                int rowsAffected = ps.executeUpdate();
//                ResultSet rs = ps.executeQuery();
                if (rowsAffected == 1) {
                    result = true;
                    System.out.println("Så blev der indsat en orderline");
                } else {
                    throw new DatabaseException("Det kunne ikke lade sig gøre");
                }
            } catch (SQLException | DatabaseException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public boolean createOrder(int customerID, ArrayList<CupcakeOrder> cupcakeOrderArrayList, LocalDateTime localDateTime) throws DatabaseException {
        boolean result = false;

        int orderID = 0;

        String sql = "insert into `order` (customer_id, date) values (?,?)";


        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, customerID);
                ps.setTimestamp(2, Timestamp.valueOf(localDateTime));
                int rowsAffected = ps.executeUpdate();
      //          ResultSet rs = ps.executeQuery();



                System.out.println(rowsAffected);
    //            System.out.println(rs);

                if (rowsAffected == 1) {
                    orderID = getOrderID(customerID, localDateTime);
                for (CupcakeOrder cupcakeOrder : cupcakeOrderArrayList) {
                    result = createrOrderline(cupcakeOrder, orderID);
                    System.out.println("Så blev der indsat en orderline");
                }
                }


            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Kunne ikke indsætte ordren i databasen");
        }
        return result;
    }

}
