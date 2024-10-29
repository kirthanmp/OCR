package org.cpa;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFToCSVAdapter {

    public static void main(String[] args) {
        String pdfFilePath = "./src/main/resources/invoice_4.pdf";

        // CSV file path
        String csvFilePath = "./src/main/resources/invoice_4.csv";

        String pdfContent = PDFReader.extractTextFromPDF(pdfFilePath);

        Map<String, Double> parsedData = parseInvoiceData(pdfContent);

        saveDataToCSV(parsedData, csvFilePath);

        // Sample to check description and price
        String description = "Personalized Mugs";
        double requestedPrice = 10.0;

    }
    // Pdf file path

    private static Map<String, Double> parseInvoiceData(String text) {
        Map<String, Double> itemPriceMap = new HashMap<>();

        // Pattern
        Pattern pricePattern = Pattern.compile("\\$?\\d+(?:\\.\\d{2})?");
        Pattern descriptionPattern = Pattern.compile("(\\w+\\s?)+");

        // Track descriptions and prices
        List<String> descriptions = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        // Split text by lines
        String[] lines = text.split("\n");
        for (String line : lines) {
            line = line.trim();

            // Check for description keywords
            Matcher descriptionMatcher = descriptionPattern.matcher(line);
            if (descriptionMatcher.find() && !line.toLowerCase().matches(".*price|amount|total.*")) {
                descriptions.add(line);
            }
            // Check price pattern
            Matcher priceMatcher = pricePattern.matcher(line);
            while (priceMatcher.find()) {
                String priceStr = priceMatcher.group();
                double price = Double.parseDouble(priceStr.replace("$", ""));
                prices.add(price);
            }
        }

        // Populate item-price map based on alignment
        int itemCount = Math.min(descriptions.size(), prices.size());
        for(int i = 0; i < itemCount; i++) {
            itemPriceMap.put(descriptions.get(i), prices.get(i));
        }

        return itemPriceMap;
    }

    private static void saveDataToCSV(Map<String, Double> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Headers
            writer.write("Description, Price");
            writer.newLine();

            // write into each row in csv file
            for(Map.Entry<String, Double> entry : data.entrySet()) {
                writer.write(entry.getKey() + "," +entry.getValue());
                writer.newLine();;
            }

            System.out.println("Data successfully written to CSV at " +filePath);
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " +e.getMessage());
        }


    }

    private static boolean validatePriceChange(Map<String, Double> parsedData, String description, double price) {
        if (parsedData.containsKey(description)) {
            return parsedData.get(description).equals(price);
        }
        return false;
    }


}
