package br.gov.ce.sop.convenios.api.exception;

public class DocumentoNotFount extends RuntimeException{

    private static final String MSG = "Documento não encontrado ";

    public DocumentoNotFount(Integer idDocumento) {
        super(MSG + idDocumento);
    }

    public DocumentoNotFount() {
        super(MSG);
    }
}
