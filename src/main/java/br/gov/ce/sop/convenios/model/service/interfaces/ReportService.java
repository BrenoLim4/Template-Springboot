package br.gov.ce.sop.convenios.model.service.interfaces;

import br.gov.ce.sop.convenios.api.exception.ReportException;

import java.util.List;
import java.util.Map;

public interface ReportService {

    byte[] getDefaultReportPdfBytes(final JasperPrint jasperPrint) throws ReportException;

    byte[] getDefaultReportXlsxBytes(final JasperPrint jasperPrint) throws ReportException;

    byte[] generateDefaultReportPdf(List<?> list, Object visibleFields, String title, String filename) throws ReportException;

    byte[] generateDefaultReportXlsx(List<?> list, Object visibleFields, String title, String filename) throws ReportException;

    byte[] generateCustomReportPdf(String customTemplateName, Map<String, Object> params, JRDataSource dataSource) throws ReportException;

    byte[] generateCustomReportXlsx(String customTemplateName, Map<String, Object> params, JRDataSource dataSource) throws ReportException;
}
