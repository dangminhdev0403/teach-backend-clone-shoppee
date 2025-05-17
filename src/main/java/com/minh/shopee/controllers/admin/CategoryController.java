package com.minh.shopee.controllers.admin;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minh.shopee.services.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j(topic = "CategoryController")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<Void> createCategoriesWithExcel(
            @org.springframework.web.bind.annotation.RequestParam("file") MultipartFile file) throws IOException {
        this.categoryService.createCategoryWithExcelFile(file);

        return ResponseEntity.ok().build();
    }

}
