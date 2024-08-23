package br.gov.ce.sop.convenios.model.service.impl.mapp;

import br.gov.ce.sop.convenios.api.exception.MappInvalidException;
import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.entity.convenio.ConvenioXMapp;
import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import br.gov.ce.sop.convenios.model.entity.mapp.VoDocumentosEspelhoMapp;
import br.gov.ce.sop.convenios.model.entity.mapp.VoMappDetalhe;
import br.gov.ce.sop.convenios.model.repository.mapp.MappRepository;
import br.gov.ce.sop.convenios.model.repository.mapp.VoDocumentosEspelhoMappRepository;
import br.gov.ce.sop.convenios.model.repository.mapp.VoMappDetalheRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.ConvenioXMappService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.HistoricoConvenioService;
import br.gov.ce.sop.convenios.model.service.interfaces.documentodigital.DocumentoDigitalService;
import br.gov.ce.sop.convenios.model.service.interfaces.mapp.MappService;
import br.gov.ce.sop.convenios.webClient.WebClientUnexpectedException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log
public class MappServiceImpl implements MappService {
    private final MappRepository repository;
    private final ConvenioXMappService convenioXMappService;
    private final VoDocumentosEspelhoMappRepository voDocumentosEspelhoMappRepository;
    private final DocumentoDigitalService documentoDigitalService;
    private final VoMappDetalheRepository voMappDetalheRepository;
    private final HistoricoConvenioService historicoConvenioService;

    @Override
    public List<Mapp> getEntities(Mapp filtro) {
        return null;
    }

    @Override
    public List<Mapp> getAll() {
        return null;
    }

    @Override
    public Mapp getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Mapp> buscarMapps(String codigoMapp) {
        return repository.findAllByQuery(codigoMapp);
    }

    @Override
    public List<VoDocumentosEspelhoMapp> buscarMappsPorIdConvenio(Integer id) {
        return voDocumentosEspelhoMappRepository.findAllByIdConvenio(id);
    }

    @Override
    public boolean possuiDocumentosAguardandoEnvio(Integer idConvenio) {
        return buscarMappsPorIdConvenio(idConvenio)
                .stream().anyMatch(doc -> !doc.getEnviado());
    }

    @Override
    @Transactional
    public String desassociarMappConvenio(Integer idMapp, Long idDocumento) {
        Optional<ConvenioXMapp> convenioXMappOptional = convenioXMappService.findConvenioXMappByidMapp(idMapp);
        ConvenioXMapp convenioXMapp = convenioXMappOptional.orElseThrow(() -> new RuntimeException("Mapp não associado ao convênio."));
        convenioXMappService.deleteByIdMapp(idMapp);

        if (Objects.nonNull(idDocumento)) {
            try {
                documentoDigitalService.deleteDocumento(idDocumento);
            } catch (WebClientUnexpectedException ex) {
                String msgError = "Erro ao tentar deletar documento na api de Documento Digital";
                log.severe(msgError + " - " + ex.getMessage());
                throw new RuntimeException(msgError);
            }
        }
        historicoConvenioService.gravarHistoricoMappDesassociado(convenioXMapp);
        return String.format("Mapp %s desassociado com sucesso do convênio", convenioXMapp.getMapp().getCodigoMapp());
    }

    @Override
    public void saveMappsCovenio(List<Integer> idMapps, Convenio convenio) {
        verificarValidadeMapp(idMapps);
        idMapps.stream()
                .map(id -> {
                    Mapp mapp = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Mapp %d não encontrado.", id)));
                    return new ConvenioXMapp(convenio, mapp);
                })
                .forEach(convenioXMappService::salvarConvXMapp);
    }

    @Override
    public void verificarValidadeMapp(List<Integer> idMapps) {
        if (idMapps.stream().anyMatch(convenioXMappService::existsByMapp))
            throw new MappInvalidException("Mapp já encontra-se associado a um convênio!");

    }

    @Override
    public List<VoMappDetalhe> buscarMappsByIdCelebracao(Integer idCelebracao) {
        return voMappDetalheRepository.findAllByIdCelebracao(idCelebracao);
    }

    @Override
    public String buscarMappsConcatenadaosPorIdCelebracao(Integer idCelebracao) {
        return buscarMappsByIdCelebracao(idCelebracao)
                .stream()
                .map(VoMappDetalhe::getCodigoMapp)
                .collect(Collectors.joining(", ", "(", ")"));
    }

}
