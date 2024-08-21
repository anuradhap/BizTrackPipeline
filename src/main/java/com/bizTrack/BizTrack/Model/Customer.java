package com.bizTrack.BizTrack.Model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
   //@NotBlank(message = "First name is required")
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "customer")
    private List<Address> Addresses;
    private String emailId;
    private String password;
    private String phoneNumber;
    private Boolean status;
    private int departmentId;
    private double salary;
}
