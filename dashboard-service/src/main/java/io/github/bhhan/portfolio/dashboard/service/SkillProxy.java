package io.github.bhhan.portfolio.dashboard.service;

import io.github.bhhan.portfolio.career.web.skill.SkillDto;
import io.github.bhhan.portfolio.dashboard.security.RedirectProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillProxy {
    private final RestTemplate restTemplate;
    private final RedirectProperties redirectProperties;

    public List<SkillDto.SkillRes> getSkills(){
        try{
            ResponseEntity<List<SkillDto.SkillRes>> responseEntity = restTemplate.exchange(String.format("%s/career/v1/skills", redirectProperties.getGatewayUrl()), HttpMethod.GET, null, new ParameterizedTypeReference<List<SkillDto.SkillRes>>(){});
            return responseEntity.getBody();
        }catch(Exception e){
            return null;
        }
    }
}
