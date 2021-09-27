package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Output;
import uz.jamshid.app_warehouse.entity.OutputProduct;
import uz.jamshid.app_warehouse.entity.Product;
import uz.jamshid.app_warehouse.payload.OutputProductDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.OutputProductRepository;
import uz.jamshid.app_warehouse.repository.OutputRepository;
import uz.jamshid.app_warehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class OutputProductService {
    @Autowired
    OutputProductRepository outputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputRepository outputRepository;

    public Result add(OutputProductDto outputProductDto) {
        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Output not found", false);

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Product not found", false);

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setProduct(optionalProduct.get());
        outputProductRepository.save(outputProduct);
        return new Result("OutputProduct added", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("OutputProduct sent", true, outputProductRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        return new Result("OutputProduct by id sent", true, optionalOutputProduct.orElseGet(OutputProduct::new));
    }

    public Result delete(Integer id) {
        try {
            outputProductRepository.deleteById(id);
            return new Result("OutputProduct deleted", true);
        } catch (Exception exception) {
            return new Result("OutputProduct not found", false);
        }
    }

    public Result edit(Integer id, OutputProductDto outputProductDto) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (!optionalOutputProduct.isPresent())
            return new Result("OutputProduct not found", false);
        OutputProduct outputProduct = optionalOutputProduct.get();

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Output not found", false);

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Product not found", false);

        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setProduct(optionalProduct.get());
        outputProductRepository.save(outputProduct);
        return new Result("OutputProduct edited", true);
    }
}
