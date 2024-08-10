package com.neonlab.product.entities;

import com.neonlab.common.entities.Generic;
import com.neonlab.common.utilities.JsonUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "cart", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class Cart extends Generic {

    public Cart(String createdBy, String modifiedBy){super(createdBy, modifiedBy);}


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "bought_product_details", columnDefinition = "JSON", nullable = false)
    private String boughtProductDetails;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Override
    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}
