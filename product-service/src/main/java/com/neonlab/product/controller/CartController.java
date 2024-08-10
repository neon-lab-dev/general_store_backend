package com.neonlab.product.controller;

import com.neonlab.common.dto.ApiOutput;
import com.neonlab.product.apis.AddOrUpdateCartApi;
import com.neonlab.product.apis.FetchCartApi;
import com.neonlab.product.dtos.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final AddOrUpdateCartApi addOrUpdateCartApi;
    private final FetchCartApi fetchCartApi;

    @PostMapping("/create-or-update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiOutput<?> create(@RequestBody CartDto cartDto){
        return addOrUpdateCartApi.process(cartDto);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiOutput<?> list(){
        return fetchCartApi.process();
    }

}
