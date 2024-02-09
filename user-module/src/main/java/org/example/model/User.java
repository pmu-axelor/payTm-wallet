package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "wallet_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "User Ful Name should not ne blank")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "User Mobile Number should not ne blank")
    @Column(nullable = false,unique = true)
    private  String userMobileNo;

    @NotBlank(message = "User Email Id should not ne blank")
    @Column(nullable = false,unique = true)
    private String userEmailId;

    private String userAddress;

    @Column(unique = true)
    private String userPan;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

}
