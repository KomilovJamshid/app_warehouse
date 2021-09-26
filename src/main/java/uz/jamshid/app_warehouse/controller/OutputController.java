package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.payload.OutputDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.OutputService;

@RestController
@RequestMapping("/output")
public class OutputController {
    @Autowired
    OutputService outputService;

    @PostMapping
    public Result addOutput(@RequestBody OutputDto outputDto) {
        return outputService.add(outputDto);
    }

    @GetMapping
    public Result getOutputs(@RequestParam int page) {
        return outputService.get(page);
    }

    @GetMapping("/{id}")
    public Result getOutputById(@PathVariable Integer id) {
        return outputService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteOutput(@PathVariable Integer id) {
        return outputService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editOutput(@PathVariable Integer id, @RequestBody OutputDto outputDto) {
        return outputService.edit(id, outputDto);
    }
}
