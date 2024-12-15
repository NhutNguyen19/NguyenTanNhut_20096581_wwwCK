package com.iuh.edu.fit.backend.repository;

import com.iuh.edu.fit.backend.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

}
