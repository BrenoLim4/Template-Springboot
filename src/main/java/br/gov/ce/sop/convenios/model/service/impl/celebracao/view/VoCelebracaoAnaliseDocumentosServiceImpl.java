package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.CelebracaoAnaliseDocumentosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAnaliseDocumentos;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoAnaliseDocumentosRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoAnaliseDocumentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoCelebracaoAnaliseDocumentosServiceImpl implements VoCelebracaoAnaliseDocumentosService {

    private final VoCelebracaoAnaliseDocumentosRepository repository;
    @Override
    public List<CelebracaoAnaliseDocumentosDTO> findDocumentosParaAnalise(Integer idCelebracao, Integer idTipoAnalise) {
        List<VoCelebracaoAnaliseDocumentos> documentosConferencia = repository.findAllByIdEntidadeAndIdTipoAnalise(idCelebracao, idTipoAnalise);

        return new ArrayList<>(montarDocumentosConferencia(documentosConferencia));
    }

    private List<CelebracaoAnaliseDocumentosDTO> montarDocumentosConferencia(List<VoCelebracaoAnaliseDocumentos> docsConf){
        ArrayList<CelebracaoAnaliseDocumentosDTO> documentos = new ArrayList<>();

        docsConf.stream().map(doc -> {
            CelebracaoAnaliseDocumentosDTO celebracaoAnaliseDocumentosDTO = new CelebracaoAnaliseDocumentosDTO();
            BeanUtils.copyProperties(doc, celebracaoAnaliseDocumentosDTO);
            return celebracaoAnaliseDocumentosDTO;
        }).forEach(documentos::add);

        return documentos;
    }

    @Override
    public List<VoCelebracaoAnaliseDocumentos> getEntities(VoCelebracaoAnaliseDocumentos filtro) {
        return null;
    }

    @Override
    public List<VoCelebracaoAnaliseDocumentos> getAll() {
        return null;
    }

    @Override
    public VoCelebracaoAnaliseDocumentos getEntityById(Integer integer) {
        return null;
    }
}
