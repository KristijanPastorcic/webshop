package hr.kpastor.webshop.controller;

import hr.kpastor.webshop.dao.model.Basket;
import hr.kpastor.webshop.dao.model.Category;
import hr.kpastor.webshop.dao.model.Item;
import hr.kpastor.webshop.dao.model.Product;
import hr.kpastor.webshop.dao.repository.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/index")
@SessionAttributes({"basket", "user"})
public class HomeController {

    final CategoryRepository categoryRepository;

    public HomeController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        Category category = categoryRepository.findAll().get(0);
        model.addAttribute("current_category", category);
        model.addAttribute("products", category.getProducts());
        return "views/home";
    }

    @GetMapping("/{category_id}")
    public String viewCategory(@PathVariable String category_id, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        categoryRepository.findById(category_id).ifPresent(category -> {
            model.addAttribute("current_category", category);
            model.addAttribute("products", category.getProducts());
        });
        return "views/home";
    }

    @GetMapping("/{category_id}/{name}")
    public String viewProduct(@PathVariable String category_id, @PathVariable String name, Model model) {
        Optional<Product> productOptional = categoryRepository.findProductByNameAndCategoryId(name, category_id);
        productOptional.ifPresent(product -> {
            model.addAttribute("product", product);
        });
        return "views/ProductDetail";
    }

    @ModelAttribute("basket")
    public Basket storeBasket() {
        return new Basket();
    }

    @PostMapping("/addToBasket/{category_id}/{name}")
    public String StoreItemInBasket(@PathVariable String category_id, @PathVariable String name, int quantity, HttpSession session) {
        Optional<Product> productOptional = categoryRepository.findProductByNameAndCategoryId(name, category_id);
        productOptional.ifPresent(product -> {
            Basket basket = (Basket) session.getAttribute("basket");
            basket.add(new Item(product, quantity));
        });
        return "redirect:/index";
    }

}

