package dat.startcode.model.persistence;

import dat.startcode.model.entities.*;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZipMapper implements IZipMapper {

    ConnectionPool connectionPool = new ConnectionPool();

    public ZipMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Zip getSpecificZip(int zipcode) throws DatabaseException, SQLException {
        Logger.getLogger("web").log(Level.INFO, "");
        Zip zip = null;
        String sql = "SELECT * FROM zip WHERE zipcode = ?";


        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1,zipcode);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int newZipcode = rs.getInt("zipcode");
                    String city = rs.getString("city");
                    zip = new Zip(newZipcode, city);


                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            return zip;
        }
    }


    public int createZip(int zipcode, String city) throws DatabaseException {

        int newZip = 0;

        String sql = "insert into `zip` (zipcode, city) values (?,?)";


        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, zipcode);
                ps.setString(2, city);
                int rowsAffected = ps.executeUpdate();




                System.out.println(rowsAffected);
                //            System.out.println(rs);

                if (rowsAffected == 1) {

                    newZip = zipcode;


                    System.out.println("Så blev der indsat en newZip " + newZip);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
            return newZip;
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

