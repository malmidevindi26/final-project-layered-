package lk.ijse.project.layered.model;

import lk.ijse.project.layered.dto.MachineDto;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MachineModel {
    public boolean saveMachine(MachineDto machineDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Machine values (?,?,?,?,?)",
                machineDto.getMachineId(),
                machineDto.getType(),
                machineDto.getStatus(),
                machineDto.getCost(),
                machineDto.getDate()
         );

    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select machine_id from Machine order by machine_id desc limit 1");
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

    public ArrayList<MachineDto> getAllMachine() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Machine");

        ArrayList<MachineDto> list = new ArrayList<>();
        while (rst.next()) {
            MachineDto machineDto = new MachineDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
            list.add(machineDto);
        }
        return list;
    }

    public boolean updateMachine(MachineDto machineDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update Machine set machine_type = ?, status = ?, maintenance_cost = ?, last_maintenance = ? where machine_id = ?",
                machineDto.getType(),
                machineDto.getStatus(),
                machineDto.getCost(),
                machineDto.getDate(),
                machineDto.getMachineId()
           );
    }

    public boolean deleteCustomer(String machineId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Machine where machine_id = ?",machineId);
    }
}
