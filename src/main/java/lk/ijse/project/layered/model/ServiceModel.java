package lk.ijse.project.layered.model;

import lk.ijse.project.layered.dto.ServiceDto;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceModel {
    public boolean saveService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Service values (?,?,?,?)",
               serviceDto.getServiceId(),
               serviceDto.getName(),
               serviceDto.getPrice(),
               serviceDto.getDescription()
        );
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select service_id from Service order by service_id desc limit 1");

        char tableChar = 'S';

        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
            return nextIdString;
        }
        return "S001";
    }

    public ArrayList<ServiceDto> getAllService() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Service");

        ArrayList<ServiceDto> list = new ArrayList<>();
        while (rst.next()) {
            ServiceDto serviceDto = new ServiceDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4)

            );
            list.add(serviceDto);
        }
        return list;
    }

    public boolean updateService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update Service set service_name = ?, price = ?, description = ? where service_id = ?",
                serviceDto.getName(),
                serviceDto.getPrice(),
                serviceDto.getDescription(),
                serviceDto.getServiceId()
         );
    }

    public boolean deleteService(String serviceId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Service where service_id = ? ", serviceId);
    }

    public ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select service_id from Service ");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }
}

