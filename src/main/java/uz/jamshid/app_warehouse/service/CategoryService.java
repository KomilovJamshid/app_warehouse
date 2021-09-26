package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Category;
import uz.jamshid.app_warehouse.payload.CategoryDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Result addCategory(CategoryDto categoryDto) {
        boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
        if (existsByName)
            return new Result("Category wih this name already exists", false);

        Category category = new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalParentCategory.isPresent())
                return new Result("Category doesn't exists", false);
            category.setParentCategory(optionalParentCategory.get());
        } else
            category.setParentCategory(null);
        categoryRepository.save(category);
        return new Result("Category successfully added", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Category sent successfully", true, categoryRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return new Result("Category by Id sent successfully", true, optionalCategory.orElseGet(Category::new));
    }

    public Result delete(Integer id) {
        try {
            categoryRepository.deleteById(id);
            return new Result("Category deleted successfully", true);
        } catch (Exception exception) {
            return new Result("Category not found", false);
        }
    }

    public Result edit(Integer id, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new Result("Category not found", false);
        Category currentCategory = optionalCategory.get();
        currentCategory.setName(categoryDto.getName());

        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalParentCategory.isPresent())
                return new Result("Category doesn't exists", false);
            currentCategory.setParentCategory(optionalParentCategory.get());
        } else
            currentCategory.setParentCategory(null);
        categoryRepository.save(currentCategory);
        return new Result("Category edited", true);
    }
}
