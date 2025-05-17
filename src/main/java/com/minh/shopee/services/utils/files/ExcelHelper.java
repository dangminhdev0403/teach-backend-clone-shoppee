package com.minh.shopee.services.utils.files;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.minh.shopee.domain.Category;

public interface ExcelHelper {

    boolean isExcelFile(MultipartFile file);

    List<String> readExcel(MultipartFile file) throws IOException;

    List<Category> readExcelCategory(MultipartFile file) throws IOException;

}
