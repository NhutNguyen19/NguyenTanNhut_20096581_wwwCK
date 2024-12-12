package com.iuh.edu.fit.backend.service.candidate;

import com.iuh.edu.fit.backend.model.Candidate;
import org.springframework.data.domain.Page;

public interface ICandidateService {
    public Page<Candidate> getAllCandidates(int page, int size, String sortBy, String direction);
    public Candidate getCandidateById(Long id);
    public void addCandidate(Candidate candidate);
    public void deleteCandidate(Long id);
}
