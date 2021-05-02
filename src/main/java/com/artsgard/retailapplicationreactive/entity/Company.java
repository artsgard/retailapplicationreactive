package com.artsgard.retailapplicationreactive.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.springframework.data.annotation.Id;

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
    private String companyName;
    private String companyRef;
    private String description;
    private Timestamp registerDate;

    //private List<User> companyUsers = new ArrayList<>(0);
    //private List<Product> companyProducts = new ArrayList<>(0);

}

