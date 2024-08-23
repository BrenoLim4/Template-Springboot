package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.CelebracaoReprovadoDocumentosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoReprovadoDocumentos;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoReprovadoDocumentosRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoReprovadoDocumentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoCelebracaoReprovadoDocumentosServiceImpl implements VoCelebracaoReprovadoDocumentosService {
    private final VoCelebracaoReprovadoDocumentosRepository repository;
    @Override
    public List<CelebracaoReprovadoDocumentosDTO> findDocumentosReprovado(Integer idCelebracao) {
        List<VoCelebracaoReprovadoDocumentos> documentosConferencia = repository.findAllByIdCelebracao(idCelebracao);

        return new ArrayList<>(montarDocumentosReprovados(documentosConferencia));
    }

    private List<CelebracaoReprovadoDocumentosDTO> montarDocumentosReprovados(List<VoCelebracaoReprovadoDocumentos> docsConf){
        ArrayList<CelebracaoReprovadoDocumentosDTO> documentos = new ArrayList<>();

        docsConf.stream().map(doc -> {
            CelebracaoReprovadoDocumentosDTO celebracaoReprovadoDocumentosDTO = new CelebracaoReprovadoDocumentosDTO();
            BeanUtils.copyProperties(doc, celebracaoReprovadoDocumentosDTO);
            return celebracaoReprovadoDocumentosDTO;
        }).forEach(documentos::add);

        return documentos;
    }

    @Override
    public List<VoCelebracaoReprovadoDocumentos> getEntities(VoCelebracaoReprovadoDocumentos filtro) {
        return null;
    }

    @Override
    public List<VoCelebracaoReprovadoDocumentos> getAll() {
        return null;
    }

    @Override
    public VoCelebracaoReprovadoDocumentos getEntityById(Integer integer) {
        return null;
    }
}
