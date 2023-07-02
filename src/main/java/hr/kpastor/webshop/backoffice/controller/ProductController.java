package hr.kpastor.webshop.backoffice.controller;


import hr.kpastor.webshop.dao.model.Category;
import hr.kpastor.webshop.dao.model.Product;
import hr.kpastor.webshop.dao.model.validation.ValidatedProductDTO;
import hr.kpastor.webshop.dao.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;


/*
    How to properly do CRUD on products???
 */

@Controller
@RequestMapping("backoffice/product")
public class ProductController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads/ProductImages";

    final CategoryRepository categoryRepository;

    public ProductController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "backoffice/views/Product/ProductList";
    }

    @PostMapping
    public String create(ValidatedProductDTO dto) {
        Optional<Category> categoryOptional = categoryRepository.findById(dto.getCategory_id());
        categoryOptional.ifPresent(category -> {
            try {
                String imagePath = saveProductImage(category.getName(), dto.getImage());
                dto.setImagePath(imagePath);
                category.getProducts().add(new Product().copyValidatedDTO(dto));
                categoryRepository.save(category);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return "redirect:/backoffice/product";
    }

    @GetMapping("/edit/{name}")
    public String edit(@PathVariable String name, @RequestParam String category_id, Model model) {
        Optional<Product> productOptional = categoryRepository.findProductByNameAndCategoryId(name, category_id);
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            model.addAttribute("category_id", category_id);
            model.addAttribute("categories", categoryRepository.findAll());
            return "backoffice/views/Product/ProductEdit";
        } else {
            return "redirect:/backoffice/product";
        }
    }

    @PostMapping("/edit/{name}")
    public String edit(@PathVariable String name, @RequestParam String current_category_id, ValidatedProductDTO dto) throws IOException {
        Optional<Category> categoryOptional = categoryRepository.findById(current_category_id);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            // remove product form current category
            if (category.getProducts().removeIf(product -> product.getName().equals(name))) {
                categoryRepository.save(category);

                // if new category selected add product to new category else use old category
                if (current_category_id != dto.getCategory_id()) {
                    Optional<Category> newCategoryOptional = categoryRepository.findById(dto.getCategory_id());
                    if (newCategoryOptional.isPresent()) {
                        String imagePath = saveProductImage(category.getName(), dto.getImage());
                        dto.setImagePath(imagePath);
                        Category newCategory = newCategoryOptional.get();
                        newCategory.getProducts().add(new Product().copyValidatedDTO(dto));
                        categoryRepository.save(newCategory);
                    }
                } else {
                    String imagePath = saveProductImage(category.getName(), dto.getImage());
                    dto.setImagePath(imagePath);
                    category.getProducts().add(new Product().copyValidatedDTO(dto));
                    categoryRepository.save(category);
                }

            }
        }

        return "redirect:/backoffice/product";
    }

    @GetMapping("/delete/{name}")
    public String delete(@PathVariable String name, @RequestParam String category_id) {
        Optional<Category> categoryOptional = categoryRepository.findById(category_id);
        categoryOptional.ifPresent(category -> {
            if (category.getProducts().removeIf(product -> product.getName().equals(name))) {
                categoryRepository.save(category);
            }
        });
        return "redirect:/backoffice/product";
    }


    private String saveProductImage(String categoryName, MultipartFile file) throws IOException {
        // check if category folder exists
        Path parentDir = Paths.get(UPLOAD_DIRECTORY, categoryName);
        if (!Files.exists(parentDir))
            Files.createDirectories(parentDir);

        // save the image
        Path imagePath = Paths.get(UPLOAD_DIRECTORY, categoryName,
                UUID.randomUUID() + "_" + file.getOriginalFilename());
        Files.write(imagePath, file.getBytes());
        return imagePath.toString().substring(UPLOAD_DIRECTORY.length(), imagePath.toString().length()).replace("\\", "/"); // properly get relative string
    }
}
