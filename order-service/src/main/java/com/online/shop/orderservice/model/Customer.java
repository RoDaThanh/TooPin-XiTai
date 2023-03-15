package com.online.shop.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String address;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String emailAddress;

    @OneToMany(mappedBy = "customer")
    private List<Order> orderList;

}
