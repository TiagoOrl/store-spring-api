package com.asm.estore.dto.client;

import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.order.OrderDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class SingleClientDTO implements Serializable {
    private int id;
    private String firstName;
    private String secondName;
    private String fullname;
    private String countryId;
    private String email;
    private String dob;
    private Date createdAt;
    private Date updatedAt;
    private AddressDTO address;
}
