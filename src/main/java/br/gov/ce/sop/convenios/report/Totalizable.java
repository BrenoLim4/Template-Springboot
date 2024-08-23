package br.gov.ce.sop.convenios.report;

import java.util.List;
import java.util.Optional;

public interface Totalizable<T> {

    Optional<T> totalize(List<T> list);
}
