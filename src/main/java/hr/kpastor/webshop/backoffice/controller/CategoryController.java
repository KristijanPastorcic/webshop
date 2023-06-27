package hr.kpastor.webshop.backoffice.controller;

import hr.kpastor.webshop.dao.model.Category;
import hr.kpastor.webshop.dao.model.validation.ValidatedCategoryDTO;
import hr.kpastor.webshop.dao.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("backoffice/category")
public class CategoryController {

    final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "backoffice/views/category/CategoryList";
    }

    @PostMapping("/create")
    public String create(ValidatedCategoryDTO dto) {
        categoryRepository.insert(new Category().copyValidatedDTO(dto));
        return "redirect:/backoffice/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        categoryRepository.deleteById(id);
        return "redirect:/backoffice/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            model.addAttribute("category", categoryOptional.get());
            return "backoffice/views/category/CategoryEdit";
        } else {
            return "backoffice/views/category/CategoryList";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable String id, ValidatedCategoryDTO dto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        categoryOptional.ifPresent(category -> categoryRepository.save(category.copyValidatedDTO(dto)));
        return "redirect:/backoffice/category";
    }
}
