package br.gov.ce.sop.convenios.webClient;

import java.util.List;
import java.util.function.Supplier;

public interface RequestService {
    //retorna uma lista de objetoConvenio
    <T> List<T> requestToListModel(Class<T> model, Supplier<Request> request);

    //retorna um único objetoConvenio
    <T> T requestToUniqueModel(Class<T> model, Supplier<Request> request);
    void requestWithoutReturn(Supplier<Request> requestSupplier);
}
