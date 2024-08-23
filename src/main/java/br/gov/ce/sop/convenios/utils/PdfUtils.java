package br.gov.ce.sop.convenios.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

import java.awt.geom.Point2D;
import java.io.IOException;

public class PdfUtils {
    public static PDPage adicionarPaginaVazia(PDDocument originalPDF, String textoTitulo) {
        PDPage newBlankPage = new PDPage(PDRectangle.A4);

        boolean isLandscapeLocal = originalPDF.getPage(0).getMediaBox().getWidth() > originalPDF.getPage(0).getMediaBox().getHeight();
        boolean isLandscape = isLandscape(newBlankPage);

        PDFont fonteTitulo = PDType1Font.HELVETICA_BOLD;

        int fontSize = 16;
        float posicaoTitulo;
        float posicaoLinha;
        String textoLinha;

        try {
            if (isLandscapeLocal || isLandscape) {
                newBlankPage.setRotation(90);
                posicaoTitulo = 270;
                posicaoLinha = 250;
                textoLinha = "_____________________________________________________________________________________________________________________";
            } else {
                posicaoTitulo = 390;
                posicaoLinha = 370;
                textoLinha = "_________________________________________________________________________________________";
            }

            PDPageContentStream stream = new PDPageContentStream(originalPDF, newBlankPage);

            Point2D.Float titulo = new Point2D.Float(0, posicaoTitulo);
            addCenteredText(textoTitulo, fonteTitulo, fontSize, stream, newBlankPage, titulo);

            Point2D.Float linha = new Point2D.Float(0, posicaoLinha);
            addCenteredText(textoLinha, fonteTitulo, fontSize, stream, newBlankPage, linha);

            stream.close();
        } catch (IOException e) {
            System.err.println("Erro ao criar PDF - " + e);
        }
        return newBlankPage;
    }

    public static void addCenteredText(String text, PDFont font, int fontSize, PDPageContentStream content, PDPage page, Point2D.Float offset) throws IOException {
        content.setFont(font, fontSize);
        content.beginText();

        boolean pageIsLandscape = isLandscape(page);
        Point2D.Float pageCenter = getCenter(page);

        float stringWidth = getStringWidth(text, font, fontSize);
        if (pageIsLandscape) {
            float textX = pageCenter.x - stringWidth / 2F + offset.x;
            float textY = pageCenter.y - offset.y;
            content.setTextMatrix(Matrix.getRotateInstance(Math.PI / 2, textY, textX));
        } else {
            float textX = pageCenter.x - stringWidth / 2F + offset.x;
            float textY = pageCenter.y + offset.y;
            content.setTextMatrix(Matrix.getTranslateInstance(textX, textY));
        }

        content.showText(text);
        content.endText();
    }

    public static float getStringWidth(String text, PDFont font, int fontSize) throws IOException {
        return font.getStringWidth(text) * fontSize / 1000F;
    }

    public static boolean isLandscape(PDPage page) {
        int rotation = page.getRotation();
        return rotation == 90 || rotation == 270;
    }

    public static Point2D.Float getCenter(PDPage page) {
        PDRectangle pageSize = page.getMediaBox();
        boolean rotated = isLandscape(page);
        float pageWidth = rotated ? pageSize.getHeight() : pageSize.getWidth();
        float pageHeight = rotated ? pageSize.getWidth() : pageSize.getHeight();

        return new Point2D.Float(pageWidth / 2F, pageHeight / 2F);
    }
}
