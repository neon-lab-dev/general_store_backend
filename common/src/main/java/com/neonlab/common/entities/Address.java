package com.neonlab.common.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neonlab.common.utilities.JsonUtils;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_address_name", columnList = "address_name")
})

public class Address extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "landmark")
    private String landMark;

    @Column(name = "address_line1")
    private String addressLine1;  // can't be null

    @Column(name="address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city; // UI dropdown list

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "address_name")
    private String addressName; // can't be null

    @Column(name="primary_address")
    private boolean primaryAddress=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    //generic
    public Address(String createdBy,String modifiedBy){
        super(createdBy,modifiedBy);
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }
}
