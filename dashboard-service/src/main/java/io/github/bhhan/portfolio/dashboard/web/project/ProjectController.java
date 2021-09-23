package io.github.bhhan.portfolio.dashboard.web.project;

import io.github.bhhan.portfolio.career.web.project.ProjectDto;
import io.github.bhhan.portfolio.career.web.skill.SkillDto;
import io.github.bhhan.portfolio.dashboard.common.OAuthProperties;
import io.github.bhhan.portfolio.dashboard.service.ProjectProxy;
import io.github.bhhan.portfolio.dashboard.service.SkillProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final SkillProxy skillProxy;
    private final ProjectProxy projectProxy;
    private final OAuthProperties oAuthProperties;

    @ModelAttribute("currentMenu")
    public String currentMenu(){
        return "project";
    }

    @ModelAttribute("basicAuthToken")
    public String basicAuthToken(){
        return oAuthProperties.getBasicAuthToken();
    }

    @GetMapping("/board")
    public String projectBoard(Authentication authentication, Model model){
        model.addAttribute("admin", Objects.nonNull(authentication) ? "admin" : "anonymous");
        return "project/projectDashboard";
    }

    @GetMapping("/addForm")
    public String projectAddForm(Model model){
        model.addAttribute("skills", skillProxy.getSkills());
        return "project/addForm";
    }

    @GetMapping("/updateForm/{projectId}")
    public String projectUpdateForm(@PathVariable Long projectId, Model model){
        final List<SkillDto.SkillSelectedRes> allSkills = skillProxy.getSkills()
                .stream()
                .map(SkillDto.SkillSelectedRes::new)
                .collect(Collectors.toList());

        final ProjectDto.ProjectRes projectRes = projectProxy.getProject(projectId);

        projectRes.setImages(
                projectRes.getImages()
                        .stream()
                        .sorted(Comparator.comparingLong(ProjectDto.ImageRes::getId).reversed())
                        .collect(Collectors.toList())
        );

        projectRes.getSkills()
                .forEach(projectSkill -> {
                    for (SkillDto.SkillSelectedRes skill : allSkills) {
                        if(projectSkill.getId().equals(skill.getId())){
                            skill.select();
                            break;
                        }
                    }
                });

        model.addAttribute("skills", allSkills);
        model.addAttribute("project", projectRes);
        return "project/updateForm";
    }
}
