package com.neonlab.product.dtos;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.neonlab.common.dto.UserDto;
import com.neonlab.common.utilities.JsonUtils;
import com.neonlab.product.entities.Cart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.List;

@Data
public class CartDto {

    private String id;
    @NotNull @Valid
    private List<BoughtProductDetailsDto> boughtProductDetailsList;
    private UserDto userDetailsDto;

    private static ModelMapper entityMapper = new ModelMapper();

    static {
        entityMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        TypeMap<CartDto, Cart> propertyMapper = entityMapper.createTypeMap(CartDto.class, Cart.class);
        propertyMapper
                .addMapping(cartDto -> cartDto.getUserDetailsDto().getId(), Cart::setUserId)
        ;
    }

    public Cart parseToEntity(){
        var retVal = entityMapper.map(this, Cart.class);
        var json = JsonUtils.jsonOf(this.getBoughtProductDetailsList());
        retVal.setBoughtProductDetails(json);
        return retVal;
    }

    public static CartDto parse(Cart currentCart) throws JsonParseException {
        var retVal = entityMapper.map(currentCart, CartDto.class);
        var boughtProductDetails = JsonUtils.readObjectFromJson(currentCart.getBoughtProductDetails(), new TypeReference<List<BoughtProductDetailsDto>>() {});
        retVal.setBoughtProductDetailsList(boughtProductDetails);
        return retVal;
    }

}
