package com.iuh.edu.fit.backend.controller;

import com.iuh.edu.fit.backend.model.Address;
import com.iuh.edu.fit.backend.model.Candidate;
import com.iuh.edu.fit.backend.repository.AddressRepository;
import com.iuh.edu.fit.backend.repository.CandidateRepository;
import com.iuh.edu.fit.backend.service.candidate.CandidateService;
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
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/new")
    public String showNewCandidateForm(Model model) {
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        model.addAttribute("candidate", candidate);
        return "candidate/candidate-form-add";
    }

    @PostMapping
    public String addCandidate(@ModelAttribute("candidate") Candidate candidate) {
        candidateService.addCandidate(candidate);
        return "redirect:/candidates";
    }

    @GetMapping()
    public String getAllCandidates(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Candidate> candidatePage = candidateService.getAllCandidates(currentPage, pageSize, "id", "asc");
        model.addAttribute("candidatePage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "candidate/list";
    }

    @GetMapping("/show-edit-form/{id}")
    public ModelAndView edit(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Candidate> opt = candidateRepository.findById(id);
        if (opt.isPresent()) {
            Candidate candidate = opt.get();
            modelAndView.addObject("candidate", candidate);
            modelAndView.addObject("address", candidate.getAddress());
            modelAndView.setViewName("candidate/candidate-form-edit");
        }
        return modelAndView;
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("candidate") Candidate candidate,
                         @ModelAttribute("address") Address address ){
        addressRepository.save(address);
        candidateRepository.save(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/delete/{id}")
    public String deleteCandidate(@PathVariable("id") Long id) {
        candidateService.deleteCandidate(id);
        return "redirect:/candidates";
    }

    @GetMapping("/search")
    public String searchCandidates(@RequestParam("name") String name, Model model,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Candidate> candidatePage = candidateRepository.findByFullNameContainingIgnoreCase(name,
                PageRequest.of(page, size));
        model.addAttribute("candidatePage", candidatePage);
        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "candidate/list";
    }

    @GetMapping("/{id}")
    public String getCandidateProfile(@PathVariable Long id, Model model) {
        Candidate candidate = candidateRepository.findById(id) .orElseThrow(() -> new IllegalArgumentException("Invalid candidate Id:" + id));
        model.addAttribute("candidate", candidate);
        return "candidate/candidate-profile";
    }
}
