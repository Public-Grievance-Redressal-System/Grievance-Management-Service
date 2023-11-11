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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/grievance")
public class GrievanceController {

    @Autowired
    private GrievanceService grievanceService;

    @GetMapping
    public List<Grievance> getAllGrievances(@RequestHeader LinkedHashMap headers) {
        System.out.println("Header userId : " + headers.get("userid"));
        UUID userId = UUID.fromString(headers.get("userid").toString());
        return grievanceService.getAll(userId);
    }

    @GetMapping("/{id}")
    public Optional<Grievance> getGrievance(@PathVariable("id") UUID id, @RequestHeader LinkedHashMap headers) {
        System.out.println("Header userId : " + headers.get("userid"));
        UUID userId = UUID.fromString(headers.get("userid").toString());
        return grievanceService.getById(id, userId);
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
    public String deleteGrievance(@PathVariable("id") UUID id, @RequestHeader LinkedHashMap headers) throws NoPermissionException {
        System.out.println("Header userId : " + headers.get("userid"));
        UUID userId = UUID.fromString(headers.get("userid").toString());
        if("SUPER_ADMIN".equals(grievanceService.getUserDetails(userId).getUserRole().toString())) {
            grievanceService.deleteById(id);
            return "Grievance deleted successfully!";
        }
        throw new NoPermissionException("You don't have permission to do this operation");
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
