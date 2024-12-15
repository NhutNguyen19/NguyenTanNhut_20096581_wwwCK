package com.iuh.edu.fit.backend.service.job;

import com.iuh.edu.fit.backend.model.Job;
import com.iuh.edu.fit.backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService implements IJobService{

    @Autowired
    JobRepository jobRepository;

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

}
