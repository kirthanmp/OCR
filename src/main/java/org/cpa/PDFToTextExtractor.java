package org.cpa;

import java.io.File;
import java.io.IOException;

public class PDFToTextExtractor {
    public static void main(String[] args) {
        String pdfFilePath = "./src/main/resources/invoice_4.pdf";
        String outputDir = "./src/main/resources/output";

        try {
            // Convert pdf to image
            PDFToImageConverter.convertPDFToImages(pdfFilePath, outputDir);

            // Extract text from image
            File dir = new File(outputDir);
            for (File file : dir.listFiles((d, name) -> name.endsWith(".png"))) {
                String extractedText = OCRProcessor.extractTextFromImage(outputDir+"/page_0.png");
                System.out.println("Text from " + file.getName() + ":\n" + extractedText);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
