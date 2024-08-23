package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;
import br.gov.ce.sop.convenios.api.dto.filter.FilterCelebracaoAdministrativoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAdministracao;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoAdministracaoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoAdministracaoService;
import br.gov.ce.sop.convenios.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.gov.ce.sop.convenios.utils.MaskUtils.cpfMask;

@Service
@RequiredArgsConstructor
public class VoCelebracaoAdministrativoServiceImpl implements VoCelebracaoAdministracaoService {

    private final VoCelebracaoAdministracaoRepository repository;

    @Override
    public List<VoCelebracaoAdministracao> getEntities(FilterCelebracaoAdministrativoDTO filtro) {
        return null;
    }

    @Override
    public List<VoCelebracaoAdministracao> getAll() {
        return null;
    }

    @Override
    public VoCelebracaoAdministracao getEntityById(Integer id) {
        return repository.findById(id).map(vo -> {
            vo.setCpfPrefeito(cpfMask(vo.getCpfPrefeito(), '*'));
            return vo;
        }).orElse(null);
    }

    @Override
    public PostResponseDTO<VoCelebracaoAdministracao> getPage(PostRequestDTO<FilterCelebracaoAdministrativoDTO> postRequestDTO) {
        FilterCelebracaoAdministrativoDTO filters = postRequestDTO.getFilters() != null
                ? postRequestDTO.getFilters()
                : new FilterCelebracaoAdministrativoDTO();
        filters.setStatus(filters.getStatus().isEmpty() ? null : filters.getStatus());
        Page<VoCelebracaoAdministracao> page = repository.findAllByQuery(filters, PaginationUtils.applyPagination(postRequestDTO));
        page.map(item -> {
            if(item != null) item.setCpfPrefeito(cpfMask(item.getCpfPrefeito(), '*'));
            return item;
        });
        return new PostResponseDTO<>(page);
    }
}
