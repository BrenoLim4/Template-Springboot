package br.gov.ce.sop.convenios.model.service.impl;

import br.gov.ce.sop.convenios.api.exception.ReportException;
import br.gov.ce.sop.convenios.model.service.interfaces.JRDataSource;
import br.gov.ce.sop.convenios.model.service.interfaces.JasperPrint;
import br.gov.ce.sop.convenios.model.service.interfaces.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Value("${path.report.dynamic-template}")
    private String pathReportDynamicTemplate;

    @Value("${path.report.logo-sop}")
    private String pathReportLogoSop;

    @Value("${path.report.logo-gov}")
    private String pathReportLogoGov;

    private final static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public byte[] getDefaultReportPdfBytes(JasperPrint jasperPrint) throws ReportException {
        return new byte[0];
    }

    @Override
    public byte[] getDefaultReportXlsxBytes(JasperPrint jasperPrint) throws ReportException {
        return new byte[0];
    }

    @Override
    public byte[] generateDefaultReportPdf(List<?> list, Object visibleFields, String title, String filename) throws ReportException {
        return new byte[0];
    }

    @Override
    public byte[] generateDefaultReportXlsx(List<?> list, Object visibleFields, String title, String filename) throws ReportException {
        return new byte[0];
    }


    @Override
    public byte[] generateCustomReportPdf(String customTemplateName, Map<String, Object> params, JRDataSource dataSource) throws ReportException {
        return new byte[0];
    }

    @Override
    public byte[] generateCustomReportXlsx(String customTemplateName, Map<String, Object> params, JRDataSource dataSource) throws ReportException {
        return new byte[0];
    }


}
