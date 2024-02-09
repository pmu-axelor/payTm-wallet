package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.ServiceType;

@Setter
@Getter
@Builder
public class SendMailNotification {
    private String receviersMailId;

    private String mailSubject;

    private String mailBody;

    private ServiceType serviceType;

}
