package br.gov.ce.sop.convenios.model.service.impl;

import br.gov.ce.sop.convenios.api.exception.ValidarProtocoloException;
import br.gov.ce.sop.convenios.model.repository.convenio.ConvenioRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.GerencialService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.CelebracaoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GerencialServiceImpl implements GerencialService {
    CelebracaoService celebracaoService;
    ConvenioRepository convenioRepository;

    @Override
    public void protocolar(String nrProtocolo, LocalDateTime dataHoraProcesso, Object idEntidade, Integer idTipoEntidade) {
//        CELEBRACAO(410),
//        PROCESSO_REPASSE(442),
//        ADITIVO(420);
        List<String> errors = new ArrayList<>();
        if (dataHoraProcesso.isAfter(LocalDateTime.now(ZoneId.of("GMT")))) {
            errors.add("Data do Protocolo não pode ser maior que a data atual.");
        }
        if(!nrProtocolo.startsWith("43022")){
            errors.add("O protocolo deve começar com 43022, que é o código da SOP.");
        }

        String nrProtocoloNormalizado = "";
        try {
             nrProtocoloNormalizado = convenioRepository.validarProtocolo(nrProtocolo);
        } catch (Exception ex) {
            errors.add(translateException(ex));
        }
        if (!errors.isEmpty()) {
            throw new ValidarProtocoloException(errors);
        }
        switch (idTipoEntidade){
            case 410:
                celebracaoService.protocolar(nrProtocoloNormalizado, dataHoraProcesso, idEntidade);
                break;
            case 442:
                break;
            case 420:
                break;
            default:
        }
    }

    private String translateException(Exception ex) {
        SQLException sqlException = findSQLException(ex);
        return sqlException.getMessage();
    }

    private SQLException findSQLException(Throwable ex) {
        if (ex instanceof SQLException) {
            return (SQLException) ex;
        } else if (ex.getCause() != null) {
            return findSQLException(ex.getCause());
        } else {
            throw new IllegalArgumentException("Não foi possível encontrar a exceção SQL.");
        }
    }
}
