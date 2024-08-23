package br.gov.ce.sop.convenios.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static br.gov.ce.sop.convenios.utils.PdfUtils.isLandscape;


public class PdfAssinaturaUtils {
    public static SignaturePosition setSignaturePosition(PDDocument originalPDF) throws IOException, NumberFormatException {
        SignaturePosition signaturePosition = new SignaturePosition();
        int lastPage = originalPDF.getNumberOfPages() - 1;
        signaturePosition.setActualPage(lastPage);

        PDFRenderer pdfRenderer = new PDFRenderer(originalPDF);
        BufferedImage pageImage = pdfRenderer.renderImage(lastPage);
        BufferedImage newImage = new BufferedImage(pageImage.getWidth(), pageImage.getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(pageImage, 0, 0, null);

        int imageWidth = newImage.getWidth();
        int imageHeight = newImage.getHeight();

        final int STAMP_WIDTH = 145;
        final int STAMP_HEIGHT = 55;

        PDRectangle mediaBox = originalPDF.getPage(lastPage).getMediaBox();
        boolean isLandscapeLocal = mediaBox.getWidth() > mediaBox.getHeight();
        boolean isLandscape = isLandscape(originalPDF.getPage(lastPage));

        int betweenX, betweenY;

        if (isLandscapeLocal || isLandscape) {
            betweenX = 15;
            betweenY = 35;
        } else {
            betweenX = 35;
            betweenY = 15;
        }
        for (int col = 0; col < imageHeight; col++) {
            for (int line = 0; line < imageWidth; line = line + STAMP_WIDTH) {
                boolean isAreaEmpty = true;
                for (int stampLine = line; stampLine < line + STAMP_WIDTH && stampLine < imageWidth; stampLine++){
                    for (int stampCol = col; stampCol < col + STAMP_HEIGHT && stampCol < imageHeight; stampCol++) {
                        int firstPixel = newImage.getRGB(stampLine, stampCol);
//                        int alpha = (firstPixel >> 24) & 0xFF; // Obter o valor de transparência do primeiro pixe
                        if (firstPixel < -1) { // Verificar se o primeiro pixel é completamente transparente
                            isAreaEmpty = false;
                            break;
                        }
                    }
                    if (!isAreaEmpty) { // Verificar se o primeiro pixel é completamente transparente
                        break;
                    }
                }

                if (isAreaEmpty) {
                    int posXStamp = line + betweenX;
                    int posYStamp = col + betweenY;

                    signaturePosition.setPosX(posXStamp);
                    signaturePosition.setPosY(posYStamp);
                    return signaturePosition;
                }
            }
        }

        return signaturePosition;
    }

    public PDVisibleSignDesigner addImage(PDDocument originalPDF, SignaturePosition signaturePosition, String idAssinador, String nomeAssinante,
                                          String cpfAssinante, String tipoAssinador, boolean carimbo) throws IOException {

        InputStream imageURL = getClass().getResourceAsStream(carimbo ? "/images/seloAssinaturaCarimboSop.jpg" : "/images/seloAssinaturaSop.jpg");
        assert imageURL != null;
        final BufferedImage image = ImageIO.read(imageURL);

        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));

        // Setar Nome,Usuario e CPF do Assinador
        drawText(g2, nomeAssinante, 20, 115);
        drawText(g2, formatarIdAssinador(idAssinador), 20, 145);
        drawText(g2, formatarCPF(cpfAssinante), 425, 145);
        // Setar Tipo Assinador
        drawText(g2, tipoAssinador, 20, 175);
        // Setar Data e Hora Assinatura
        String dataHoraString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
        drawText(g2, dataHoraString, 425, 175);

        return setVisibleSignDesigner(originalPDF, signaturePosition.getPosX(), signaturePosition.getPosY(), -80, image, signaturePosition.getActualPage());
    }

    private void drawText(Graphics2D g2, String text, int x, int y) {
        g2.drawString(text, x, y);
    }

    private String formatarIdAssinador(String idAssinador) {
        if (idAssinador.length() == 14) {
            return idAssinador.substring(0, 2) + "." + idAssinador.substring(2, 5) + "." + idAssinador.substring(5, 8) +
                    "/" + idAssinador.substring(8, 12) + "-" + idAssinador.substring(12, 14);
        }
        return idAssinador;
    }

    private String formatarCPF(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public PDVisibleSignDesigner setVisibleSignDesigner(PDDocument originalPDF, int x, int y, int zoomPercent, BufferedImage image, int page) throws IOException {
        int degree = originalPDF.getPage(page).getRotation();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        InputStream imageStream = new ByteArrayInputStream(os.toByteArray());

        PDVisibleSignDesigner visibleSignDesigner;
        int pageAdjusted = (page > 0) ? page : (page + 1);

        visibleSignDesigner = new PDVisibleSignDesigner(originalPDF, imageStream, pageAdjusted);
        visibleSignDesigner.xAxis((float) x).yAxis((float) y).zoom(zoomPercent);
        if (degree == 90 || degree == 270) {
            adjustForRotation(originalPDF, degree, pageAdjusted - 1, visibleSignDesigner, zoomPercent);
        } else {
            visibleSignDesigner.adjustForRotation();
        }

        return visibleSignDesigner;
    }

    public static PDVisibleSignDesigner adjustForRotation(PDDocument originalPDF, int rotation, int page, PDVisibleSignDesigner vsd,
                                                          int zoomFactor) {
        if (rotation < 90) {
            return vsd;
        }

        PDRectangle mediaBox = originalPDF.getPage(page).getMediaBox();
        float factor = 1 + ((float) zoomFactor / 100);

        BufferedImage image = vsd.getImage();
        float imageHeight = image.getWidth() * factor;
        float imageWidth = image.getHeight() * factor;

        float factorX = mediaBox.getWidth() / mediaBox.getHeight();
        float factorY = mediaBox.getHeight() / mediaBox.getWidth();

        AffineTransform affineTransform = new AffineTransform();
        float tmp;
        float newX;
        float newY;

        switch (rotation) {
            case 90 -> {
                tmp = vsd.getxAxis();
                newX = vsd.getyAxis() * factorX;
                newY = tmp * factorY;
                vsd.xAxis(newX);
                vsd.yAxis(mediaBox.getHeight() - (imageHeight + newY));
                affineTransform = new AffineTransform(0, imageHeight / imageWidth, -imageWidth / imageHeight, 0, imageWidth, 0);
            }
            case 270 -> {
                tmp = vsd.getxAxis();
                newX = mediaBox.getWidth() - (vsd.getyAxis() * factorX);
                newY = tmp * factorY;
                vsd.xAxis(newX - imageWidth);
                vsd.yAxis(newY);
                affineTransform = new AffineTransform(0, -imageHeight / imageWidth, imageWidth / imageHeight, 0, 0, imageHeight);
            }
        }

        vsd.width(imageWidth);
        vsd.height(imageHeight);
        vsd.transform(affineTransform);

        return vsd;
    }

    public static PDVisibleSigProperties setVisibleSignatureProperties(PDVisibleSignDesigner parmVisibleSignDesigner, String name,
                                                                String location, String reason, int preferredSize, int page, boolean visualSignEnabled) {

        PDVisibleSigProperties varVisibleSignatureProperties = new PDVisibleSigProperties();

        return varVisibleSignatureProperties.signerName(name).signerLocation(location)
                .signatureReason(reason).preferredSize(preferredSize).page(page).visualSignEnabled(visualSignEnabled)
                .setPdVisibleSignature(parmVisibleSignDesigner);
    }

}
