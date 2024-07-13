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
        this.averageOrderCost = orderReportModel.getTotalOrders() != 0 ?
                (orderReportModel.getTotalRevenue().divide(BigDecimal.valueOf(orderReportModel.getCountPerStatus().get(OrderStatus.DELIVERED)), MathUtils.DEFAULT_SCALE, RoundingMode.HALF_UP)):
                BigDecimal.ZERO;
    }

}
