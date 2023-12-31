package com.asm.estore.dto.client;

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
    private String countryId;
    private String email;
    private String dob;
    private Date createdAt;
    private Date updatedAt;
    private Set<OrderDTO> orders;
}
