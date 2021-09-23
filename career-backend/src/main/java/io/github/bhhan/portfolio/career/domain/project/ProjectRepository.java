package io.github.bhhan.portfolio.career.domain.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findFirstByIdGreaterThanOrderByIdAsc(Long projectId);
    Optional<Project> findFirstByIdLessThanOrderByIdDesc(Long projectId);
}
