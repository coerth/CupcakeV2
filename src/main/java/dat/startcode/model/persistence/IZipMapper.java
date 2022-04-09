package dat.startcode.model.persistence;

import dat.startcode.model.entities.Zip;
import dat.startcode.model.exceptions.DatabaseException;

import java.util.ArrayList;

public interface IZipMapper {
    public ArrayList<Zip> getAllZips() throws DatabaseException;


}
