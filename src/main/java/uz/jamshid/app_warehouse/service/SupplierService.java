package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Supplier;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.SupplierRepository;

import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Result addSupplier(Supplier supplier) {
        boolean existsByPhoneNumber = supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Phone number already exists", false);

        supplierRepository.save(supplier);
        return new Result("Supplier saved", true);
    }

    public Result getSuppliers(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Suppliers sent successfully", true, supplierRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        return new Result("Supplier by id sent", true, optionalSupplier.orElseGet(Supplier::new));
    }

    public Result delete(Integer id) {
        try {
            supplierRepository.deleteById(id);
            return new Result("Supplier deleted", true);
        } catch (Exception exception) {
            return new Result("Supplier not found", false);
        }
    }

    public Result edit(Integer id, Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent())
            return new Result("Supplier not found", false);
        Supplier currentSupplier = new Supplier();

        boolean existsByPhoneNumber = supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Phone number already exists", false);
        currentSupplier.setName(supplier.getName());
        currentSupplier.setPhoneNumber(supplier.getPhoneNumber());
        supplierRepository.save(currentSupplier);
        return new Result("Supplier edited", true);
    }
}
