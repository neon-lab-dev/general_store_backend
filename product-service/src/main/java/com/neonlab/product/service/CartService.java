package com.neonlab.product.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.UserDto;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.services.UserService;
import com.neonlab.common.utilities.JsonUtils;
import com.neonlab.common.utilities.ObjectMapperUtils;
import com.neonlab.product.dtos.BoughtProductDetailsDto;
import com.neonlab.product.dtos.CartDto;
import com.neonlab.product.entities.Cart;
import com.neonlab.product.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Loggable
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    public CartDto add(CartDto cartDto) throws InvalidInputException, JsonParseException, ServerException {
        var loggedInUser = userService.getLoggedInUser();
        cartDto.setUserDetailsDto(UserDto.parse(loggedInUser));
        for (var boughtProduct : cartDto.getBoughtProductDetailsList()){
            var variety = productService.fetchVarietyById(boughtProduct.getVarietyId());
            var productVarietyResponse = productService.fetchProductVarietyResponse(variety);
            ObjectMapperUtils.map(productVarietyResponse, boughtProduct);
            boughtProduct.setup();
        }
        var cart = cartDto.parseToEntity();
        cart.setCreatedBy(loggedInUser.getPrimaryPhoneNo());
        cart = cartRepository.save(cart);
        var retVal = CartDto.parse(cart);
        retVal.setUserDetailsDto(UserDto.parse(loggedInUser));
        return retVal;
    }

    public CartDto update(CartDto cartDto) throws InvalidInputException, JsonParseException, ServerException {
        var loggedInUser = userService.getLoggedInUser();
        var prevCart = fetchByUserId(loggedInUser.getId());
        for (var boughtProduct : cartDto.getBoughtProductDetailsList()){
            getUpdatedBoughtProduct(boughtProduct);
        }
        prevCart.setBoughtProductDetails(JsonUtils.jsonOf(cartDto.getBoughtProductDetailsList()));
        prevCart = cartRepository.save(prevCart);
        var retVal = CartDto.parse(prevCart);
        retVal.setUserDetailsDto(UserDto.parse(loggedInUser));
        return retVal;
    }

    private void getUpdatedBoughtProduct(BoughtProductDetailsDto boughtProduct) throws InvalidInputException, ServerException {
        var variety = productService.fetchVarietyById(boughtProduct.getVarietyId());
        var productVarietyResponse = productService.fetchProductVarietyResponse(variety);
        ObjectMapperUtils.map(productVarietyResponse, boughtProduct);
        boughtProduct.setup();
    }

    private Map<String, BoughtProductDetailsDto> getVarietyIdToBoughtProductMap(List<BoughtProductDetailsDto> boughtProductDetailsDtoList){
        var retVal = new HashMap<String, BoughtProductDetailsDto>();
        boughtProductDetailsDtoList.forEach(boughtProduct -> retVal.put(boughtProduct.getVarietyId(), boughtProduct));
        return retVal;
    }

    public CartDto fetch() throws InvalidInputException, JsonParseException {
        if (isCartAvailable()){
            var user = userService.getLoggedInUser();
            var cart = fetchByUserId(user.getId());
            var retVal = CartDto.parse(cart);
            retVal.setUserDetailsDto(UserDto.parse(user));
            return retVal;
        }
        return new CartDto();
    }

    public void delete() throws InvalidInputException {
        var user = userService.getLoggedInUser();
        var cart = fetchByUserId(user.getId());
        cartRepository.delete(cart);
    }

    public boolean isCartAvailable() throws InvalidInputException {
        var loggedInUser = userService.getLoggedInUser();
        var cartMayBe = cartRepository.findByUserId(loggedInUser.getId());
        return cartMayBe.isPresent();
    }

    public Cart fetchByUserId(String userId) throws InvalidInputException {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new InvalidInputException("Cart not found for user "+ userId));
    }

}
