package com.minh.shopee.services.utils.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {

    public static List<String> readExcel(MultipartFile file) throws IOException {
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
}
