package com.grievancemanagementservice.model;

import com.grievancemanagementservice.dtos.GrievanceDtoIn;
import com.grievancemanagementservice.utils.GrievanceStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
@Data
public class Grievance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column
    private String title;

    @Column
    private String body;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "closing_date")
    private Instant closingDate;

    @Column(name = "department")
    private Departments department;

    @Column(name = "created_by")
    private long createdBy;

    @Column(name = "assigned_to")
    private UUID assignedTo;

    @Column
    @Enumerated(EnumType.STRING)
    private GrievanceStatusEnum status = GrievanceStatusEnum.NOT_ASSIGNED;

    public static Grievance from(GrievanceDtoIn grievanceDto){
        Grievance grievance = new Grievance();
        grievance.setTitle(grievanceDto.getTitle());
        grievance.setBody(grievanceDto.getBody());
        grievance.setCreatedBy(grievanceDto.getUserId());
        grievance.setDepartment(grievanceDto.getDepartment());
        Instant current =  Instant.now();
        grievance.setCreationDate(current);
        grievance.setClosingDate(Grievance.getClosingDate(current));
        return grievance;
    }
    public static Instant getClosingDate(Instant current){
        Instant now = current;
        LocalDate currentDate = LocalDateTime.ofInstant(now, ZoneOffset.UTC).toLocalDate();
        LocalDateTime endOfDay = LocalDateTime.of(currentDate, LocalTime.MAX);
        Instant result = endOfDay.toInstant(ZoneOffset.UTC);
        return result;
    }
}
