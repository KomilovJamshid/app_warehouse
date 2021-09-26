package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Currency;
import uz.jamshid.app_warehouse.entity.Input;
import uz.jamshid.app_warehouse.entity.Supplier;
import uz.jamshid.app_warehouse.entity.Warehouse;
import uz.jamshid.app_warehouse.payload.InputDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.CurrencyRepository;
import uz.jamshid.app_warehouse.repository.InputRepository;
import uz.jamshid.app_warehouse.repository.SupplierRepository;
import uz.jamshid.app_warehouse.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class InputService {
    @Autowired
    InputRepository inputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    public Result add(InputDto inputDto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Warehouse not found", false);

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new Result("Supplier not found", false);

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Currency not found", false);

        Input input = new Input();
        input.setDate(inputDto.getDate());
        input.setFactureName(inputDto.getFactureName());
        input.setCode(generateCode());
        input.setWarehouse(optionalWarehouse.get());
        input.setSupplier(optionalSupplier.get());
        input.setCurrency(optionalCurrency.get());
        inputRepository.save(input);
        return new Result("Input saved", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Input sent succesfully", true, inputRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        return new Result("Input by id sent", true, optionalInput.orElseGet(Input::new));
    }

    public Result delete(Integer id) {
        try {
            inputRepository.deleteById(id);
            return new Result("Input deleted", true);
        } catch (Exception exception) {
            return new Result("Input not found", false);
        }
    }

    public Result edit(Integer id, InputDto inputDto) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent())
            return new Result("Input not found", false);
        Input input = optionalInput.get();

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Warehouse not found", false);

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new Result("Supplier not found", false);

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Currency not found", false);

        input.setDate(inputDto.getDate());
        input.setFactureName(inputDto.getFactureName());
        input.setWarehouse(optionalWarehouse.get());
        input.setSupplier(optionalSupplier.get());
        input.setCurrency(optionalCurrency.get());
        inputRepository.save(input);
        return new Result("Input edited", true);
    }

    public String generateCode() {
        return String.valueOf(inputRepository.count() + 1);
    }
}
