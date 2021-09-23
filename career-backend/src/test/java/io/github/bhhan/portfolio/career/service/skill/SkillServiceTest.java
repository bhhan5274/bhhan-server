package io.github.bhhan.portfolio.career.service.skill;

import io.github.bhhan.portfolio.career.CareerConfig;
import io.github.bhhan.portfolio.career.service.StorageService;
import io.github.bhhan.portfolio.career.service.common.StorageServiceImpl;
import io.github.bhhan.portfolio.career.web.skill.SkillDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {CareerConfig.class, SkillServiceTest.Config.class})
class SkillServiceTest {
    @Configuration
    static class Config {
        @Bean
        public StorageService storageService(){
            return new StorageServiceImpl();
        }
    }

    @Autowired
    private SkillService skillService;

    @Test
    @DisplayName("skillService cacheTest1")
    void test1(){
        skillService.addSkill(makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME"));
        skillService.getSkills();
        skillService.getSkills();

        skillService.addSkill(makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME"));
        skillService.getSkills();
    }

    @Test
    @DisplayName("skillService cacheTest2")
    void test2(){
        final Long id1 = skillService.addSkill(makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME")).getId();
        final Long id2 = skillService.addSkill(makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME")).getId();

        skillService.getSkill(id1);
        skillService.getSkill(id1);
        skillService.getSkill(id2);
        skillService.getSkill(id2);
    }

    @Test
    @DisplayName("skillService cacheTest3")
    void test3(){
        final Long id1 = skillService.addSkill(makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME")).getId();
        final Long id2 = skillService.addSkill(makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME")).getId();

        skillService.getSkills();
        skillService.getSkill(id1);
        skillService.getSkills();
        skillService.removeSkill(id1);
        skillService.getSkills();
        skillService.getSkill(id2);

        assertThrows(SkillException.class, () -> {
            skillService.getSkill(id1);
        });
    }

    @Test
    @DisplayName("skillService cacheTest4")
    void test4(){
        final Long id1 = skillService.addSkill(makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME")).getId();
        skillService.addSkill(makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME")).getId();

        skillService.getSkill(id1);
        skillService.getSkill(id1);
        skillService.getSkills();
        skillService.getSkills();
        skillService.removeSkills();
        skillService.getSkills();

        assertThrows(SkillException.class, () -> {
            skillService.getSkill(id1);
        });
    }

    @Test
    @DisplayName("skillService cacheTest5")
    void test5(){
        final SkillDto.SkillFormReq skillFormReq = makeSkillFormReq("file1.txt", "WORK", "BACKEND", "DESC", "NAME");
        final Long id1 = skillService.addSkill(skillFormReq).getId();

        skillService.getSkill(id1);
        skillService.getSkill(id1);
        skillService.getSkills();
        skillService.getSkills();
        skillService.updateSkill(id1, skillFormReq);
        skillService.getSkills();
        skillService.getSkill(id1);
    }

    private SkillDto.SkillFormReq makeSkillFormReq(String fileName, String use, String type, String description, String name){
        return SkillDto.SkillFormReq.builder()
                .file(new MockMultipartFile(fileName, fileName, "", new byte[]{}))
                .use(use)
                .type(type)
                .description(description)
                .name(name)
                .build();
    }
}