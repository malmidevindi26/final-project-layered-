package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.MachineDto;

import java.sql.SQLException;
import java.util.List;

public interface MachineBO extends SuperBO {
    List<MachineDto> getAllMachines() throws SQLException, ClassNotFoundException;

    void saveMachine(MachineDto dto) throws Exception, DuplicateException;

    void updateMachine(MachineDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteMachine(String id) throws Exception, InUseException;

    String getNextId() throws Exception;
}
