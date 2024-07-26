package com.neonlab.product.repository.specifications;

import com.neonlab.common.utilities.StringUtil;
import com.neonlab.product.constants.EntityConstant;
import com.neonlab.product.entities.Category;
import com.neonlab.product.entities.Product;
import com.neonlab.product.entities.Variety;
import com.neonlab.product.models.searchCriteria.ProductSearchCriteria;
import com.neonlab.product.models.searchCriteria.VarietySearchCriteria;
import jakarta.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.neonlab.common.constants.GlobalConstants.Symbols.PERCENTAGE;
import static com.neonlab.product.constants.EntityConstant.Product.*;
import static com.neonlab.product.constants.EntityConstant.Variety.*;

public class VarietySpecifications {

    public static Specification<Variety> buildSearchCriteria(final VarietySearchCriteria searchCriteria){
        var retVal = Specification.<Variety>where(null);
        if (!StringUtils.isEmpty(searchCriteria.getProductId())){
            retVal = retVal.and(filterByProductId(searchCriteria.getProductId()));
        }
        if (Objects.nonNull(searchCriteria.getMinimumPrice())){
            retVal = retVal.and(filterByMinimumPrice(searchCriteria.getMinimumPrice()));
        }
        if (Objects.nonNull(searchCriteria.getMaximumPrice())){
            retVal = retVal.and(filterByMaximumPrice(searchCriteria.getMaximumPrice()));
        }
        if (Objects.nonNull(searchCriteria.getQuantity())){
            retVal = retVal.and(filterByVarietyQuantity(searchCriteria.getQuantity()));
        }
        if (!StringUtil.isNullOrEmpty(searchCriteria.getVarietyDescription())){
            var words = searchCriteria.getVarietyDescription().split(" ");
            Specification<Variety> varietySpecification = Specification.where(null);
            for (var word : words){
                varietySpecification = varietySpecification.and(filterByVarietyDescriptionLike(withLikePattern(word)));
            }
            retVal = retVal.and(varietySpecification);
        }
        return retVal;
    }

    private static Specification<Variety> filterByProductId(final String productId){
        return ((root, query, criteriaBuilder) ->
        {
            Join<Variety, Product> productVarietyJoin = root.join(PRODUCT);
            return criteriaBuilder.equal(productVarietyJoin.get(EntityConstant.ID), productId);
        }
        );
    }


    private static Specification<Variety> filterByMinimumPrice(final BigDecimal startingPrice){
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(PRICE), startingPrice)
                );
    }

    private static Specification<Variety> filterByMaximumPrice(final BigDecimal endingPrice){
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(PRICE), endingPrice)
                );
    }

    private static Specification<Variety> filterByVarietyDescriptionLike(final String varietyDescription){
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(DESCRIPTION), varietyDescription)
                );
    }

    private static Specification<Variety> filterByVarietyQuantity(final Integer quantity){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(QUANTITY), quantity)
                );
    }

    private static String withLikePattern(String str){
        return PERCENTAGE + str + PERCENTAGE;
    }

}
