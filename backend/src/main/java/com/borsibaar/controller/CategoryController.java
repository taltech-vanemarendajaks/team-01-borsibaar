package com.borsibaar.controller;

import com.borsibaar.dto.CategoryRequestDto;
import com.borsibaar.dto.CategoryResponseDto;
import com.borsibaar.entity.User;
import com.borsibaar.service.CategoryService;
import com.borsibaar.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto request) {
        User user = SecurityUtils.getCurrentUser();
        return categoryService.create(request, user.getOrganizationId());
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        User user = SecurityUtils.getCurrentUser();
        return categoryService.getAllByOrg(user.getOrganizationId());
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable Long id) {
        User user = SecurityUtils.getCurrentUser();
        return categoryService.getByIdAndOrg(id, user.getOrganizationId());
    }

    @DeleteMapping({ "/{id}" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        User user = SecurityUtils.getCurrentUser();
        categoryService.deleteReturningDto(id, user.getOrganizationId());
    }
}
