package com.minh.shopee.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minh.shopee.domain.Category;
import com.minh.shopee.repository.CategoryRepository;
import com.minh.shopee.services.CategoryService;
import com.minh.shopee.services.utils.files.ExcelHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j(topic = "CategoryService")
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ExcelHelper excelHelper;

    @Override
    public void createCategoryWithExcelFile(MultipartFile file) throws IOException {
        List<Category> listCategoriesDB = this.getAllCategories();
        List<Category> listCategoriesExcel = this.excelHelper.readExcelCategory(file);

        log.info("Check listCategoriesExcel: {}", listCategoriesExcel);
        listCategoriesExcel.removeIf(categoryExcel -> {
            for (Category categoryDB : listCategoriesDB) {
                if (categoryDB.getName().equals(categoryExcel.getName())) {
                    return true;
                }
            }
            return false;
        });

        this.categoryRepository.saveAll(listCategoriesExcel);
        log.info("created ListCategory successfully: {}", listCategoriesDB);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

}
