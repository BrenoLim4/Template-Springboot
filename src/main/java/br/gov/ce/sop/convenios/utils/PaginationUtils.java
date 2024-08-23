package br.gov.ce.sop.convenios.utils;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.SortColumn;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaginationUtils {

    public static Sort sortBuild(List<SortColumn> properties) {
        List<Sort.Order> orders = new ArrayList<>();
        // TODO: Adicionar o ignoreCase, mas checar antes para não usar quando a propriedade for temporal
        properties.forEach(property -> orders.add(
                Sort.Order.by(property.getProperty()).with(Sort.Direction.fromString(property.getDirection()))));
        return Sort.by(orders);
    }

    public static <T> Pageable applyPagination(PostRequestDTO<T> postRequestDTO) {
        return PageRequest.of(
                Optional.of(postRequestDTO.getPage() - 1).orElse(0),
                Optional.ofNullable(postRequestDTO.getSize()).orElse(30),
                Optional.of(sortBuild(postRequestDTO.getSortColumns())).orElse(Sort.unsorted()));
    }
}
