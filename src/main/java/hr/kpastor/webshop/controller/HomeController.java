package hr.kpastor.webshop.controller;

import hr.kpastor.webshop.dao.model.Basket;
import hr.kpastor.webshop.dao.model.Category;
import hr.kpastor.webshop.dao.model.Item;
import hr.kpastor.webshop.dao.model.Product;
import hr.kpastor.webshop.dao.repository.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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

    /**
     * Adds product to basket and redirects back to PLP
     */
    @PostMapping("/addToBasket/{category_id}/{name}")
    public String AddItemInBasket(@PathVariable String category_id,
                                  @PathVariable String name,
                                  int quantity,
                                  HttpSession session) {
        Optional<Product> productOptional = categoryRepository.findProductByNameAndCategoryId(name, category_id);
        productOptional.ifPresent(product -> {
            Basket basket = (Basket) session.getAttribute("basket");
            basket.add(new Item(product, quantity));
        });
        return "redirect:/index";
    }

    /**
     * View basket
     */
    @GetMapping("/basket")
    public String viewBasket(HttpSession session, Model model) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null) {
            basket = new Basket();
            basket.setItemsInBasket(0);
        }
        model.addAttribute("basket", basket);
        return "views/Basket";
    }

    /**
     * remove one item from basket
     */
    @GetMapping("/basket/rm/{index}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeProductFromBasket(@PathVariable int index, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        basket.removeProduct(index);

        Map<String, Object> responseData = new HashMap<>();

        responseData.put("total", basket.getTotal());
        responseData.put("count", basket.getItemsInBasket());
        return ResponseEntity.ok(responseData);
    }

    /**
     * inc basket product
     */
    @GetMapping("/basket/inc/{index}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> incProductInBasket(@PathVariable int index, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        Item item = basket.getItemList().get(index);

        item.setQuantity(item.getQuantity() + 1);
        item.setTotal(item.getTotal() + item.getPrice());
        basket.setItemsInBasket(basket.getItemsInBasket() + 1);
        basket.setTotal(basket.getTotal() + item.getPrice());


        return ResponseEntity.ok(basket.getBasketData(item));
    }

    @GetMapping("/basket/dec/{index}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> decProductInBasket(@PathVariable int index, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        Item item = basket.getItemList().get(index);
        if (item.getQuantity() < 1) {
            return ResponseEntity.ok(basket.getBasketData(item));
        }

        item.setQuantity(item.getQuantity() - 1);
        item.setTotal(item.getTotal() - item.getPrice());
        basket.setItemsInBasket(basket.getItemsInBasket() - 1);
        basket.setTotal(basket.getTotal() - item.getPrice());


        return ResponseEntity.ok(basket.getBasketData(item));
    }

}

