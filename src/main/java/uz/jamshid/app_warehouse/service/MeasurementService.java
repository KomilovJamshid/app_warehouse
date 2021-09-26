package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Measurement;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.MeasurementRepository;

import java.util.Optional;

@Service
public class MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;

    public Result add(Measurement measurement) {
        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName)
            return new Result("Measuring unit already exists", false);

        measurementRepository.save(measurement);
        return new Result("Measuring unit successfully saved", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Measurement sent successfully", true, measurementRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        return new Result("Measurement by Id sent successfully", true, optionalMeasurement.orElseGet(Measurement::new));
    }

    public Result delete(Integer id) {
        try {
            measurementRepository.deleteById(id);
            return new Result("Measurement deleted successfully", true);
        } catch (Exception exception) {
            return new Result("Measurement not found", false);
        }
    }

    public Result edit(Integer id, Measurement measurement) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (!optionalMeasurement.isPresent())
            return new Result("Measurement not found", false);
        Measurement currentMeasurement = optionalMeasurement.get();

        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName)
            return new Result("Measuring unit already exists", false);
        currentMeasurement.setName(measurement.getName());
        measurementRepository.save(measurement);
        return new Result("Measurement successfully edited", true);
    }
}
