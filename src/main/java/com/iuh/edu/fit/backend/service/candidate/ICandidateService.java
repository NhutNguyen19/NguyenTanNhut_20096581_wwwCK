package com.iuh.edu.fit.backend.service.candidate;

import com.iuh.edu.fit.backend.enums.SkillType;
import com.iuh.edu.fit.backend.model.Candidate;
import com.iuh.edu.fit.backend.model.JobSkill;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICandidateService {
    public Page<Candidate> getAllCandidates(int page, int size, String sortBy, String direction);
    public Candidate getCandidateById(Long id);
    public void addCandidate(Candidate candidate);
    public void deleteCandidate(Long id);
}
