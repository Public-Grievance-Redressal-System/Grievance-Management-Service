package com.grievancemanagementservice.repository;

import com.grievancemanagementservice.model.Grievance;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GrievanceRepository extends CrudRepository<Grievance, UUID> {
}
