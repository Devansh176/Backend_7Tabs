package prefix.service;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import prefix.entity.Prefix;

import java.io.*;
import java.util.List;

@Service
public class PuppeteerService {

    @Autowired
    private PrefixService prefixService;

    @Value("${puppeteer.script.dir}")
    private String scriptDir;

    @Value("${puppeteer.node.path}")
    private String nodeExe;

    @Value("${puppeteer.script.name}")
    private String scriptName;

    @Value("${puppeteer.output.file}")
    private String outputPdfName;

    @Value("${puppeteer.html.file}")
    private String inputHtmlName;

    public void generatePdf(OutputStream outputStream) throws IOException {
        createDataHtmlFile();
        runPuppeteerScript();

        File pdfFile = new File(scriptDir, outputPdfName);
        if (!pdfFile.exists()) {
            throw new RuntimeException("PDF file was not generated at: " + pdfFile.getAbsolutePath());
        }

        try (FileInputStream in = new FileInputStream(pdfFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private void createDataHtmlFile() throws IOException {
        List<Prefix> list = prefixService.getAllPrefixes();

        StringBuilder html = new StringBuilder();
        html.append("<html><body><h1>Prefix Report</h1><table>");
        for (var p : list) {
            html.append("<tr><td>").append(p.getId()).append("</td></tr>");
        }
        html.append("</table></body></html>");

        File htmlFile = new File(scriptDir, inputHtmlName);
        try (PrintWriter writer = new PrintWriter(htmlFile)) {
            writer.write(html.toString());
        }
    }


    private void runPuppeteerScript() throws IOException {
        try {
            ProcessBuilder builder = new ProcessBuilder(nodeExe, scriptName);
            builder.directory(new File(scriptDir));

            Process process = builder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                String errorMsg = readInputStream(process.getErrorStream());

                throw new RuntimeException("Node script failed. Exit: " + exitCode + " Error: " + errorMsg);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Process interrupted", e);
        }
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        // Read the stream in chunks
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        // Convert bytes to String
        return new String(buffer.toByteArray(), "UTF-8");
    }
}