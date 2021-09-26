package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.entity.Supplier;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @PostMapping
    public Result addSupplier(@RequestBody Supplier supplier) {
        return supplierService.addSupplier(supplier);
    }

    @GetMapping
    public Result getSuppliers(@RequestParam int page) {
        return supplierService.getSuppliers(page);
    }

    @GetMapping("/{id}")
    public Result getSupplierById(@PathVariable Integer id) {
        return supplierService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteSupplier(@PathVariable Integer id) {
        return supplierService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) {
        return supplierService.edit(id, supplier);
    }
}
