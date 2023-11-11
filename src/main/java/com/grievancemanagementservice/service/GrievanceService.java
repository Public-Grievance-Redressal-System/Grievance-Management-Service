package com.grievancemanagementservice.service;

import com.grievancemanagementservice.model.Grievance;
import com.grievancemanagementservice.repository.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GrievanceService {

    @Autowired
    private GrievanceRepository grievanceRepository;

    public List<Grievance> getAll() {
        var grievances = new ArrayList<Grievance>();
        grievanceRepository.findAll().forEach(grievances::add);
        return grievances;
    }

    public Optional<Grievance> getById(UUID id) {
        return grievanceRepository.findById(id);
    }

    public void create(Grievance grievance) {
        grievance.setCreationDate(new Date());
        grievanceRepository.save(grievance);
    }

    public Grievance createOrUpdate(Grievance grievance, UUID id) {
        return grievanceRepository.findById(id)
                .map(grievanceDbObj -> {
                    System.out.println("Found grievancer ID : " + id);
                    return grievanceRepository.save(Grievance.getUpdatedGrievance(grievanceDbObj, grievance));
                })
                .orElseGet(() -> {
                    grievance.setCreationDate(new Date());
                    return grievanceRepository.save(grievance);
                });
    }

    public void deleteById(UUID id) {
        grievanceRepository.deleteById(id);
    }
}
