package com.iuh.edu.fit.backend.repository;

import com.iuh.edu.fit.backend.model.Candidate;
import com.iuh.edu.fit.backend.model.JobSkill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findAll(Sort sort);
    Page<Candidate> findByFullNameContainingIgnoreCase(String name, Pageable pageable);
}
