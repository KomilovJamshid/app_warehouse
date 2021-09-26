package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.entity.Measurement;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.MeasurementService;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {
    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurement(@RequestBody Measurement measurement) {
        return measurementService.add(measurement);
    }

    @GetMapping
    public Result getMeasurements(@RequestParam int page) {
        return measurementService.get(page);
    }

    @GetMapping("/{id}")
    public Result getMeasurementById(@PathVariable Integer id) {
        return measurementService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteMeasurement(@PathVariable Integer id) {
        return measurementService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editMeasurement(@PathVariable Integer id, @RequestBody Measurement measurement) {
        return measurementService.edit(id, measurement);
    }
}
