package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.payload.InputDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.InputService;

@RestController
@RequestMapping("/input")
public class InputController {
    @Autowired
    InputService inputService;

    @PostMapping
    public Result addInput(@RequestBody InputDto inputDto) {
        return inputService.add(inputDto);
    }

    @GetMapping
    public Result getInput(@RequestParam int page) {
        return inputService.get(page);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return inputService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return inputService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editInput(@PathVariable Integer id, @RequestBody InputDto inputDto) {
        return inputService.edit(id, inputDto);
    }
}
