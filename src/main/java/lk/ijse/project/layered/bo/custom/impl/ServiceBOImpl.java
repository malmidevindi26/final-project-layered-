package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.ServiceBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.ServiceDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.ServiceDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.ServiceEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceBOImpl implements ServiceBO {
    private final ServiceDAO serviceDAO = DAOFactory.getInstance().getDAO(DAOType.SERVICE);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public List<ServiceDto> getAllServices() throws SQLException, ClassNotFoundException {
        List<ServiceEntity> serviseEntities = serviceDAO.getAll();
        List<ServiceDto> serviceDtos = new ArrayList<>();
        for (ServiceEntity serviceEntity : serviseEntities) {
            serviceDtos.add(converter.getServiceDTO(serviceEntity));

        }
        return serviceDtos;
    }

    @Override
    public boolean saveService(ServiceDto dto) throws Exception, DuplicateException {
        Optional<ServiceEntity> optionalService = serviceDAO.findById(dto.getServiceId());
        if (optionalService.isPresent()) {
            throw new DuplicateException("Duplicate service id");
        }

        ServiceEntity serviceEntity = converter.getService(dto);

        boolean save = serviceDAO.save(serviceEntity);
        return save;
    }

    @Override
    public boolean updateService(ServiceDto dto) throws SQLException, ClassNotFoundException {
        Optional<ServiceEntity> optionalService = serviceDAO.findById(dto.getServiceId());
        if(optionalService.isEmpty()){
            throw new NotFoundException("ServiceId not found");
        }

        ServiceEntity serviceEntity = converter.getService(dto);
        return serviceDAO.update(serviceEntity);
    }

    @Override
    public boolean deleteService(String id) throws Exception, InUseException {
        Optional<ServiceEntity> optionalService = serviceDAO.findById(id);
        if(optionalService.isEmpty()){
            throw new NotFoundException("ServiceId not found");
        }

        try {
            boolean delete = serviceDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = serviceDAO.getLastId();
        String tableChar = "SE";
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }
}
