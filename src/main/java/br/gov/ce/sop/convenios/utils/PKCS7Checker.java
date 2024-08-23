package br.gov.ce.sop.convenios.utils;

import br.gov.ce.sop.convenios.model.service.impl.documentodigital.SignatureInformations;

import java.util.List;

public interface PKCS7Checker {
    List<SignatureInformations> checkAttachedSignature(byte[] signedData);

    List<SignatureInformations> checkDetachedSignature(byte[] content,
                                                       byte[] signedData);

    List<SignatureInformations> checkSignatureByHash(
            String digestAlgorithmOID, byte[] calculatedHashContent,
            byte[] signedData);

    List<SignatureInformations> getSignaturesInfo();
}
