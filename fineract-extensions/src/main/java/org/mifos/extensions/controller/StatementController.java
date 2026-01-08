package org.mifos.extensions.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/savingsaccounts")
public class StatementController {

    @GetMapping("/{id}/statement")
    public ResponseEntity<String> statement(@PathVariable Long id) {
        // Placeholder: return a small HTML preview; real implementation will call services to assemble DTO
        String html = "<html><body><h3>Statement preview for account " + id + "</h3><p>Placeholder content.</p></body></html>";
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
    }

    @GetMapping("/{id}/statement/pdf")
    public ResponseEntity<String> statementPdf(@PathVariable Long id) {
        // Simple PDF generation using OpenHTMLToPDF
        String html = "<html><body><h1>Statement for account " + id + "</h1><p>Generated PDF.</p></body></html>";
        try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream()) {
            com.openhtmltopdf.pdfboxout.PdfRendererBuilder builder = new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(baos);
            builder.run();
            byte[] pdf = baos.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=statement_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new String(pdf));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("PDF generation failed: " + e.getMessage());
        }
    }
}
