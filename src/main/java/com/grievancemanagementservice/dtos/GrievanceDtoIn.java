package com.grievancemanagementservice.dtos;

import com.grievancemanagementservice.model.Departments;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrievanceDtoIn {
    private long userId;
    private String title;
    private String body;
    private Departments department;
}
