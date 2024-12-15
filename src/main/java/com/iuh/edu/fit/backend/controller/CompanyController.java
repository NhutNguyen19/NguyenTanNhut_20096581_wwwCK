package com.iuh.edu.fit.backend.controller;

import com.iuh.edu.fit.backend.dto.DataMailDTO;
import com.iuh.edu.fit.backend.model.Address;
import com.iuh.edu.fit.backend.model.Candidate;
import com.iuh.edu.fit.backend.model.Company;
import com.iuh.edu.fit.backend.model.JobSkill;
import com.iuh.edu.fit.backend.repository.AddressRepository;
import com.iuh.edu.fit.backend.repository.CompanyRepository;
import com.iuh.edu.fit.backend.service.candidate.ICandidateService;
import com.iuh.edu.fit.backend.service.company.ICompanyService;
import com.iuh.edu.fit.backend.service.email.IMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ICandidateService candidateService;
    @Autowired
    private IMailService mailService;

    @GetMapping("/new")
    public String showNewCompanyForm(Model model) {
        Company company = new Company();
        company.setAddress(new Address());
        model.addAttribute("company", company);
        return "company/company-form-add";
    }

    @PostMapping
    public String addCompany(@ModelAttribute("company") Company company) {
        companyService.addCompany(company);
        return "redirect:/companies";
    }

    @GetMapping()
    public String getAllCompanies(Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Company> companyPage = companyService.getAllCompanies(currentPage, pageSize, "id", "asc");
        model.addAttribute("companyPage", companyPage);

        int totalPages = companyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "company/list";
    }

    @GetMapping("/show-edit-form/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Company> opt = companyRepository.findById(id);
        if (opt.isPresent()) {
            Company company = opt.get();
            modelAndView.addObject("company", company);
            modelAndView.addObject("address", company.getAddress());
            modelAndView.setViewName("company/company-form-edit");
        }
        return modelAndView;
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("company") Company company,
                         @ModelAttribute("address") Address address) {
        addressRepository.save(address);
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }

    @GetMapping("/{id}")
    public String getCompanyProfile(@PathVariable Long id, Model model) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        model.addAttribute("company", company);
        return "company/company-profile";
    }

    @GetMapping("/findCandidates")
    public String findCandidatesForm(Model model) {
        model.addAttribute("skills", "");
        return "findCandidates";
    }

    @PostMapping("/sendInvitations")
    public String sendInvitations(@RequestParam List<String> candidateEmails) {
        DataMailDTO dataMailDTO = new DataMailDTO();
        for (String email : candidateEmails) {
            dataMailDTO.setTo(email);
            dataMailDTO.setSubject("Job Opportunity");
            dataMailDTO.setProps(Map.of("message", "We found a job that matches your skills!"));
            try {
                mailService.sendInvitationEmail(dataMailDTO, "invitationTemplate");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/companies/findCandidates";
    }
}
