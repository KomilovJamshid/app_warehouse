package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.payload.CategoryDto;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping
    public Result getCategories(@RequestParam int page) {
        return categoryService.get(page);
    }

    @GetMapping("/{id}")
    public Result getCategoryById(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable Integer id) {
        return categoryService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        return categoryService.edit(id, categoryDto);
    }
}
