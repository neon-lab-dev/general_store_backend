package com.neonlab.product.apis;

import com.fasterxml.jackson.core.JsonParseException;
import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.product.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@Loggable
@RequiredArgsConstructor
public class FetchCartApi {

    private final CartService cartService;

    public ApiOutput<?> process(){
        try {
            var retVal = cartService.fetch();
            if (Objects.nonNull(retVal.getBoughtProductDetailsList()) && !retVal.getBoughtProductDetailsList().isEmpty()){
                return new ApiOutput<>(HttpStatus.OK.value(), null, retVal);
            }
            return new ApiOutput<>(HttpStatus.NO_CONTENT.value(), "Empty Cart", null);
        } catch (InvalidInputException | JsonParseException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}
