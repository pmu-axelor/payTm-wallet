package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserWalletCreationRequest {

   private String userId;
   private String name;
   private String emailId;
}
