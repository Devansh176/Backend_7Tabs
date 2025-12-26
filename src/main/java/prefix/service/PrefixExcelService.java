package prefix.service;

import lombok.var;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prefix.entity.Prefix;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
@Transactional
public class PrefixExcelService {

    @Autowired
    private PrefixService prefixService;

    // --- UPLOAD LOGIC ---
    public void processExcelUpload(InputStream inputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Read Columns
                String title = getCellValue(row.getCell(0));
                String gender = getCellValue(row.getCell(1));
                String prefixName = getCellValue(row.getCell(2));

                if (!title.isEmpty() && !gender.isEmpty() && !prefixName.isEmpty()) {
                    prefixService.createPrefix(title, gender, prefixName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing Excel file: " + e.getMessage());
        }
    }

    // --- DOWNLOAD LOGIC ---
    public void generateExcelReport(OutputStream outputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Prefix Data");

            // Header Row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Title");
            header.createCell(2).setCellValue("Gender");
            header.createCell(3).setCellValue("Prefix");

            List<Prefix> list = prefixService.getAllPrefixes();
            int rowIdx = 1;
            for (var p : list) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getTitle() != null ? p.getTitle().name() : "");
                row.createCell(2).setCellValue(p.getGender() != null ? p.getGender().name() : "");
                row.createCell(3).setCellValue(p.getPrefix() != null ? p.getPrefix().name() : "");
            }

            workbook.write(outputStream);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return cell.toString().trim();
    }
}