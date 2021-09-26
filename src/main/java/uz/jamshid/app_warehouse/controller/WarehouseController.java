package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.entity.Warehouse;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @PostMapping
    public Result addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.add(warehouse);
    }

    @GetMapping
    public Result getWarehouse(@RequestParam int page) {
        return warehouseService.get(page);
    }

    @GetMapping("/{id}")
    public Result getWarehouseById(@PathVariable Integer id) {
        return warehouseService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteWarehouse(@PathVariable Integer id) {
        return warehouseService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editWarehouse(@PathVariable Integer id, @RequestBody Warehouse warehouse) {
        return warehouseService.edit(id, warehouse);
    }
}
