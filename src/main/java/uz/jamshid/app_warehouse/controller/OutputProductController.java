package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.payload.OutputProductDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.OutputProductService;

import java.net.Inet4Address;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {
    @Autowired
    OutputProductService outputProductService;

    @PostMapping
    public Result addOutputProduct(@RequestBody OutputProductDto outputProductDto) {
        return outputProductService.add(outputProductDto);
    }

    @GetMapping
    public Result getOutputProducts(@RequestParam int page) {
        return outputProductService.get(page);
    }

    @GetMapping("/{id}")
    public Result getOutputProductById(@PathVariable Integer id) {
        return outputProductService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteOutputProduct(@PathVariable Integer id) {
        return outputProductService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editOutputProduct(@PathVariable Integer id, @RequestBody OutputProductDto outputProductDto) {
        return outputProductService.edit(id, outputProductDto);
    }
}
