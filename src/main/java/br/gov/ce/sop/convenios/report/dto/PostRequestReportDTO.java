package br.gov.ce.sop.convenios.report.dto;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;

public record PostRequestReportDTO<F, VC>(
        PostRequestDTO<F> request,
        VC visibleColumns
) {
    public PostRequestReportDTO {
        request.setSize(Integer.MAX_VALUE);
        request.setPage(1);
    }
}
