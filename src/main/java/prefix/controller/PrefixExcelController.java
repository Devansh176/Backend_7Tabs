package prefix.controller;

import lombok.var;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import prefix.service.PrefixService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PrefixExcelController {

    @Autowired
    private PrefixService prefixService;

    @PostMapping("/uploadPrefixExcel")
    public void uploadExcel(@RequestParam("excelFile") MultipartFile file, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        if (file.isEmpty()) {
            response.getWriter().write("{success: false, msg: 'File is empty'}");
            return;
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Read Columns: 0=Title, 1=Gender, 2=Prefix
                String title = getCellValue(row.getCell(0));
                String gender = getCellValue(row.getCell(1));
                String prefixName = getCellValue(row.getCell(2));

                if (!title.isEmpty() && !gender.isEmpty() && !prefixName.isEmpty()) {
                    prefixService.createPrefix(title, gender, prefixName);
                }
            }
            response.getWriter().write("{success: true}");
        } catch (Exception e) {
            e.printStackTrace();
            // ExtJS expects a JSON-like response for failure
            response.getWriter().write("{success: false, msg: '" + e.getMessage().replace("'", "") + "'}");
        }
    }


    @GetMapping("/downloadPrefixExcel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=prefix_data.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Prefix Data");

            // Create Header Row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Title");
            header.createCell(2).setCellValue("Gender");
            header.createCell(3).setCellValue("Prefix");

            var list = prefixService.getAllPrefixes();
            int rowIdx = 1;
            for (var p : list) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getId());

                row.createCell(1).setCellValue(p.getTitle() != null ? p.getTitle().name() : "");
                row.createCell(2).setCellValue(p.getGender() != null ? p.getGender().name() : "");
                row.createCell(3).setCellValue(p.getPrefix() != null ? p.getPrefix().name() : "");
            }
            workbook.write(response.getOutputStream());
        }
    }

    // Helper to avoid NullPointerExceptions reading cells
    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return cell.toString().trim();
    }
}