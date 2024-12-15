package com.iuh.edu.fit.backend.service.skill;

import java.util.List;

public interface ISkillRecommendationService {
    public List<String> recommendSkills(List<String> currentSkills);
}
