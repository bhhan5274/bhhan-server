package io.github.bhhan.portfolio.dashboard.web.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
    @ModelAttribute("currentMenu")
    public String currentMenu(){
        return "dashboard";
    }

    @GetMapping("/")
    public String index(@RequestParam(value="error", defaultValue = "false") boolean error, Model model){
        if(error){
            model.addAttribute("security_exception", "Login Failed!!!");
        }
        return "project/projectDashboard";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
}
