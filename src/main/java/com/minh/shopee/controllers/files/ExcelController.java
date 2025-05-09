package com.minh.shopee.controllers.files;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minh.shopee.services.utils.files.ExcelHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/excel")
@Slf4j(topic = "ExcelController")
public class ExcelController {

    @PostMapping("")
    public ResponseEntity<?> handleExcelUpload(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || (!originalFilename.endsWith(".xlsx") && !originalFilename.endsWith(".xls"))) {
            return ResponseEntity.badRequest().body("File không đúng định dạng Excel");
        }

        try {
            List<String> result = ExcelHelper.readExcel(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi đọc file: " + e.getMessage());
        }
    }

}