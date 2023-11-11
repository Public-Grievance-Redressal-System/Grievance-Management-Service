package com.grievancemanagementservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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

import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Date creationDate;

    @Column(name = "closing_date")
    private Date closingDate;

    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "assigned_to")
    private UUID assignedTo;

    @Column
    @Enumerated(EnumType.STRING)
    private GrievanceStatusEnum status = GrievanceStatusEnum.NOT_ASSIGNED;

    public static Grievance getUpdatedGrievance(Grievance grievanceDbObj, Grievance grievance) {
        if((grievanceDbObj.getTitle() != null && grievance.getTitle() != null && !grievanceDbObj.getTitle().equals(grievance.getTitle())) ||
                (grievanceDbObj.getTitle() == null && grievance.getTitle() != null))
            grievanceDbObj.setTitle(grievance.getTitle());

        if((grievanceDbObj.getBody() != null && grievance.getBody() != null && !grievanceDbObj.getBody().equals(grievance.getBody())) ||
                (grievanceDbObj.getBody() == null && grievance.getBody() != null))
            grievanceDbObj.setBody(grievance.getBody());

        if((grievanceDbObj.getStatus() != null && grievance.getStatus() != null && !grievanceDbObj.getStatus().equals(grievance.getStatus())) ||
                (grievanceDbObj.getStatus() == null && grievance.getStatus() != null)) {
            grievanceDbObj.setStatus(grievance.getStatus());
            if(grievance.getStatus().equals(GrievanceStatusEnum.RESOLVED))
                grievanceDbObj.setClosingDate(new Date());
        }

        if((grievanceDbObj.getDepartmentId() != null && grievance.getDepartmentId() != null && grievanceDbObj.getDepartmentId() != grievance.getDepartmentId()) ||
                (grievanceDbObj.getDepartmentId() == null && grievance.getDepartmentId() != null))
            grievanceDbObj.setDepartmentId(grievance.getDepartmentId());

        if((grievanceDbObj.getAssignedTo() != null && grievance.getAssignedTo() != null && grievanceDbObj.getAssignedTo() != grievance.getAssignedTo()) ||
                (grievanceDbObj.getAssignedTo() == null && grievance.getAssignedTo() != null))
            grievanceDbObj.setAssignedTo(grievance.getAssignedTo());

        if((grievanceDbObj.getCreatedBy() != null && grievance.getCreatedBy() != null && grievanceDbObj.getCreatedBy() != grievance.getCreatedBy()) ||
                (grievanceDbObj.getCreatedBy() == null && grievance.getCreatedBy() != null))
            grievanceDbObj.setCreatedBy(grievance.getCreatedBy());

        return grievanceDbObj;
    }
}
