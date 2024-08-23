package br.gov.ce.sop.convenios.model.service.impl.documentodigital;

import br.gov.ce.sop.convenios.api.dto.ColaboradorDTO;
import br.gov.ce.sop.convenios.api.dto.HashDocumentoDigitalDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.RParamsAnexarAssinatura;
import br.gov.ce.sop.convenios.api.dto.parametros.RParamsHashDocumentoDigital;
import br.gov.ce.sop.convenios.api.dto.parametros.RParamsRejeitarDocumento;
import br.gov.ce.sop.convenios.api.dto.parametros.RParamsUploadDocumento;
import br.gov.ce.sop.convenios.api.exception.PontoControleNaoValidadoException;
import br.gov.ce.sop.convenios.api.exception.ValidarCertificadoException;
import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.model.dto.DocumentosDigitais;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoDocumentosGeral;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoHistoricoDocumento;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoListaDocumentosDigitaisAssinadores;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeito;
import br.gov.ce.sop.convenios.model.enums.TipoColaborador;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoHistoricoDocumentoRepository;
import br.gov.ce.sop.convenios.model.service.impl.celebracao.view.VoCelebracaoDocumentosGeralServiceImpl;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoListaDocumentosDigitaisAssinadoresService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.ColaboradorService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeitoService;
import br.gov.ce.sop.convenios.model.service.interfaces.documentodigital.DocumentoDigitalService;
import br.gov.ce.sop.convenios.model.service.interfaces.documentodigital.TipoDocumentoXTipoAssinadorService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import br.gov.ce.sop.convenios.utils.PAdESChecker;
import br.gov.ce.sop.convenios.utils.PdfAssinaturaUtils;
import br.gov.ce.sop.convenios.utils.SignaturePosition;
import br.gov.ce.sop.convenios.webClient.Request;
import br.gov.ce.sop.convenios.webClient.RequestService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.ExternalSigningSupport;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.Store;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static br.gov.ce.sop.convenios.model.enums.TipoAssinador.*;
import static br.gov.ce.sop.convenios.model.enums.TipoColaborador.ENGENHEIRO;
import static br.gov.ce.sop.convenios.model.enums.TipoColaborador.PREFEITO;
import static br.gov.ce.sop.convenios.utils.PdfAssinaturaUtils.setSignaturePosition;
import static br.gov.ce.sop.convenios.utils.PdfAssinaturaUtils.setVisibleSignatureProperties;
import static br.gov.ce.sop.convenios.utils.PdfUtils.adicionarPaginaVazia;
import static java.util.Comparator.comparing;


@Service
@RequiredArgsConstructor
@Primary
public class DocumentoDigitalServiceImpl implements DocumentoDigitalService {

    private final RequestService requestService;
    private final PrefeitoService prefeitoService;
    private final ColaboradorService colaboradorService;
    private final TipoDocumentoXTipoAssinadorService tipoDocumentoXTipoAssinadorService;
    private final VoCelebracaoDocumentosGeralServiceImpl voCelebracaoDocumentosGeralServiceImpl;
    private final VoListaDocumentosDigitaisAssinadoresService voListaDocumentosDigitaisAssinadoresService;
    private final VoHistoricoDocumentoRepository voHistoricoDocumentoRepository;
    private final String REQUEST_MAPPING = "/doc-digital";
    private final String REQUEST_ASSINATURA_MAPPING = "/assinatura";
    public List<ColaboradorDTO> colaboradoresAssinatura = new ArrayList<>();

    @Override
    public DocumentosDigitais uploadDocumento(RParamsUploadDocumento params) {

       String assinatura = statusAssinaturaDocumento(Base64.getEncoder().encodeToString(params.arquivo()), params.idTipoDocumento());

       DocumentosDigitais documento = requestService.requestToUniqueModel(DocumentosDigitais.class, Request.builder()
                .method(HttpMethod.POST)
                .endPoint(REQUEST_MAPPING + "/upload")
                .bodyRequestObject(params)
                ::build);

        if (Objects.equals(assinatura, "SUCCESS")) {
            documento = adicionarAssinatura(documento.getId());
        }
       return documento;
    }

    @Override
    public byte[] downloadDocumento(Long id) {
        return requestService.requestToUniqueModel(byte[].class, Request.builder()
                .method(HttpMethod.GET)
                .endPoint(REQUEST_MAPPING + "/get-bytes/" + id.toString())::build);
    }

    @Override
    public DocumentosDigitais getDocumento(Long id) {
        return requestService.requestToUniqueModel(DocumentosDigitais.class, Request.builder()
                .method(HttpMethod.GET)
                .endPoint(REQUEST_MAPPING + "/" + id.toString())::build);
    }

    @Override
    public List<DocumentosDigitais> getDocumentos(DocumentosDigitais documentosDigitais) {
        return requestService.requestToListModel(DocumentosDigitais.class, Request.builder()
                .method(HttpMethod.POST)
                .endPoint(REQUEST_MAPPING + "/find-all")
                .bodyRequestObject(documentosDigitais)::build);
    }

    @Override
    public String deleteDocumento(Long idDocumento) {
        return requestService.requestToUniqueModel(String.class, Request.builder()
                .method(HttpMethod.DELETE)
                .endPoint(REQUEST_MAPPING + "/" + idDocumento)::build);
    }

    @Override
    public String conferirDocumento(Long id) {
        return requestService.requestToUniqueModel(String.class, () -> Request.builder()
                .method(HttpMethod.PUT)
                .endPoint(REQUEST_MAPPING + "/conferir/" + id)
                .build());
    }

    @Override
    public byte[] downloadDocumentoZip(Integer id, String nrProtocolo) throws IOException {
        List<VoCelebracaoDocumentosGeral> documentos = voCelebracaoDocumentosGeralServiceImpl.getEntities(VoCelebracaoDocumentosGeral.builder().idCelebracao(id).build());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream)) {
            for (VoCelebracaoDocumentosGeral documento : documentos) {
                if (documento.getIdDocumento() != null) {
                    byte[] documentoBytes = downloadDocumento(documento.getIdDocumento());
                    File file = new File( nrProtocolo + " - " + documento.getNomeArquivo());
                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    fileOutputStream.write(documentoBytes);
                    fileOutputStream.close();

                    zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                    FileInputStream fileInputStream = new FileInputStream(file);

                    org.apache.pdfbox.io.IOUtils.copy(fileInputStream, zipOutputStream);

                    fileInputStream.close();
                    zipOutputStream.closeEntry();
                    file.deleteOnExit();
                    if (!file.delete()) throw new RuntimeException("Erro ao gerar arquivo zip");
                }
            }

            zipOutputStream.finish();
            zipOutputStream.flush();
            org.apache.pdfbox.io.IOUtils.closeQuietly(zipOutputStream);
            org.apache.pdfbox.io.IOUtils.closeQuietly(bufferedOutputStream);
            org.apache.pdfbox.io.IOUtils.closeQuietly(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    @Override
    public String rejeitarDocumento(RParamsRejeitarDocumento params) {
        return requestService.requestToUniqueModel(String.class, () -> Request.builder()
                .method(HttpMethod.PUT)
                .endPoint(REQUEST_MAPPING + "/rejeitar")
                .bodyRequestObject(params)
                .build());
    }

    @Override
    public void checarValidadePontoControle(Integer idPontoEntidade) {
        Boolean validado = requestService.requestToUniqueModel(boolean.class, () -> Request.builder()
                .method(HttpMethod.GET)
                .endPoint("/ponto-controle/" + idPontoEntidade)
                .build());
        if (!validado) {
            throw new PontoControleNaoValidadoException();
        }
    }

    @Override
    public boolean conferenciaPendente(Integer idPontoEntidade) {
        return requestService.requestToUniqueModel(boolean.class, () -> Request.builder()
                .method(HttpMethod.GET)
                .endPoint("/ponto-controle/conferencia-pendente/" + idPontoEntidade)
                .build());
    }

    @Override
    public HashDocumentoDigitalDTO retornarHashOficio(String base64, Integer idTipoDocumento, String nomeSO, String matricula) throws Exception {
        String algoritmoHash = nomeSO.equals("WIN") ? "SHA-256" : "SHA-512";
        Integer idTipoAssinador = tipoDocumentoXTipoAssinadorService.findAllByIdTipoDocumento(idTipoDocumento).stream().findFirst().orElseThrow();
        Prefeito prefeito = prefeitoService.findPrefeitoAtivoByPrefeitura();

        String base64withSignPages = addPage(base64);
        PDDocument originalPDF = prepararDocumentoDigitalAssinatura(base64withSignPages, prefeito.getNome(), prefeito.getCpf(), fromValue(idTipoAssinador).name(), matricula, algoritmoHash);

        ExternalSigningSupport externalSigningSupport;
        String hashParaAssinarBase64;
        try {
            externalSigningSupport = originalPDF.saveIncrementalForExternalSigning(null);
            InputStream conteudoAssinar = externalSigningSupport.getContent();
            MessageDigest md = MessageDigest.getInstance(algoritmoHash);
            hashParaAssinarBase64 = Base64.getEncoder().encodeToString(md.digest(IOUtils.toByteArray(conteudoAssinar)));

        } catch (IOException | NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        }

        originalPDF.close();

        return HashDocumentoDigitalDTO.builder().documentoAntigo(base64withSignPages).hash(hashParaAssinarBase64).build();
    }

    @Override
    public byte[] anexarAssinaturaOficio(String assinatura, String base64, Integer idTipoDocumento, String nomeSO, String matricula) throws Exception {
        byte[] signatureBytes = Base64.getDecoder().decode(assinatura);
        String algoritmoHash = nomeSO.equals("WIN") ? "SHA-256" : "SHA-512";
        Integer idTipoAssinador = tipoDocumentoXTipoAssinadorService.findAllByIdTipoDocumento(idTipoDocumento).stream().findFirst().orElseThrow();
        ColaboradorDTO prefeito = colaboradorService.getCpfColaboradoresByPrefeituraAndTipoColaborador(PREFEITO.ordinal()).stream().findFirst().orElseThrow();

        PDDocument document = prepararDocumentoDigitalAssinatura(base64, prefeito.nome(), prefeito.cpf(), fromValue(idTipoAssinador).name(), matricula, algoritmoHash);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExternalSigningSupport externalSigningSupport = document.saveIncrementalForExternalSigning(outputStream);
        externalSigningSupport.setSignature(signatureBytes);
        document.saveIncremental(outputStream);

        byte[] signedPdfBytes = outputStream.toByteArray();

        document.close();
        outputStream.close();

        return validarArquivoAssinado(outputStream.toByteArray(), prefeito.cpf(), document) ? signedPdfBytes : null;
    }

    @Override
    public HashDocumentoDigitalDTO retornarHashDocumentoDigital(RParamsHashDocumentoDigital params) {
        HashMap<String, Object> bodyUploadAssinatura = new HashMap<>();
        bodyUploadAssinatura.put("idAssinador", params.idAssinador());
        bodyUploadAssinatura.put("idDocumento", params.idDocumento());
        bodyUploadAssinatura.put("idTipoAssinador", TipoColaborador.valueOf(params.idTipoColaborador()).tipoAssinador.idTipoAssinador);
        bodyUploadAssinatura.put("tipoAssinador", TipoColaborador.valueOf(params.idTipoColaborador()).tipoAssinador);
        bodyUploadAssinatura.put("nomeSistemaOperacional", params.nomeSistemaOperacional());
        return requestService.requestToUniqueModel(HashDocumentoDigitalDTO.class, () -> Request.builder()
                .method(HttpMethod.POST)
                .endPoint(REQUEST_ASSINATURA_MAPPING + "/gerar-hash")
                .bodyRequest(bodyUploadAssinatura)
                .build());
    }

    @Override
    public String anexarAssinaturaDocumento(RParamsAnexarAssinatura params) {
        HashMap<String, Object> bodyUploadAssinatura = new HashMap<>();
        bodyUploadAssinatura.put("assinatura", params.assinatura());
        bodyUploadAssinatura.put("idAssinador", params.idAssinador());
        bodyUploadAssinatura.put("idTipoAssinador", TipoColaborador.valueOf(params.idTipoColaborador()).tipoAssinador.idTipoAssinador);
        bodyUploadAssinatura.put("tipoAssinador", TipoColaborador.valueOf(params.idTipoColaborador()).tipoAssinador);
        bodyUploadAssinatura.put("idDocumento", params.idDocumento());
        bodyUploadAssinatura.put("nomeSistemaOperacional", params.nomeSistemaOperacional());
        return requestService.requestToUniqueModel(String.class, () -> Request.builder()
                .method(HttpMethod.POST)
                .endPoint(REQUEST_ASSINATURA_MAPPING + "/anexar-assinatura")
                .bodyRequest(bodyUploadAssinatura)
                .build());
    }

    @Override
    public String possuiAssinaturasValidas(String base64, Integer idTipoDocumento) {
        List<Integer> tipoAssinadores = tipoDocumentoXTipoAssinadorService.findAllByIdTipoDocumento(idTipoDocumento);
        String msg = null;
        if (tipoAssinadores.isEmpty()){
            msg = "SUCCESS:Documento não requer assinatura.";
            return msg;
        }
        colaboradoresAssinatura =  new ArrayList<>();
        for (Integer tipoAssinador: tipoAssinadores) {
            if (msg != null && Objects.equals(msg.split(":")[0], "WARNING")) {
                msg = this.possuiAssinaturas(base64, tipoAssinador);
                if (Objects.equals(msg.split(":")[0], "SUCCESS")) {
                    return "ERROR:O Documento não possui todas as assinaturas necessárias.";
                }
            } else if (msg != null && Objects.equals(msg.split(":")[0], "SUCCESS")) {
                msg = this.possuiAssinaturas(base64, tipoAssinador);
                if (Objects.equals(msg.split(":")[0], "WARNING")) {
                    return "ERROR:O Documento não possui todas as assinaturas necessárias.";
                }
            } else {
                msg = this.possuiAssinaturas(base64, tipoAssinador);
            }

            if (Objects.equals(msg.split(":")[0], "ERROR")) {
                return msg;
            }
        }

        return msg;
    }

    @Override
    public List<VoListaDocumentosDigitaisAssinadores> assinadoresDocumento(Long id) {
        return voListaDocumentosDigitaisAssinadoresService.findAllByIdDocumento(id);
    }

    @Override
    public List<VoHistoricoDocumento> getHistoricoDocumento(Long id) {
        return voHistoricoDocumentoRepository.findAllByIdDocumento(id.intValue()).stream().sorted(comparing(VoHistoricoDocumento::getDataHora)).toList();
    }

    private PDDocument prepararDocumentoDigitalAssinatura(String base64, String nomeAssinador, String cpfAssinador, String tipoAssinador, String cnpjEmpresa, String algoritmoHash) throws Exception {
        byte[] pdfBytes = Base64.getDecoder().decode(base64);
        PdfAssinaturaUtils pdfAssinaturaUtils = new PdfAssinaturaUtils();
        MessageDigest.getInstance(algoritmoHash);

        PDDocument document = PDDocument.load(pdfBytes);
        Long idDoc = 1L;
        document.setDocumentId(idDoc);

        SignaturePosition signaturePosition;
        try {
            signaturePosition = setSignaturePosition(document);
        } catch (NumberFormatException e) {
            throw new Exception("Erro ao tentar posicionar selo " + e.getMessage(), e);
        }
        PDVisibleSignDesigner visibleSignDesigner = pdfAssinaturaUtils.addImage(document, signaturePosition, cnpjEmpresa, nomeAssinador, cpfAssinador, tipoAssinador, false);

        PDSignature signature = new PDSignature();
        signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        signature.setSignDate(cal);

        PDVisibleSigProperties visibleSignatureProperties = setVisibleSignatureProperties(visibleSignDesigner, nomeAssinador, "Brasil", "Plataforma de Assinaturas do Serpro", 0, signaturePosition.getActualPage(), true);
        if (visibleSignatureProperties != null) {
            try {
                visibleSignatureProperties.buildSignature();
                signature.setName(visibleSignatureProperties.getSignerName());
                signature.setLocation(visibleSignatureProperties.getSignerLocation());
                signature.setReason(visibleSignatureProperties.getSignatureReason());
            } catch (IOException e) {
                throw new Exception("Erro ao adicionar as informações visiveis da Assinatura:" + e.getMessage());
            }
        }

        if (visibleSignatureProperties != null && visibleSignatureProperties.isVisualSignEnabled()) {
            SignatureOptions signatureOptions = new SignatureOptions();
            try {
                signatureOptions.setVisualSignature(visibleSignatureProperties.getVisibleSignature());
                signatureOptions.setPage(visibleSignatureProperties.getPage());
                document.addSignature(signature, signatureOptions);

                return document;
            } catch (IOException e) {
                throw new Exception("Erro ao incluir Assinatura no documento: " + e.getMessage());
            }
        } else {
            try {
                document.addSignature(signature);
                return document;
            } catch (IOException e) {
                throw new Exception("Erro ao incluir Assinatura no documento: " + e.getMessage());
            }
        }
    }

    private String addPage(String base64) throws Exception {
        byte[] pdfBytes = Base64.getDecoder().decode(base64);

        PDDocument document = PDDocument.load(pdfBytes);
        if (document.isEncrypted()) {
            try {
                document.setAllSecurityToBeRemoved(true);
            } catch (Exception e) {
                document.close();
                throw new Exception("O documento está protegido com senha.", e);
            }
        }

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if (document.getSignatureDictionaries().isEmpty()) {
                document.addPage(adicionarPaginaVazia(document, "Página de Assinatura"));
                document.save(outputStream);
                document.close();

                return Base64.getEncoder().encodeToString(outputStream.toByteArray());
            } else {
//                document.save(outputStream);//                outputStream.write(pdfBytes);//                outputStream.flush();//                outputStream.close();
                throw new ValidarCertificadoException("Documento já se encontra assinado, por favor inserir um documento sem assinaturas.");
            }
        } catch (IOException e1) {
            throw new Exception("Erro incluir pagina para assinaturas: " + e1.getMessage());
        }
    }

    private String statusAssinaturaDocumento(String base64, Integer idTipoDocumento) {
        List<Integer> idTipoAssinadores = tipoDocumentoXTipoAssinadorService.findAllByIdTipoDocumento(idTipoDocumento);
        String assinatura = null;
        this.colaboradoresAssinatura =  new ArrayList<>();
        for (Integer idTipoAssinador : idTipoAssinadores ) {
            if (assinatura != null && Objects.equals(assinatura.split(":")[0], "WARNING")) {
                break;
            } else if (assinatura != null && Objects.equals(assinatura.split(":")[0], "SUCCESS")) {
                assinatura = this.possuiAssinaturas(base64, idTipoAssinador);
                if (Objects.equals(assinatura, "ERROR")) {
                    throw new WarningException("O Documento não possui todas as assinaturas necessárias.");
                }
            } else {
                assinatura = this.possuiAssinaturas(base64, idTipoAssinador);
            }

            if (assinatura != null && Objects.equals(assinatura.split(":")[0], "ERROR")) {
                throw new RuntimeException(assinatura.split(":")[1]);
            }
        }
        return assinatura != null ? assinatura.split(":")[0] : null;
    }

    private DocumentosDigitais adicionarAssinatura(Long idDocumento) {
        HashMap<String, Object> bodyUploadAssinatura = new HashMap<>();
        bodyUploadAssinatura.put("idAssinador", "");
        bodyUploadAssinatura.put("idTipoAssinador", "");
        bodyUploadAssinatura.put("idDocumento", idDocumento);
        DocumentosDigitais documento = null;
        for ( ColaboradorDTO colaboradorAssinatura : this.colaboradoresAssinatura ) {
            bodyUploadAssinatura.replace("idAssinador", colaboradorAssinatura.id());
            bodyUploadAssinatura.replace("idTipoAssinador", TipoColaborador.valueOf(colaboradorAssinatura.tipoColaborador()).tipoAssinador.idTipoAssinador);
            documento = requestService.requestToUniqueModel(DocumentosDigitais.class, () -> Request.builder()
                .method(HttpMethod.POST)
                .endPoint(REQUEST_ASSINATURA_MAPPING + "/set-assinado")
                .bodyRequest(bodyUploadAssinatura)
                .build());
        }

        return documento;
    }

    private String possuiAssinaturas(String base64, Integer tipoAssinador) {
        byte[] pdfBytes = Base64.getDecoder().decode(base64);
        boolean assinado = false;
        String status = "";

        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            List<PDSignature> signatures = document.getSignatureDictionaries();

            for (PDSignature signature : signatures) {
                assinado = true;
                byte[] signatureBytes = signature.getContents(pdfBytes);
                CMSSignedData signedData = new CMSSignedData(signatureBytes);

                List<ColaboradorDTO> colaboradores;
                if (Objects.equals(tipoAssinador, CONVENENTE.idTipoAssinador)) {
                    colaboradores = colaboradorService.getCpfColaboradoresByPrefeituraAndTipoColaborador(PREFEITO.ordinal());
                } else if (Objects.equals(tipoAssinador, RESPONSAVEL_TECNICO.idTipoAssinador)){
                    colaboradores = colaboradorService.getCpfColaboradoresByPrefeituraAndTipoColaborador(ENGENHEIRO.ordinal());
                } else {
                    return "ERROR:Cadastre um colaborador válido para assinar o documento na página 'Cadastrar Colaborador'. " +
                            "Não foi possível validar a assinatura.";
                }

                String cpfAssinador = "";
                for (ColaboradorDTO colaborador: colaboradores) {
                    if (Objects.equals(cpfAssinador, "")) {
                        cpfAssinador = validarAssinaturaAtual(pdfBytes, colaborador.cpf(), signature);
                        this.colaboradoresAssinatura.add(colaborador);
                    } else {
                        break;
                    }
                }

                X509Certificate certificate = extractCertificate(signedData);
                if (!Objects.equals(cpfAssinador, "") && certificate != null && isICPBrasilIssuer(certificate)) {
                    if (!isSignatureExpired(signature, certificate)) {
                        status = "SUCCESS:Assinatura digital válida detectada.";
                    } else {
                        status = "ERROR:Assinatura digital expirada.";
                    }
                }
            }

            if (assinado && status.isEmpty()) {
                status = "ERROR:Não foi possível encontrar nenhuma assinatura qualificada válida que possuísse as credenciais corretas.";
            }

            if (!assinado) {
                status = "WARNING:Documento não está assinado.";
            }

            return status;
        } catch (IOException | CMSException e) {
            if (e.getCause() instanceof NumberFormatException) throw new RuntimeException("Documento sem assinatura reconhecível ou com assinatura corrompida", e);
            throw new RuntimeException("Erro ao processar o documento PDF", e);
        }
    }

    private String validarAssinaturaAtual(byte[] documentoDigital, String cpfAssinador, PDSignature signature) throws ValidarCertificadoException, IOException  {
        boolean retorno;
        List<SignatureInformations> results = new ArrayList<>();
        try {
            getSignaturesInfo(documentoDigital, signature, results);

            retorno = validarSignInformation(cpfAssinador, results);
        } catch (ValidarCertificadoException ex) {
            throw new ValidarCertificadoException(ex.getMessage());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return retorno ? cpfAssinador : "";
    }

    private boolean validarArquivoAssinado(byte[] documentoDigital, String cpfAssinador, PDDocument document) throws ValidarCertificadoException {
        List<SignatureInformations> results = new ArrayList<>();
        try {
            SortedMap<Integer, PDSignature> sortedMap = new TreeMap<>();
            for (PDSignature signature : document.getSignatureDictionaries()) {
                int sigOffset = signature.getByteRange()[1];
                sortedMap.put(sigOffset, signature);
            }
            if (!sortedMap.isEmpty()) {
                PDSignature lastSignature = sortedMap.get(sortedMap.lastKey());
                getSignaturesInfo(documentoDigital, lastSignature, results);
            }

            boolean retorno = validarSignInformation(cpfAssinador, results);
            if( !retorno ) throw new ValidarCertificadoException("Certificado não pertence ao usuário logado!");

        } catch (ValidarCertificadoException ex) {
            throw new ValidarCertificadoException(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }

    private  void getSignaturesInfo(byte[] documentoDigital, PDSignature signature, List<SignatureInformations> results) throws IOException {
        List<SignatureInformations> result;
        COSBase type = signature.getCOSObject().getItem(COSName.TYPE);
        if (type.equals(COSName.SIG) || type.equals(COSName.DOC_TIME_STAMP)) {
            COSDictionary sigDict = signature.getCOSObject();
            COSString contents = (COSString) sigDict.getDictionaryObject(COSName.CONTENTS);

            byte[] buf;

            buf = signature.getSignedContent(documentoDigital);

            ConfigurationRepo configlcr = ConfigurationRepo.getInstance();

            configlcr.setOnline(false);

            PAdESChecker checker = new PAdESChecker();
            byte[] assinatura = contents.getBytes();

            System.out.println("validando");
//            result = checker.checkDetachedSignature(buf, assinatura);

//            if (result == null || result.isEmpty()) {
//                System.err.println("Erro ao validar");
//            }

//            results.addAll(checker.getSignaturesInfo());
        }
    }

    private boolean validarSignInformation(String cpfAssinador, List<SignatureInformations> results) {
        if (results.isEmpty()) {
            return false;
        }

        for (SignatureInformations sis : results) {
            BasicCertificate bc = sis.getIcpBrasilcertificate();
            //Verifica se é pessoa juridica, se o cnpj é o mesmo da empresa logada e o cpf do responsável éo mesmo do assinador
//            if (bc.hasCertificatePJ() && (!bc.getICPBRCertificatePJ().getClass().equals(TokenService.getTokenUsername()) || !bc.getICPBRCertificatePJ().getResponsibleCPF().equals(cpfAssinador))) {
//                return false;
//            }else if (bc.hasCertificatePF() && !bc.getICPBRCertificatePF().getCPF().equals(cpfAssinador)) return false;

        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private X509Certificate extractCertificate(CMSSignedData signedData) {
        try {
            SignerInformationStore signerStore = signedData.getSignerInfos();
            Optional<SignerInformation> signerOpt = signerStore.getSigners().stream().findFirst();
            if (signerOpt.isPresent()) {
                SignerInformation signer = signerOpt.get();

                Store<X509CertificateHolder> certificates = signedData.getCertificates();
                Optional<X509CertificateHolder> certificateHolderOpt = certificates.getMatches(signer.getSID()).stream().findFirst();
                if (certificateHolderOpt.isPresent()) {
                    X509CertificateHolder certificateHolder = certificateHolderOpt.get();
                    return new JcaX509CertificateConverter().getCertificate(certificateHolder);
                }
            }
        } catch (CertificateException e) {
            throw new RuntimeException("Erro ao converter o certificado", e);
        }

        return null;
    }

    private boolean isICPBrasilIssuer(X509Certificate certificate) {
        Pattern patternIssuer = Pattern.compile("ICP-Brasil");
        return patternIssuer.matcher(certificate.getIssuerX500Principal().toString()).find();
    }

    private boolean isSignatureExpired(PDSignature signature, X509Certificate certificate) {
        Date signDate = signature.getSignDate().getTime();
        return signDate.after(certificate.getNotAfter()) || signDate.before(certificate.getNotBefore());
    }
}