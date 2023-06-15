package hr.kpastor.webshop.backoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backoffice")
public class BackOfficeHomeController {
    @GetMapping
    public String home(Model model) {
        return "/backoffice/views/home";
    }
}
