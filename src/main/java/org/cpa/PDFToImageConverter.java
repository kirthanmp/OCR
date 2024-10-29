package org.cpa;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFToImageConverter {
    public static void convertPDFToImages(String pdfFilePath, String outputDir) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))){
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300);
                File outputFile = new File(outputDir + "/page_" + page + ".png");
                ImageIO.write(bufferedImage, "png", outputFile);
                System.out.println("Saved: " + outputFile.getAbsolutePath());
            }
        }
    }
}
