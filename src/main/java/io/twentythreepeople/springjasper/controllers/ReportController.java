package io.twentythreepeople.springjasper.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@RestController
@RequestMapping("/report")
public class ReportController {

  @GetMapping("/hello")
  public String helloWorld() {
    return "hello world";
  }

  @GetMapping("/pdf")
  public void pdf(HttpServletResponse response) throws JRException, IOException {
    InputStream jasperStream = this.getClass().getResourceAsStream("/reports/report-example.jasper");
    Map<String, Object> params = new HashMap<>();
    params.put("Title", "Hello wOrLd");

    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

    response.setContentType("application/x-pdf");
    response.setHeader("Content-disposition", "inline; filename=helloWorldReport.pdf");

    final OutputStream outStream = response.getOutputStream();
    JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
  }
  
}
