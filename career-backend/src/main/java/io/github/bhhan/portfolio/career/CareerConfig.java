package io.github.bhhan.portfolio.career;

import io.github.bhhan.portfolio.career.domain.project.ProjectRepository;
import io.github.bhhan.portfolio.career.domain.skill.SkillRepository;
import io.github.bhhan.portfolio.career.service.StorageService;
import io.github.bhhan.portfolio.career.service.project.ProjectService;
import io.github.bhhan.portfolio.career.service.skill.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@RequiredArgsConstructor
@Import(CareerCacheConfig.class)
public class CareerConfig {
    private final SkillRepository skillRepository;
    private final StorageService storageService;
    private final ProjectRepository projectRepository;

    @Bean
    public SkillService skillService(){
        return new SkillService(skillRepository, storageService);
    }

    @Bean
    public ProjectService projectService(){
        return new ProjectService(projectRepository, skillRepository, storageService);
    }
}
