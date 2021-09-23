package io.github.bhhan.portfolio.career.domain.skill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByOrderByIdAsc();
}
