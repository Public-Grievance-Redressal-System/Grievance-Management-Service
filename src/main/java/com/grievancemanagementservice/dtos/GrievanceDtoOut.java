package com.grievancemanagementservice.dtos;

import com.grievancemanagementservice.model.Departments;
import com.grievancemanagementservice.model.Grievance;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class GrievanceDtoOut {
    private UUID id;
    private long userId;
    private String title;
    private String body;
    private Departments department;
    private Instant creationDate;
    private Instant closingDate;

    public static GrievanceDtoOut from(Grievance grievance){
        GrievanceDtoOut grievanceDto = new GrievanceDtoOut();
        grievanceDto.setId(grievance.getId());
        grievanceDto.setUserId(grievance.getCreatedBy());
        grievanceDto.setTitle(grievance.getTitle());
        grievanceDto.setBody(grievance.getBody());
        grievanceDto.setDepartment(grievance.getDepartment());
        grievanceDto.setCreationDate(grievance.getCreationDate());
        grievanceDto.setClosingDate(grievance.getClosingDate());
        return grievanceDto;
    }
}
