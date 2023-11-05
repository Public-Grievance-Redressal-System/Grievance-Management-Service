package com.grievancemanagementservice.controller;

import com.grievancemanagementservice.model.Grievance;
import com.grievancemanagementservice.service.GrievanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/grievance")
public class GrievanceController {

    @Autowired
    private GrievanceService grievanceService;

    @GetMapping
    public List<Grievance> getAllGrievances() {
        return grievanceService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Grievance> getAllGrievances(@PathVariable("id") UUID id) {
        return grievanceService.getById(id);
    }

    @PostMapping
    public Grievance createGrievance(@RequestBody Grievance grievance) {
        grievanceService.create(grievance);
        return grievance;
    }

    @PutMapping
    public Grievance updateGrievance(@RequestBody Grievance grievance) {
        grievanceService.update(grievance);
        return grievance;
    }

    @DeleteMapping("/{id}")
    public void deleteGrievance(@PathVariable("id") UUID id) {
        grievanceService.deleteById(id);
    }
}
