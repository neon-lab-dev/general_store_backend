package com.neonlab.product.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.utilities.ValidationUtils;
import com.neonlab.product.dtos.CartDto;
import com.neonlab.product.service.CartService;
import com.neonlab.product.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Loggable
@RequiredArgsConstructor
public class AddOrUpdateCartApi {

    @Autowired
    private AddCartApi addCartApi;
    @Autowired
    private UpdateCartApi updateCartApi;
    @Autowired
    private CartService cartService;
    @Autowired
    private ValidationUtils validationUtils;
    @Autowired
    private OrderService orderService;


    public ApiOutput<?> process(CartDto cartDto){
        try {
            validate(cartDto);
            if (cartService.isCartAvailable()){
                return updateCartApi.update(cartDto);
            }
            return addCartApi.add(cartDto);
        } catch (InvalidInputException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    private void validate(CartDto cartDto) throws InvalidInputException {
        validationUtils.validate(cartDto);
        orderService.validateVarietyIds(cartDto.getBoughtProductDetailsList());
    }

}
