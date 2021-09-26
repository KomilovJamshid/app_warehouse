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
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());

        Output output = new Output();
        output.setDate(outputDto.getDate());
        output.setFactureName(outputDto.getFactureName());
        output.setCode(generateCode());
        output.setWarehouse(optionalWarehouse.orElseGet(Warehouse::new));
        output.setClient(optionalClient.orElseGet(Client::new));
        output.setCurrency(optionalCurrency.orElseGet(Currency::new));
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
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());

        Output output = optionalOutput.orElseGet(Output::new);
        output.setDate(outputDto.getDate());
        output.setFactureName(outputDto.getFactureName());
        output.setWarehouse(optionalWarehouse.orElseGet(Warehouse::new));
        output.setClient(optionalClient.orElseGet(Client::new));
        output.setCurrency(optionalCurrency.orElseGet(Currency::new));
        outputRepository.save(output);
        return new Result("Output edited", true);
    }

    public String generateCode() {
        return String.valueOf(outputRepository.count() + 1);
    }
}
