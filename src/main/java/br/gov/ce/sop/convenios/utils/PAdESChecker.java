package br.gov.ce.sop.convenios.utils;

import br.gov.ce.sop.convenios.model.service.impl.documentodigital.SignatureInformations;

import java.util.List;

public class PAdESChecker implements PKCS7Checker {

//    private final CAdESChecker cAdESChecker = new CAdESChecker();

    @Override
    public List<SignatureInformations> checkAttachedSignature(byte[] signedData) {
//        return cAdESChecker.checkAttachedSignature(signedData);
        return null;
    }

    @Override
    public List<SignatureInformations> checkDetachedSignature(byte[] content,
                                                              byte[] signedData) {
//        return cAdESChecker.checkDetachedSignature(content, signedData);
        return null;
    }

    @Override
    public List<SignatureInformations> checkSignatureByHash(
            String digestAlgorithmOID, byte[] calculatedHashContent,
            byte[] signedData) {
        // TODO Auto-generated method stub
//        return cAdESChecker.checkSignatureByHash(digestAlgorithmOID, calculatedHashContent, signedData);
        return null;
    }

    @Override
    public List<SignatureInformations> getSignaturesInfo() {
        // TODO Auto-generated method stub
//        return cAdESChecker.getSignaturesInfo();
        return null;
    }

}
