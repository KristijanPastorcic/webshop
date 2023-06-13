package hr.kpastor.webshop.controllers;

import hr.kpastor.webshop.models.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/")
public class homeController {

    @GetMapping
    public String home(Model model) {
        List<Item> items = List.of(
                new Item("meat", "img/product/product-1.jpg", "20.00$"),
                new Item("meat", "img/product/product-1.jpg", "20.00$"),
                new Item("meat", "img/product/product-1.jpg", "20.00$"));
        model.addAttribute("items", items);
        return "views/home";
    }


}
