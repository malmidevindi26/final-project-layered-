package lk.ijse.project.layered.model;

import lk.ijse.project.layered.dto.InventoryDto;
import lk.ijse.project.layered.dto.OrderItemDto;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryModel {
    public boolean saveInventory(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Inventory values (?,?,?,?,?,?,?)",
                  inventoryDto.getItemId(),
                  //inventoryDto.getItemId(),
                  inventoryDto.getName(),
                  inventoryDto.getManufacturerdDate(),
                  inventoryDto.getExpaireDate(),
                  inventoryDto.getStatus(),
                  inventoryDto.getQuantity(),
                  inventoryDto.getPrice()
                );
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select item_id from Inventory order by item_id desc limit 1");

        String tableString = "I";
        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextId = String.format(tableString + "%03d",nextIdNumber);
            return nextId;
        }
        return "I001";
    }

    public ArrayList<InventoryDto> getAllInventories() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Inventory");

        ArrayList<InventoryDto> list = new ArrayList<>();
        while (rst.next()) {
            InventoryDto inventoryDto = new InventoryDto(
                    rst.getString(1),
                  //  rst.getString(2),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getDouble(7)
            );
            list.add(inventoryDto);
        }
        return list;
    }

    public ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select item_id from Inventory");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    public boolean updateInventory(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update Inventory set item_name = ?, manufactured_date = ?, expaire_date = ?, status = ?, quantity = ?, price = ? where item_id = ?",
                  inventoryDto.getName(),
                  inventoryDto.getManufacturerdDate(),
                inventoryDto.getExpaireDate(),
                inventoryDto.getStatus(),
                inventoryDto.getQuantity(),
                inventoryDto.getPrice(),
                inventoryDto.getItemId()
                );
    }

    public boolean deleteInventory(String inventoryId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Inventory where item_id = ?",inventoryId);

    }


public InventoryDto findById(String selectedItemId) throws SQLException, ClassNotFoundException {
    ResultSet rst = CrudUtil.execute("select item_id, item_name, quantity, price from Inventory where item_id = ? ",
              selectedItemId
            );
    if (rst.next()){
        return new InventoryDto(
                rst.getString(1),
                rst.getString(2),
                rst.getDouble(3),
                rst.getDouble(4)
        );
    }
    return null;
}

    public boolean reduceQty(OrderItemDto orderItemDto) throws SQLException, ClassNotFoundException {
       return CrudUtil.execute("update Inventory set quantity = quantity-? where item_id =?",
               orderItemDto.getQty(),
               orderItemDto.getItemId()
       );
    }

}
