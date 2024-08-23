package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;
import br.gov.ce.sop.convenios.api.dto.filter.FilterDadosPrefeitoDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDadosPrefeito;
import br.gov.ce.sop.convenios.model.repository.convenio.prefeitura.VoDadosPrefeitoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.VoDadosPrefeitoService;
import br.gov.ce.sop.convenios.utils.MaskUtils;
import br.gov.ce.sop.convenios.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoDadosPrefeitoServiceImpl implements VoDadosPrefeitoService {
    private final VoDadosPrefeitoRepository repository;

    @Override
    public List<VoDadosPrefeito> getEntities(FilterDadosPrefeitoDTO filtro) {
        return null;
    }

    @Override
    public List<VoDadosPrefeito> getAll() {
        return null;
    }

    @Override
    public VoDadosPrefeito getEntityById(Integer id) {
        return null;
    }

    @Override
    public PostResponseDTO<VoDadosPrefeito> getPage(PostRequestDTO<FilterDadosPrefeitoDTO> postRequestDTO) {
        FilterDadosPrefeitoDTO filters =postRequestDTO.getFilters() != null ? postRequestDTO.getFilters() : FilterDadosPrefeitoDTO.builder().build();
        Page<VoDadosPrefeito> page = repository.findAllByQuery(filters, PaginationUtils.applyPagination(postRequestDTO));
        page.map(vo -> {
            vo.setCpf(MaskUtils.cpfMask(vo.getCpf(), '*'));
            return vo;
        });
        return new PostResponseDTO<>(page);
    }
}
