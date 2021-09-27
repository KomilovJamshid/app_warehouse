package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Client;
import uz.jamshid.app_warehouse.entity.Currency;
import uz.jamshid.app_warehouse.entity.Output;
import uz.jamshid.app_warehouse.entity.Warehouse;
import uz.jamshid.app_warehouse.payload.OutputDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.ClientRepository;
import uz.jamshid.app_warehouse.repository.CurrencyRepository;
import uz.jamshid.app_warehouse.repository.OutputRepository;
import uz.jamshid.app_warehouse.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class OutputService {
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    public Result add(OutputDto outputDto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Warehouse not found", false);

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent())
            return new Result("Client not found", false);

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Currency not found", false);

        Output output = new Output();
        output.setDate(outputDto.getDate());
        output.setFactureName(outputDto.getFactureName());
        output.setCode(generateCode());
        output.setWarehouse(optionalWarehouse.get());
        output.setClient(optionalClient.get());
        output.setCurrency(optionalCurrency.get());
        outputRepository.save(output);
        return new Result("Output saved", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Outputs sent successfully", true, outputRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        return new Result("Output by id sent", true, optionalOutput.orElseGet(Output::new));
    }

    public Result delete(Integer id) {
        try {
            outputRepository.deleteById(id);
            return new Result("Output deleted", true);
        } catch (Exception exception) {
            return new Result("Output not found", false);
        }
    }

    public Result edit(Integer id, OutputDto outputDto) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent())
            return new Result("Output nor found", false);
        Output output = optionalOutput.get();

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Warehouse not found", false);

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent())
            return new Result("Client not found", false);

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Currency not found", false);

        output.setDate(outputDto.getDate());
        output.setFactureName(outputDto.getFactureName());
        output.setWarehouse(optionalWarehouse.get());
        output.setClient(optionalClient.get());
        output.setCurrency(optionalCurrency.get());
        outputRepository.save(output);
        return new Result("Output edited", true);
    }

    public String generateCode() {
        return String.valueOf(outputRepository.count() + 1);
    }
}
