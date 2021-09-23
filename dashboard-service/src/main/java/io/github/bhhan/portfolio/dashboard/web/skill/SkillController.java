package io.github.bhhan.portfolio.dashboard.web.skill;

import io.github.bhhan.portfolio.dashboard.common.OAuthProperties;
import io.github.bhhan.portfolio.dashboard.common.UserProperties;
import io.github.bhhan.portfolio.dashboard.service.SkillProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/skill")
@RequiredArgsConstructor
public class SkillController {
    private final SkillProxy skillProxy;
    private final OAuthProperties oAuthProperties;

    @ModelAttribute("currentMenu")
    public String currentMenu() {
        return "skill";
    }

    @ModelAttribute("basicAuthToken")
    public String basicAuthToken(){
        return oAuthProperties.getBasicAuthToken();
    }

    @GetMapping("/board")
    public String projectBoard(Model model) {

        model.addAttribute("skills", skillProxy.getSkills());

        return "skill/skillDashboard";
    }
}
