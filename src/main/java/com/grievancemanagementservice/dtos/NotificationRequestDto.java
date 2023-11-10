package com.grievancemanagementservice.dtos;

import com.grievancemanagementservice.model.Grievance;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NotificationRequestDto {
    private long requestingServiceId;
    private long requestingServiceNotificationId;
    private long userId;
    private String messageTitle;
    private String messageContent;
    private ChannelDto notificationChannel;
    public static NotificationRequestDto newGrievanceFrom(Grievance grievance, ChannelDto channel){
        NotificationRequestDto requestDto = new NotificationRequestDto();
        requestDto.setRequestingServiceId(1l);
        requestDto.setRequestingServiceNotificationId(1);
        requestDto.setUserId(grievance.getCreatedBy());
        requestDto.setMessageTitle("Grievance Successfully Registered");
        requestDto.setMessageContent(grievance.getTitle()+"\n Note the following id for your reference: "
                + grievance.getId().toString());
//        requestDto.setMessageContent(grievance.getTitle());
        requestDto.setNotificationChannel(channel);
        return requestDto;
    }
}
