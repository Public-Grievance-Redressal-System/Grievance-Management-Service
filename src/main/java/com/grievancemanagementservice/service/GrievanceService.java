package com.grievancemanagementservice.service;

import com.grievancemanagementservice.dtos.ChannelDto;
import com.grievancemanagementservice.dtos.GrievanceDtoIn;
import com.grievancemanagementservice.dtos.GrievanceDtoOut;
import com.grievancemanagementservice.dtos.NotificationRequestDto;
import com.grievancemanagementservice.model.Grievance;
import com.grievancemanagementservice.repository.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GrievanceService {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

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

    public ResponseEntity<GrievanceDtoOut> create(GrievanceDtoIn grievanceDto) {
        Grievance grievance = Grievance.from(grievanceDto);
        //grievance.setCreationDate(new Date());
        grievanceRepository.save(grievance);
        ResponseEntity<String> response = notifyUser(grievance,ChannelDto.SMS);
        System.out.println(response.getBody());
        GrievanceDtoOut grievanceDtoOut = GrievanceDtoOut.from(grievance);
        return new ResponseEntity<GrievanceDtoOut>(grievanceDtoOut, HttpStatus.OK);
    }
    ResponseEntity<String> notifyUser(Grievance grievance, ChannelDto channel){
        String sendNotificationUrl = "http://localhost:4000/notifications/send";
        RestTemplate restTemplate = restTemplateBuilder.build();
        NotificationRequestDto requestDto =
                NotificationRequestDto.newGrievanceFrom(grievance, ChannelDto.SMS);
        System.out.println("RequestDto userId: "+requestDto.getUserId());
        ResponseEntity<String> response = restTemplate.postForEntity(
                sendNotificationUrl,requestDto,String.class);
        return response;
    }
    public Grievance createOrUpdate(Grievance grievance, UUID id) {
        grievanceRepository.save(grievance);
        return grievanceRepository.findById(id)
                .map(userObj -> {
                    grievance.setId(id);
                    return grievanceRepository.save(grievance);
                })
                .orElseGet(() -> grievanceRepository.save(grievance));
    }

    public void deleteById(UUID id) {
        grievanceRepository.deleteById(id);
    }
}
