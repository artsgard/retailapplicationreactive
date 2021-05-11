package com.artsgard.retailapplicationreactive.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author WillemDragstra
 *
 */
@Data
@ToString
@NoArgsConstructor
public class Company implements Serializable {
    private static final long serialVersionUID = -6485417372344318985L;

    @Id
    private Long id;

    @NotNull(message = "some message1")
    //@Email(message = "{email.notempty}")
    @Size(min = 2, max = 80, message = "some message2")
    private String companyName;

    @NotNull
    @Size(min = 4, max = 80)
    private String companyRef;

    private String description;

    private Timestamp registerDate;

    //private List<User> companyUsers = new ArrayList<>(0);
    //private List<Product> companyProducts = new ArrayList<>(0);

}

