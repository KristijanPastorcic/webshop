package hr.kpastor.webshop.controller;

import hr.kpastor.webshop.dao.model.Category;
import hr.kpastor.webshop.dao.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    final CategoryRepository categoryRepository;

    public HomeController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String home(Model model) {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty() == false) {
            model.addAttribute("products", categories.get(0).getProducts());
            model.addAttribute("categories", categories);
        }
        return "views/home";
    }


}
