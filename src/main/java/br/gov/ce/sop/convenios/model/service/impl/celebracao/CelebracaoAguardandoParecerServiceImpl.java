package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;
import br.gov.ce.sop.convenios.api.dto.filter.FilterCelebracaoAguardandoParecerDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAguardandoParecer;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAguardandoParecerDocumentos;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoAguardandoParecerDocumentosRepository;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoAguardandoParecerRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.CelebracaoAguardandoParecerService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import br.gov.ce.sop.convenios.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.gov.ce.sop.convenios.utils.MaskUtils.cpfMask;

@Service
@RequiredArgsConstructor
public class CelebracaoAguardandoParecerServiceImpl implements CelebracaoAguardandoParecerService {
    private final VoCelebracaoAguardandoParecerRepository repository;
    private final VoCelebracaoAguardandoParecerDocumentosRepository celebracaoAguardandoParecerDocumentosRepository;
    @Override
    public List<VoCelebracaoAguardandoParecer> getEntities(FilterCelebracaoAguardandoParecerDTO filtro) {
        return null;
    }

    @Override
    public List<VoCelebracaoAguardandoParecer> getAll() {
        return repository.findAll();
    }

    @Override
    public VoCelebracaoAguardandoParecer getEntityById(Integer id) {
        return repository.findById(id).map(vo -> {
            vo.setCpfPrefeito(cpfMask(vo.getCpfPrefeito(), '*'));
            return vo;
        }).orElse(null);
    }

    @Override
    public PostResponseDTO<VoCelebracaoAguardandoParecer> getPage(PostRequestDTO<FilterCelebracaoAguardandoParecerDTO> postRequestDTO) {
        FilterCelebracaoAguardandoParecerDTO filters = postRequestDTO.getFilters() != null
                ? postRequestDTO.getFilters()
                : new FilterCelebracaoAguardandoParecerDTO();
        if(TokenService.isConvenente()){
            filters.setCnpjConvenente(TokenService.getTokenUsername());
        }
        Page<VoCelebracaoAguardandoParecer> page = repository.findAllByQuery(filters, PaginationUtils.applyPagination(postRequestDTO));
        page.map(item -> {
            if(item != null) item.setCpfPrefeito(cpfMask(item.getCpfPrefeito(), '*'));
            return item;
        });
        return new PostResponseDTO<>(page);
    }

    @Override
    public List<VoCelebracaoAguardandoParecerDocumentos> findAllDocumentsByIdCelebracao(Integer idCelebracao) {
        return celebracaoAguardandoParecerDocumentosRepository.findAllByIdEntidade(idCelebracao);
    }
}
