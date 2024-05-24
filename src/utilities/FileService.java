package utilities;

import models.receipts.Receipt;

import java.io.*;

public class FileService {
    public void saveReceiptAsFile(Receipt receipt) {
        String fileName = "receipt_" + receipt.getSerialNumber() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(receipt.toString());
        } catch (IOException e) {
            System.err.println("Error saving receipt to file: " + e.getMessage());
        }
    }

    public String readReceiptFromFile(String serialNumber) {
        String fileName = "receipt_" + serialNumber + ".txt";
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading receipt from file: " + e.getMessage());
        }
        return content.toString();
    }
}