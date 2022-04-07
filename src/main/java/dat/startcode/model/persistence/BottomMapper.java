package dat.startcode.model.persistence;

import dat.startcode.model.entities.Bottom;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BottomMapper implements IBottomMapper
{

    private ConnectionPool connectionPool;

    public BottomMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public ArrayList<Bottom> getAllBottoms() throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        ArrayList<Bottom> bottomArrayList = new ArrayList<>();

        String sql = "SELECT * FROM bottom";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int bottomId = rs.getInt("bottom_id");
                    String bottomName = rs.getString("name");
                    int price = rs.getInt("price");
                    Bottom newBottom = new Bottom(bottomId, bottomName, price);
                    bottomArrayList.add(newBottom);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af lånere fra databasen");
        }

        return bottomArrayList;
    }

    @Override
    public Bottom getABottom(String bottomName) throws DatabaseException
    {
        return null;
    }
}
