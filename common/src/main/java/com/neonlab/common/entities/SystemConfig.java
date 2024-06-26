package com.neonlab.common.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "system_config", indexes = {
        @Index(name = "idx_config_key", columnList = "config_key")
})
public class SystemConfig extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_key")
    private String key;

    @Column(name = "config_value")
    private String value;
    //generic

}
