package com.iuh.edu.fit.backend.controller;

import com.iuh.edu.fit.backend.service.skill.SkillRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/recommends")
public class SkillRecommendationController {
    @Autowired
    private SkillRecommendationService skillRecommendationService;

    @GetMapping("/recommendSkills")
    public String recommendSkills(@RequestParam List<String> skills, Model model) {
        List<String> recommendedSkills = skillRecommendationService.recommendSkills(skills);
        model.addAttribute("recommendedSkills", recommendedSkills);
        return "recommendedSkills";
    }
}
