package com.grievancemanagementservice.service;

import com.grievancemanagementservice.model.Grievance;
import com.grievancemanagementservice.model.User;
import com.grievancemanagementservice.repository.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GrievanceService {

    @Autowired
    private GrievanceRepository grievanceRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Grievance> getAll(UUID userId) {
        User user = getUserDetails(userId);
        if(user == null) return new ArrayList<>();
        String userRole = user.getUserRole().toString();
        if(userRole.equals("SUPER_ADMIN")) {
            var grievances = new ArrayList<Grievance>();
            grievanceRepository.findAll().forEach(grievances::add);
            return grievances;
        }
        else if(userRole.equals("DEPT_ADMIN")) {
            return grievanceRepository.findByDepartmentId(user.getDepartmentId());
        } else if(userRole.equals("USER")) {
            return grievanceRepository.findByCreatedBy(user.getId());
        }
        return new ArrayList<>();
    }

    public Optional<Grievance> getById(UUID id, UUID useUuid) {
        User user = getUserDetails(useUuid);
        String userRole = user.getUserRole().toString();
        switch (userRole) {
            case "SUPER_ADMIN":
                return grievanceRepository.findById(id);
            case "DEPT_ADMIN": {
                var dbUser = grievanceRepository.findById(id);
                if (dbUser.isPresent() && dbUser.get().getDepartmentId() == user.getDepartmentId())
                    return dbUser;
                break;
            }
            case "USER": {
                var dbUser = grievanceRepository.findById(id);
                if (dbUser.isPresent() && dbUser.get().getCreatedBy() == useUuid)
                    return dbUser;
                break;
            }
        }
        return Optional.empty();
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

    public User getUserDetails(UUID userId) {
        User user = restTemplate.getForObject("http://localhost:8082/user/%s/role".formatted(userId), User.class);
        System.out.println("User received fro mAdminService : " + user.toString());
        return user;
    }
}
