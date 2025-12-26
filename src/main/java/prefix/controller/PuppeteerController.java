package prefix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import prefix.service.PuppeteerService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PuppeteerController {

    @Autowired
    private PuppeteerService puppeteerService;

    @GetMapping("/downloadPuppeteerPdf")
    public void downloadPdf(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Mednet_Report.pdf");

            puppeteerService.generatePdf(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Error generating PDF: " + e.getMessage());
        }
    }
}