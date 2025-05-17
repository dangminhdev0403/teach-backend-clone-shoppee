package com.minh.shopee.services.utils.files.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minh.shopee.domain.Category;
import com.minh.shopee.services.utils.error.AppException;
import com.minh.shopee.services.utils.files.ExcelHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ExcelHelper")
@Service
public class ExcelHelperImpl implements ExcelHelper {

    public List<String> readExcel(MultipartFile file) throws IOException {
        List<String> result = new ArrayList<>();

        try (InputStream is = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0); // lấy sheet đầu tiên

            for (Row row : sheet) {
                StringBuilder rowData = new StringBuilder();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING -> rowData.append(cell.getStringCellValue()).append(" | ");
                        case NUMERIC -> rowData.append(cell.getNumericCellValue()).append(" | ");
                        case BOOLEAN -> rowData.append(cell.getBooleanCellValue()).append(" | ");
                        default -> rowData.append("UNKNOWN | ");
                    }
                }
                result.add(rowData.toString());
            }
        }

        return result;
    }

    @Override
    public List<Category> readExcelCategory(MultipartFile file) throws IOException {
        log.info("readExcelCategory");
        this.isExcelFile(file);
        List<Category> result = new ArrayList<>();

        try (InputStream is = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0); // lấy sheet (trang excel) đầu tiên
            log.info("read sheet" + sheet.getSheetName());
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // bỏ qua dòng tiêu đề
                }
                for (Cell cell : row) {
                    if (cell.getStringCellValue() == null || cell.getStringCellValue().isEmpty()) {
                        log.info(cell.getAddress().toString() + " is empty cell ignore it");

                        continue; // bỏ qua dòng không hợp lệ

                    }
                    log.info("add cell " + cell.getAddress().toString() + " with value " + cell.getStringCellValue());
                    Category category = new Category();
                    category.setName(cell.getStringCellValue());
                    result.add(category);
                }

            }

        }

        return result;
    }

    @Override
    public boolean isExcelFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        log.info("checking file excel {}", originalFilename);

        if (originalFilename == null || (!originalFilename.endsWith(".xlsx") && !originalFilename.endsWith(".xls"))) {
            log.error("Invalid file excel");
            throw new AppException(400, "Invalid file excel", "File is not an Excel file");
        }
        log.info(originalFilename + " is an excel file");
        return true;
    }
}
