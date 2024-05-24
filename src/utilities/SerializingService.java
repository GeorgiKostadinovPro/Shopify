package utilities;

import models.receipts.Receipt;

import java.io.*;

public class SerializingService {
    public void serializeReceipt(Receipt receipt, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(receipt);
            System.out.println("Receipt is successfully added to: " + filename);
        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
        }
    }

    public Receipt deserializeReceipt(String filename) {
        Receipt receipt = null;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = inputStream.readObject();
            if (obj instanceof Receipt) {
                receipt = (Receipt) obj;
                System.out.println("Receipt deserialized successfully from: " + filename);
            } else {
                System.err.println("Unexpected object type found in file: " + obj.getClass().getName());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization failed: " + e.getMessage());
        }

        return receipt;
    }
}
