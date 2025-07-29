package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.MachineDAO;
import lk.ijse.project.layered.entity.MachineEntity;

import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MachineDAOImpl implements MachineDAO {
    @Override
    public List<MachineEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Machine");

       List<MachineEntity> list = new ArrayList<>();
        while (rst.next()) {
            MachineEntity machineEntity = new MachineEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getDate(5)
            );
            list.add(machineEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select machine_id from Machine order by machine_id desc limit 1");
        char tableChar = 'I';

        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("%s%03d", tableChar, nextIdNumber);
            return nextIdString;
        }
        return "I001";
    }

    @Override
    public boolean save(MachineEntity machineEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Machine values (?,?,?,?,?)",
                machineEntity.getId(),
                machineEntity.getType(),
                machineEntity.getStatus(),
                machineEntity.getCost(),
                machineEntity.getDate()
        );

    }

    @Override
    public boolean update(MachineEntity machineEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Machine set machine_type = ?, status = ?, maintenance_cost = ?, last_maintenance = ? where machine_id = ?",
                machineEntity.getType(),
                machineEntity.getStatus(),
                machineEntity.getCost(),
                machineEntity.getDate(),
                machineEntity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Machine where machine_id = ?",id);    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select machine_id from Machine");
        List<String> list = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<MachineEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Machine where machine_id = ?", id);
        if(rst.next()){
            return Optional.of(new MachineEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getDate(5)
            ));
        }
        return Optional.empty();
    }
}
