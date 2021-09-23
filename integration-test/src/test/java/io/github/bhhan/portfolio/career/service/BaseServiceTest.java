package io.github.bhhan.portfolio.career.service;

import io.github.bhhan.portfolio.career.CareerConfig;
import io.github.bhhan.portfolio.career.domain.project.ProjectRepository;
import io.github.bhhan.portfolio.career.domain.skill.SkillRepository;
import io.github.bhhan.portfolio.career.service.project.ProjectService;
import io.github.bhhan.portfolio.career.service.skill.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

public abstract class BaseServiceTest {
    @Configuration
    @Import({CareerConfig.class})
    public static class Config {
        @Bean
        public StorageService storageService() {
            return new StorageServiceImpl();
        }
    }

    @Autowired
    protected SkillService skillService;

    @Autowired
    protected SkillRepository skillRepository;

    @Autowired
    protected ProjectService projectService;

    @Autowired
    protected ProjectRepository projectRepository;
}
