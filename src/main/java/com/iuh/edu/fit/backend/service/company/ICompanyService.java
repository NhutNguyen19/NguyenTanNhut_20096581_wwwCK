package com.iuh.edu.fit.backend.service.company;

import com.iuh.edu.fit.backend.model.Candidate;
import com.iuh.edu.fit.backend.model.Company;
import org.springframework.data.domain.Page;

public interface ICompanyService {
    public Page<Company> getAllCompanies(int page, int size, String sortBy, String direction);
    public Company getCompanyById(Long id);
    public void addCompany(Company company);
    public void deleteCompany(Long id);
}
