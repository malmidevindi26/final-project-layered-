package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.ServiceDAO;
import lk.ijse.project.layered.entity.ServiceEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDAOImpl implements ServiceDAO {
    @Override
    public List<ServiceEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Service");

        List<ServiceEntity> list = new ArrayList<>();
        while (rst.next()) {
            ServiceEntity serviceEntity = new ServiceEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4)

            );
            list.add(serviceEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select service_id from Service order by service_id desc limit 1");

        String tableChar = "SE";

        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
            return nextIdString;
        }
        return "SE001";
    }

    @Override
    public boolean save(ServiceEntity serviceEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Service values (?,?,?,?)",
                serviceEntity.getId(),
                serviceEntity.getName(),
                serviceEntity.getPrice(),
                serviceEntity.getDescription()
        );
    }

    @Override
    public boolean update(ServiceEntity serviceEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Service set service_name = ?, price = ?, description = ? where service_id = ?",
                serviceEntity.getName(),
                serviceEntity.getPrice(),
                serviceEntity.getDescription(),
                serviceEntity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Service where service_id = ? ", id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select service_id from Service ");
        List<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<ServiceEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Service where service_id = ?", id);
        if(rst.next()){
            return Optional.of(new ServiceEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4)

            ));
        }
        return Optional.empty();
    }
}
