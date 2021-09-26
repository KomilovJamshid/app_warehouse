package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.entity.Currency;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.CurrencyService;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @PostMapping
    public Result addCurrency(@RequestBody Currency currency) {
        return currencyService.add(currency);
    }

    @GetMapping
    public Result getCurrency(@RequestParam int page) {
        return currencyService.get(page);
    }

    @GetMapping("/{id}")
    public Result getCurrencyById(@PathVariable Integer id) {
        return currencyService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteCurrency(@PathVariable Integer id) {
        return currencyService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editCurrency(@PathVariable Integer id, @RequestBody Currency currency) {
        return currencyService.edit(id, currency);
    }
}
