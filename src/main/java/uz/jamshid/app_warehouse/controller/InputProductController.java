package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.payload.InputProductDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.InputProductService;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {
    @Autowired
    InputProductService inputProductService;

    @PostMapping
    public Result addInputProduct(@RequestBody InputProductDto inputProductDto) {
        return inputProductService.add(inputProductDto);
    }

    @GetMapping
    public Result getInputProducts(@RequestParam int page) {
        return inputProductService.get(page);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return inputProductService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteInputProduct(@PathVariable Integer id) {
        return inputProductService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editInputProduct(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto) {
        return inputProductService.edit(id, inputProductDto);
    }
}
