package com.iuh.edu.fit.backend.service.candidate;

import com.iuh.edu.fit.backend.model.Address;
import com.iuh.edu.fit.backend.model.Candidate;
import com.iuh.edu.fit.backend.repository.AddressRepository;
import com.iuh.edu.fit.backend.repository.CandidateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CandidateService implements ICandidateService{

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Page<Candidate> getAllCandidates(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return candidateRepository.findAll(pageRequest); }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    @Override
    public void addCandidate(Candidate candidate) {
        Address address = addressRepository.save(candidate.getAddress());
        candidate.setAddress(address);
        candidateRepository.save(candidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
        candidateRepository.delete(candidate);
    }
}
