package com.iuh.edu.fit.backend.repository;

import com.iuh.edu.fit.backend.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<Candidate, Long> {
    boolean findByEmail(String email);
}
