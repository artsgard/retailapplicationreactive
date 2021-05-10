package com.artsgard.retailapplicationreactive.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.*;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
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
public class Product implements Serializable {
    private static final long serialVersionUID = 8155558790497552116L;

    @Id
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private String productRef;

    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=12, fraction=2)
    private BigDecimal price;

    @NotNull
    private Boolean promotion;

    @NotNull
    private String graduation;

    @NotNull
    public enum BeerType {
        PILS, HELLES, STARKBIER, ALTBIER, DUNKELBIER
    }

    @NotNull
    private BeerType beerType;

    @NotNull
    private String nationality;

    private Timestamp creationDate;

    @NotNull
    private Company company;

    //private List<ProductPurchaseEntity> purchaseOrders = new ArrayList<>(0);

}
