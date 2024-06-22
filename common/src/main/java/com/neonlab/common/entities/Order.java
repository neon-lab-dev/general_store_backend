package com.neonlab.common.entities;

import com.neonlab.common.enums.OrderStatus;
import com.neonlab.common.enums.PaymentMode;
import com.neonlab.common.utilities.JsonUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "`order`", indexes = {
        @Index(name = "idx_user_id",columnList = "user_id"),
        @Index(name = "idx_payment_id",columnList = "payment_id"),
        @Index(name = "idx_payment_mode",columnList = "payment_mode")
})

public class Order extends Generic {

    public Order(String createdBy, String modifiedBy){
        super(createdBy, modifiedBy);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "address_id")
    private String addressId;

    @Column(name = "bought_product_details", columnDefinition = "JSON", nullable = false)
    private String boughtProductDetails;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "driver_Id")
    private String driverId;

    @Column(name = "total_item_cost", nullable = false)
    private BigDecimal totalItemCost;

    @Column(name = "delivery_charges", nullable = false)
    private BigDecimal deliveryCharges;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;

    @Column(name = "delivered_at")
    private BigDecimal deliveredAt;

    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode",nullable = false)
    private PaymentMode paymentMode;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Override
    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}