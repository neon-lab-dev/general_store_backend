package com.neonlab.common.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neonlab.common.utilities.JsonUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

//@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Table(name = "user", indexes = {
        @Index(name = "idx_primary_phone", columnList = "primary_phone_no"),
        @Index(name = "idx_email", columnList = "email")
})
public class User extends Generic {

    public User(){
        super();
    }

    public User(String createdBy, String modifiedBy){
        super(createdBy,modifiedBy);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)//logic to generate table id, have some doubt how to create UUId
    private String id;

    @Column(name = "name")// this column definition is optional
    private String name;// need to create one utility class for this name in common repo

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "primary_phone_no", unique = true)
    private String primaryPhoneNo;// can't be null

    @Column(name = "secondary_phone_no")
    private String secondaryPhoneNo;//optional

    //one more column need to add generic
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Address> addresses = new ArrayList<>();

    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}
