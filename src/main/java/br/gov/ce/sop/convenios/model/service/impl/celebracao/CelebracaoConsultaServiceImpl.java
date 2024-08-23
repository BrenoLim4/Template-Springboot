package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;
import br.gov.ce.sop.convenios.api.dto.filter.FilterConsultaCelebracaoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.HistoricoCelebracao;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsulta;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoConsultaAnalisesRepository;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoConsultaRepository;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoDocumentosGeralDetalhesRepository;
import br.gov.ce.sop.convenios.model.repository.mapp.VoMappDetalheRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.CelebracaoConsultaService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.HistoricoCelebracaoService;
import br.gov.ce.sop.convenios.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.gov.ce.sop.convenios.utils.MaskUtils.cpfMask;
import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
@Log
public class CelebracaoConsultaServiceImpl implements CelebracaoConsultaService {

    private final VoCelebracaoConsultaRepository repository;
    private final VoCelebracaoConsultaAnalisesRepository analisesRepository;
    private final HistoricoCelebracaoService historicoCelebracaoService;
    private final VoMappDetalheRepository mappRepository;
    private final VoCelebracaoDocumentosGeralDetalhesRepository documentosGeralDetalhesRepository;

    @Override
    public List<VoCelebracaoConsulta> getEntities(FilterConsultaCelebracaoDTO filtro) {
        return List.of();
    }

    @Override
    public List<VoCelebracaoConsulta> getAll() {
        return List.of();
    }

    @Override
    public VoCelebracaoConsulta getEntityById(Integer id) {
        VoCelebracaoConsulta voCelebracaoConsulta = repository.findById(id).orElseThrow(() -> new RuntimeException("Nenhum registro encontrado com o id " + id));

        voCelebracaoConsulta.setCpfPrefeito(cpfMask(voCelebracaoConsulta.getCpfPrefeito(), '*'));
        voCelebracaoConsulta.setAnalises(analisesRepository.findAllByIdCelebracao(id).stream().sorted((c1, c2) -> c2.getAtual().compareTo(c1.getAtual())).toList());
        voCelebracaoConsulta.setHistoricos(historicoCelebracaoService.getEntities(HistoricoCelebracao.builder().idCelebracao(id).build()).stream().sorted(comparing(HistoricoCelebracao::getDataHora)).toList());
        voCelebracaoConsulta.setMappsDetalhes(mappRepository.findAllByIdCelebracao(id));
        voCelebracaoConsulta.setDocumentos(documentosGeralDetalhesRepository.findAllByIdCelebracao(id));
//        voCelebracaoConsulta.getAnalises().sort((c1, c2) -> c2.getAtual().compareTo(c1.getAtual()));
//        voCelebracaoConsulta.getHistoricos().sort(comparing(HistoricoCelebracao::getDataHora));
        return voCelebracaoConsulta;
    }

    @Override
    public PostResponseDTO<VoCelebracaoConsulta> getPage(PostRequestDTO<FilterConsultaCelebracaoDTO> postRequestDTO) {
        FilterConsultaCelebracaoDTO filter = postRequestDTO.getFilters() != null ? postRequestDTO.getFilters() : FilterConsultaCelebracaoDTO.builder().build();
        Page<VoCelebracaoConsulta> page = repository.findAllByQuery(filter, PaginationUtils.applyPagination(postRequestDTO));
        page.map(item -> {
            if(item != null) item.setCpfPrefeito(cpfMask(item.getCpfPrefeito(), '*'));
            return item;
        });
        return new PostResponseDTO<>(page);
    }
}
