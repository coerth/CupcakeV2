package dat.startcode.model.persistence;

import dat.startcode.model.entities.Bottom;
import dat.startcode.model.exceptions.DatabaseException;

import java.util.ArrayList;

public interface IBottomMapper
{
    public ArrayList<Bottom> getAllBottoms() throws DatabaseException;
    public Bottom getABottom(String bottomName) throws DatabaseException;
}
