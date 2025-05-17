package com.minh.shopee.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.minh.shopee.domain.Category;

public interface CategoryService {
    void createCategoryWithExcelFile(MultipartFile file) throws IOException;

    List<Category> getAllCategories();
}
