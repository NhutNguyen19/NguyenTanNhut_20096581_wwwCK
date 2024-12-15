package com.iuh.edu.fit.backend.service.job;

import com.iuh.edu.fit.backend.model.Job;

import java.util.List;

public interface IJobService {
    List<Job> getAllJobs();
    Job saveJob(Job job);
}
