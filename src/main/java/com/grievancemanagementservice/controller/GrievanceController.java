package com.grievancemanagementservice.controller;

import com.grievancemanagementservice.model.Grievance;
import com.grievancemanagementservice.service.GrievanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @PutMapping("/{id}")
    public Grievance updateGrievance(@RequestBody Grievance grievance, @PathVariable("id") UUID id) {
        return grievanceService.createOrUpdate(grievance, id);
    }

    @DeleteMapping("/{id}")
    public void deleteGrievance(@PathVariable("id") UUID id) {
        grievanceService.deleteById(id);
    }

    @GetMapping("/{grievanceId}/{departmentId}")
    public String patchGrievance(@PathVariable("departmentId") UUID departmentId, @PathVariable("grievanceId") UUID grievanceId) {
        System.out.println("Patch departmentId : " + departmentId);
        System.out.println("Patch grievanceId : " + grievanceId);
        Grievance grievance = new Grievance();
        grievance.setDepartmentId(departmentId);
        if(grievanceService.createOrUpdate(grievance, grievanceId) != null)
            return "Grievance routed to departmentId %s successfully!".formatted(departmentId);
        return "Failed to route grievance to departmentId " + departmentId;
    }
}
