package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Currency;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.CurrencyRepository;

import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    public Result add(Currency currency) {
        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName)
            return new Result("This currency already exists", false);

        currencyRepository.save(currency);
        return new Result("Currency added", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Currency sent successfully", true, currencyRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        return new Result("Currency by id sent", true, optionalCurrency.orElseGet(Currency::new));
    }

    public Result delete(Integer id) {
        try {
            currencyRepository.deleteById(id);
            return new Result("Currency deleted", true);
        } catch (Exception exception) {
            return new Result("Currency not found", false);
        }
    }

    public Result edit(Integer id, Currency currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent())
            return new Result("Currency not found", false);
        Currency currentCurrency = optionalCurrency.get();

        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName)
            return new Result("This currency already exists", false);
        currentCurrency.setName(currency.getName());
        currencyRepository.save(currentCurrency);
        return new Result("Currency edited", true);
    }
}
