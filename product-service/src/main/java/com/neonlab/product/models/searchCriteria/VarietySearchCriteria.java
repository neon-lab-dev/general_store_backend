package com.neonlab.product.models.searchCriteria;

import com.neonlab.common.models.searchCriteria.PageableSearchCriteria;
import com.neonlab.product.enums.VarietyType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VarietySearchCriteria extends PageableSearchCriteria {

    /**
     * filter by productId
     */
    private String productId;
    /**
     * filter by varietyType
     */
    private VarietyType varietyType;

    /**
     * filter by varietyValue
     */
    private String value;

    /**
     * filter by variety Description
     */
    private String varietyDescription;

    /**
     * filter by product minimum price
     */
    private BigDecimal minimumPrice;

    /**
     * filter by product maximum price
     */
    private BigDecimal maximumPrice;
    /**
     * filter by quantity
     */
    private Integer quantity;

    @Builder(builderMethodName = "varietySearchCriteriaBuilder")
    public VarietySearchCriteria (
            final String productId,
            final VarietyType varietyType,
            final String value,
            final String varietyDescription,
            final BigDecimal minimumPrice,
            final BigDecimal maximumPrice,
            final Integer quantity
    ){
        this.productId = productId;
        this.varietyType = varietyType;
        this.value = value;
        this.varietyDescription = varietyDescription;
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
        this.quantity = quantity;
    }

    public VarietySearchCriteria(final ProductSearchCriteria searchCriteria){
        this.productId = searchCriteria.getId();
        this.varietyType = searchCriteria.getVarietyType();
        this.value = searchCriteria.getValue();
        this.varietyDescription = searchCriteria.getVarietyDescription();
        this.minimumPrice = searchCriteria.getMinimumPrice();
        this.maximumPrice = searchCriteria.getMaximumPrice();
        this.quantity = searchCriteria.getQuantity();
        this.setSortBy(searchCriteria.getSortBy());

    }

}
