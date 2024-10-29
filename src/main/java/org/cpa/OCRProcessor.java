package org.cpa;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRProcessor {
    public static String extractTextFromImage(String imagePath) {
        System.setProperty("jna.library.path", "/opt/homebrew/Cellar/tesseract/5.4.1_1/lib");
        ITesseract tesseract = new Tesseract();

        // path for test data
        tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.4.1_1/share/tessdata");


        try {
            return tesseract.doOCR(new File(imagePath));
        } catch (TesseractException e) {
            System.err.println("Error during OCR processing: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        String imagePath = "./src/main/resources/output/page_0.png";
        String extractedText = extractTextFromImage(imagePath);
        System.out.println("Extracted Text:\n" + extractedText);
    }
}
