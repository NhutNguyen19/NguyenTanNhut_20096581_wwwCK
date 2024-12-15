package com.iuh.edu.fit.backend.service.skill;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillRecommendationService implements ISkillRecommendationService {
    @Override
    public List<String> recommendSkills(List<String> currentSkills) {
        List<String> recommendedSkills = new ArrayList<>();
        List<String> allSkills = List.of("Java", "Spring Boot", "React", "SQL", "AWS");
        for (String skill : allSkills) {
            if (!currentSkills.contains(skill)) {
                recommendedSkills.add(skill);
            }
        }
        return recommendedSkills;
    }
}
