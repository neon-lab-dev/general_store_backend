package com.neonlab.product.apis;

import com.fasterxml.jackson.core.JsonParseException;
import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.utilities.ValidationUtils;
import com.neonlab.product.dtos.CartDto;
import com.neonlab.product.service.CartService;
import com.neonlab.product.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Loggable
public class AddCartApi {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ValidationUtils validationUtils;

    public ApiOutput<?> add(CartDto cartDto){
        try {
            return new ApiOutput<>(HttpStatus.OK.value(), null, cartService.add(cartDto));
        } catch (InvalidInputException | JsonParseException | ServerException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}

