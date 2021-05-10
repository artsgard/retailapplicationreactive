package com.artsgard.retailapplicationreactive.entity;

import org.springframework.data.annotation.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 7897726368667201276L;

    @Id
    private Long id;


    @NotNull
    @Size(min = 2, max = 20)
    private String username;

    @NotNull
    private String password;

    private String firstName;

    @NotNull
    @Size(min = 2, max = 40)
    private String lastName;

    @NotNull
    @Email
    private String email;

    private Timestamp registerDate;

    private Timestamp modifactionDate;

    @NotNull
    private Boolean active;

    //private List<PurchaseEntity> userPurchases = new ArrayList<>(0);

    //private List<CompanyEntity> userCompanies = new ArrayList<>(0);

}