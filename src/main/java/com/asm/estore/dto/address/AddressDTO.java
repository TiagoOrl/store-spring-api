package com.asm.estore.dto.address;


import lombok.Data;

import java.util.Date;
@Data
public class AddressDTO {
    private Integer id;
    private String street;
    private String neighborhood;
    private Integer number;
    private String city;
    private String stateOrCounty;
    private String country;
    private Date createdAt;
    private Date updatedAt;
    private String clientsName;
}
