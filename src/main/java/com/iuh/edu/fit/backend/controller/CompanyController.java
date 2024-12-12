package com.iuh.edu.fit.backend.controller;

import com.iuh.edu.fit.backend.model.Address;
import com.iuh.edu.fit.backend.model.Company;
import com.iuh.edu.fit.backend.repository.AddressRepository;
import com.iuh.edu.fit.backend.repository.CompanyRepository;
import com.iuh.edu.fit.backend.service.company.CompanyService;
import com.iuh.edu.fit.backend.service.company.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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
    public ModelAndView edit(@PathVariable("id") long id){
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
                         @ModelAttribute("address") Address address ){
        addressRepository.save(address);
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }

//    @GetMapping("/search")
//    public String searchCompanies(@RequestParam("name") String name, Model model,
//                                  @RequestParam(value = "page", defaultValue = "1") int page,
//                                  @RequestParam(value = "size", defaultValue = "10") int size) {
//        Page<Company> companyPage = companyRepository.findByFullNameContainingIgnoreCase(name,
//                PageRequest.of(page, size));
//        model.addAttribute("companyPage", companyPage);
//        int totalPages = companyPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        return "company/list";
//    }

    @GetMapping("/{id}")
    public String getCompanyProfile(@PathVariable Long id, Model model) {
        Company company = companyRepository.findById(id) .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        model.addAttribute("company", company);
        return "company/company-profile";
    }
}
