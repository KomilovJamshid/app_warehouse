package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Warehouse;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    public Result add(Warehouse warehouse) {
        boolean existsByName = warehouseRepository.existsByName(warehouse.getName());
        if (existsByName)
            return new Result("Warehouse with this name already exists", false);

        warehouseRepository.save(warehouse);
        return new Result("Warehouse successfully saved", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Warehouses sent successfully", true, warehouseRepository.findAll(pageable));
    }

    public Result getById(int id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        return new Result("Warehouse sent by id", true, optionalWarehouse.orElseGet(Warehouse::new));
    }

    public Result delete(int id) {
        try {
            warehouseRepository.deleteById(id);
            return new Result("Warehouse deleted successfully", true);
        } catch (Exception exception) {
            return new Result("Warehouse not found", false);
        }
    }

    public Result edit(Integer id, Warehouse warehouse) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (!optionalWarehouse.isPresent())
            return new Result("Warehouse not found", false);
        Warehouse currentWarehouse = optionalWarehouse.get();

        boolean existsByName = warehouseRepository.existsByName(warehouse.getName());
        if (existsByName)
            return new Result("Warehouse with this name already exists", false);
        currentWarehouse.setName(warehouse.getName());
        warehouseRepository.save(currentWarehouse);
        return new Result("Warehouse edited successfully", true);
    }
}
