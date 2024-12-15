package com.iuh.edu.fit.backend.controller;

import com.iuh.edu.fit.backend.model.Job;
import com.iuh.edu.fit.backend.service.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public String getAllJobs(Model model) {
        model.addAttribute("jobs", jobService.getAllJobs());
        return "jobs";
    }

    @GetMapping("/new")
    public String createJobForm(Model model) {
        Job job = new Job();
        model.addAttribute("job",job);
        return "job/job-form-add";
    }

    @PostMapping
    public String saveJob(@ModelAttribute Job job) {
        jobService.saveJob(job);
        return "redirect:/jobs";
    }
}
