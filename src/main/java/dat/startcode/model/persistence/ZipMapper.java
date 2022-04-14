package dat.startcode.model.persistence;

import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Customer;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.entities.Zip;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZipMapper implements IZipMapper {

    ConnectionPool connectionPool = new ConnectionPool();

    public ZipMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public int getSpecificZip(int zipcode, String city) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        Zip zip;
        String sql = "SELECT * FROM zip WHERE zipcode = ?";

        int currentZipcode=0;

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, zipcode);
                ps.setString(2, city);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    currentZipcode = rs.getInt("zipcode");
                }
                if(currentZipcode == 0)
                {
                    throw new DatabaseException("Kunne ikke finde postnr");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert into database");
        }
        return currentZipcode;
    }


    @Override
    public ArrayList<Zip> getAllZips () throws DatabaseException {

        Logger.getLogger("web").log(Level.INFO, "");
        ArrayList<Zip> zipArrayList = new ArrayList<>();

        String sql = "SELECT * FROM zip";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int zipcode = rs.getInt("zipcode");
                    String city = rs.getString("city");
                    Zip newZip = new Zip(zipcode, city);
                    zipArrayList.add(newZip);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af zipkoder fra databasen");
        }

        return zipArrayList;
    }


}

