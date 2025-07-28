package lk.ijse.project.layered.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO <T> extends SuperDAO{
    List<T> getAll() throws SQLException, ClassNotFoundException;

    String getLastId() throws SQLException, ClassNotFoundException;

    boolean save(T t) throws SQLException, ClassNotFoundException;

    boolean update(T t) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    List<String> getAllIds() throws SQLException, ClassNotFoundException;

    Optional<T> findById(String id) throws SQLException, ClassNotFoundException;
}
