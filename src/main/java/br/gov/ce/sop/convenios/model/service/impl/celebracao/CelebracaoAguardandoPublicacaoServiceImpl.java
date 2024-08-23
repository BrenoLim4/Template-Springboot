package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;
import br.gov.ce.sop.convenios.api.dto.filter.FilterCelebracaoAguardandoPublicacaoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAguardandoPublicacao;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoAguardandoPublicacaoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.CelebracaoAguardandoPublicacaoService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import br.gov.ce.sop.convenios.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.gov.ce.sop.convenios.utils.MaskUtils.cpfMask;

@Service
@RequiredArgsConstructor
public class CelebracaoAguardandoPublicacaoServiceImpl implements CelebracaoAguardandoPublicacaoService {
    private final VoCelebracaoAguardandoPublicacaoRepository repository;
    @Override
    public List<VoCelebracaoAguardandoPublicacao> getEntities(FilterCelebracaoAguardandoPublicacaoDTO filtro) {
        return null;
    }

    @Override
    public List<VoCelebracaoAguardandoPublicacao> getAll() {
        return repository.findAll();
    }

    @Override
    public VoCelebracaoAguardandoPublicacao getEntityById(Integer id) {
        return repository.findById(id).map(vo -> {
            vo.setCpfPrefeito(cpfMask(vo.getCpfPrefeito(), '*'));
            return vo;
        }).orElse(null);
    }

    @Override
    public PostResponseDTO<VoCelebracaoAguardandoPublicacao> getPage(PostRequestDTO<FilterCelebracaoAguardandoPublicacaoDTO> postRequestDTO) {
        FilterCelebracaoAguardandoPublicacaoDTO filters = postRequestDTO.getFilters() != null
                ? postRequestDTO.getFilters()
                : new FilterCelebracaoAguardandoPublicacaoDTO();
        if(TokenService.isConvenente()){
            filters.setCnpjConvenente(TokenService.getTokenUsername());
        }
        Page<VoCelebracaoAguardandoPublicacao> page = repository.findAllByQuery(filters, PaginationUtils.applyPagination(postRequestDTO));
        page.map(item -> {
            if(item != null) item.setCpfPrefeito(cpfMask(item.getCpfPrefeito(), '*'));
            return item;
        });
        return new PostResponseDTO<>(page);
    }
}
