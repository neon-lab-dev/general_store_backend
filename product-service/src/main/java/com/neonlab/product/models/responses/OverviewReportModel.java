package com.neonlab.product.models.responses;

import com.neonlab.common.enums.OrderStatus;
import com.neonlab.common.utilities.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverviewReportModel {

    private BigDecimal totalRevenue;
    private Long totalOrders;
    private Long totalProducts;
    private BigDecimal averageOrderCost;

    public OverviewReportModel (
            final ProductReportModel productReportModel,
            final OrderReportModel orderReportModel
    ){
        this.totalRevenue = orderReportModel.getTotalRevenue();
        this.totalProducts = productReportModel.getTotalProducts();
        this.totalOrders = orderReportModel.getTotalOrders();
        var deliveredOrders = BigDecimal.valueOf(orderReportModel.getCountPerStatus().get(OrderStatus.DELIVERED));
        this.averageOrderCost = !deliveredOrders.equals(BigDecimal.ZERO) ?
                (orderReportModel.getTotalRevenue().divide(deliveredOrders,MathUtils.DEFAULT_SCALE, RoundingMode.HALF_UP)) :
                BigDecimal.ZERO;
    }

}
