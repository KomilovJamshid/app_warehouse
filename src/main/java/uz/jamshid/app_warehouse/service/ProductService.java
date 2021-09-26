package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Attachment;
import uz.jamshid.app_warehouse.entity.Category;
import uz.jamshid.app_warehouse.entity.Measurement;
import uz.jamshid.app_warehouse.entity.Product;
import uz.jamshid.app_warehouse.payload.ProductDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.AttachmentRepository;
import uz.jamshid.app_warehouse.repository.CategoryRepository;
import uz.jamshid.app_warehouse.repository.MeasurementRepository;
import uz.jamshid.app_warehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    MeasurementRepository measurementRepository;

    public Result add(ProductDto productDto) {
        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (existsByNameAndCategoryId)
            return new Result("Product name under category already exists", false);

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Category doesn't exists", false);

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent())
            return new Result("Photo doesn't exists", false);

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Measurement unit doesn't exists", false);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCode(generateCode());
        product.setCategory(optionalCategory.get());
        product.setPhoto(optionalAttachment.get());
        product.setMeasurement(optionalMeasurement.get());
        productRepository.save(product);
        return new Result("Product successfully saved", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Products sent successfully", true, productRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return new Result("Product by Id sent successfully", true, optionalProduct.orElseGet(null));
    }

    public Result delete(Integer id) {
        try {
            productRepository.deleteById(id);
            return new Result("Product deleted", true);
        } catch (Exception exception) {
            return new Result("Product not found", false);
        }
    }

    public Result edit(Integer id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return new Result("Product not found", false);
        Product product = optionalProduct.get();

        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (existsByNameAndCategoryId)
            return new Result("Product name under category already exists", false);

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Category doesn't exists", false);

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent())
            return new Result("Photo doesn't exists", false);

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Measurement unit doesn't exists", false);

        product.setName(productDto.getName());
        product.setCategory(optionalCategory.get());
        product.setPhoto(optionalAttachment.get());
        product.setMeasurement(optionalMeasurement.get());
        productRepository.save(product);
        return new Result("Product edited", true);
    }

    public String generateCode() {
        return String.valueOf(productRepository.count() + 1);
    }
}
