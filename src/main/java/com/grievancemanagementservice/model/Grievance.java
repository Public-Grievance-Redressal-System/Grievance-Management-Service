package com.grievancemanagementservice.model;

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
}
