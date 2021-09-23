package io.github.bhhan.portfolio.dashboard.service;

import io.github.bhhan.portfolio.career.web.project.ProjectDto;
import io.github.bhhan.portfolio.dashboard.security.RedirectProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectProxy {
    private final RestTemplate restTemplate;
    private final RedirectProperties redirectProperties;

    public ProjectDto.ProjectRes getProject(Long projectId){
        try{
            ResponseEntity<ProjectDto.ProjectRes> responseEntity = restTemplate.getForEntity(String.format("%s/career/v1/projects/%d", redirectProperties.getGatewayUrl(), projectId), ProjectDto.ProjectRes.class);
            return responseEntity.getBody();
        }catch(Exception e){
            return null;
        }
    }
}
