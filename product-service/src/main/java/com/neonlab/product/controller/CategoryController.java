package com.neonlab.product.controller;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.product.apis.AddCategoryApi;
import com.neonlab.product.apis.FetchCategoryApi;
import com.neonlab.product.apis.GetAllCategoryApi;
import com.neonlab.product.apis.UpdateCategoryApi;
import com.neonlab.product.dtos.CategoryDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Loggable
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final AddCategoryApi addCategoryApi;
    private final UpdateCategoryApi updateCategoryApi;
    private final FetchCategoryApi fetchCategoryApi;
    private final GetAllCategoryApi getAllCategoryApi;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiOutput<CategoryDto> add(@ModelAttribute CategoryDto categoryDto){
        return addCategoryApi.add(categoryDto);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiOutput<CategoryDto> update(@RequestParam String existingCategoryName,@ModelAttribute CategoryDto categoryDto){
        return updateCategoryApi.update(existingCategoryName,categoryDto);
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiOutput<?> fetch(@RequestParam(value = "name",required = false) String name){
        return fetchCategoryApi.process(name);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiOutput<?> getAll(){
        return getAllCategoryApi.getAll();
    }
}
