package org.cpa;

import com.opencsv.CSVWriter;

import java.io.FileWriter;

public class CSVGenerator {
    public static void writeDataToCSV(String[] headers, String[][] data, String csvfilePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvfilePath))) {
            // Headers
            writer.writeNext(headers);

            // Data
            for(String[] row : data) {
                writer.writeNext(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
