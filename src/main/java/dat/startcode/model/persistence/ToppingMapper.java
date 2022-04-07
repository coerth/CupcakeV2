package dat.startcode.model.persistence;

import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToppingMapper implements IToppingMapper{

    ConnectionPool connectionPool;


    public ToppingMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    public ArrayList<Topping> getAllToppings () throws DatabaseException {


        ArrayList<Topping> toppingArrayList = new ArrayList<>();

        Topping topping = null;

    try(
        Connection connection = connectionPool.getConnection())

        {
            String sql = "select * from topping";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int topping_id = rs.getInt("topping_id");
                    String name = rs.getString("name");
                    int price = rs.getInt("price");


                    toppingArrayList.add(new Topping(topping_id, name, price));
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
        } catch(
        SQLException throwables)

        {
            throwables.printStackTrace();
        }
        return toppingArrayList;
    }
}

