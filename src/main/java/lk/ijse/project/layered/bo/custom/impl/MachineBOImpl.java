package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.MachineBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.MachineDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.MachineDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.MachineEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MachineBOImpl implements MachineBO {
    private final EntityDTOConverter converter = new EntityDTOConverter();
    private final MachineDAO machineDAO = DAOFactory.getInstance().getDAO(DAOType.MACHINE);
    @Override
    public List<MachineDto> getAllMachines() throws SQLException, ClassNotFoundException {
        List<MachineEntity> machineEntities = machineDAO.getAll();
        List<MachineDto> machineDtosDtos = new ArrayList<>();
        for (MachineEntity machineEntity : machineEntities) {
            machineDtosDtos.add(converter.getMachineDTO(machineEntity));

        }
        return machineDtosDtos;
    }

    @Override
    public void saveMachine(MachineDto dto) throws Exception, DuplicateException {

        Optional<MachineEntity> optionalMachine = machineDAO.findById(dto.getMachineId());
        if (optionalMachine.isPresent()) {
            throw new DuplicateException("Duplicate machine id");
        }

        MachineEntity machineEntity = converter.getMachine(dto);

        boolean save = machineDAO.save(machineEntity);
    }

    @Override
    public void updateMachine(MachineDto dto) throws SQLException, ClassNotFoundException {

        Optional<MachineEntity> optionalMachine = machineDAO.findById(dto.getMachineId());
        if(optionalMachine.isEmpty()){
            throw new NotFoundException("Machine not found");
        }

        MachineEntity machineEntity = converter.getMachine(dto);
        machineDAO.update(machineEntity);
    }

    @Override
    public boolean deleteMachine(String id) throws Exception, InUseException {
        Optional<MachineEntity> optionalMachine = machineDAO.findById(id);
        if(optionalMachine.isEmpty()){
            throw new NotFoundException("Machine not found");
        }

        try {
            boolean delete = machineDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws Exception {
//        String lastId = machineDAO.getLastId();
//        char tableChar = 'M';
//        if (lastId != null) {
//            String lastIdNumberString = lastId.substring(1);
//            int lastIdNumber = Integer.parseInt(lastIdNumberString);
//            int nextIdNumber = lastIdNumber + 1;
//            return String.format(tableChar + "%03d", nextIdNumber);
//        }
//        return tableChar + "001";
        return machineDAO.getLastId();
    }
}
