/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Scanner;
import org.junit.jupiter.api.ClassOrderer.Random;

/**
 *
 * @author Student
 */
public class Message {

    Scanner userInput = new Scanner(System.in);

    /**
     * Allows user to choose message action
     * @param msgId
     * @param msgContent
     * @param msgHash
     * @param receiver
     * @return 
     */
    public String selectMessageAction(String msgId, String msgContent, String msgHash, String receiver) {

        String[] actionLabels = {
            "Sent",
            "Disregarded",
            "Stored"
        };

        System.out.println("\nChoose action:");
        System.out.println("0. Send Message");
        System.out.println("1. Disregard Message");
        System.out.println("2. Store Message");

        System.out.print("User: ");
        int userSelection = userInput.nextInt();
        userInput.nextLine();

        switch (userSelection) {
            case 0 -> {
                System.out.println("Message sent successfully.");
                System.out.println(displayOnlySentMessages(msgId, msgContent, msgHash, receiver));
            }

            case 1 -> System.out.println("Message ignored.");

            case 2 -> {
                saveMessage(msgId, msgContent, msgHash, receiver);
                System.out.println("Message saved to file.");
            }
        }

        return actionLabels[userSelection];
    }

    /**
     * Generates a random 10-digit ID
     */
 public  static class MessageIdGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateMessageID() {
        StringBuilder idBuilder = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            idBuilder.append(RANDOM.nextInt(10));
        }

        return idBuilder.toString();
    }
}
    /**
     * Creates a message hash
     */
    public String generateMessageHash(String msgId, int index, String text) {
        String start = msgId.substring(0, 2);
        String[] words = text.trim().split("\\s+");
        String first = text.split(" ")[0];
        String last = text.split(" ")[text.split(" ").length - 1];

        return (start + ":" + index + ":" + first + last).toUpperCase();
    }

    /**
     * Validates message length
     * @param text
     * @return 
     */
    public boolean checkMessageLength(String text) {

        if (text.length() <= 250) {
            System.out.println("Message length valid.");
            return true;
        }

        System.out.println("Message too long (max 250 characters).");
        return false;
    }

    /**
     * Displays sent messages only
     * @param id
     * @param text
     * @param hash
     * @param recipient
     * @return 
     */
    public String displayOnlySentMessages(String id, String text, String hash, String recipient) {

        StringBuilder output = new StringBuilder();

        output.append("\n------------------------\n")
              .append("Message ID: ").append(id).append("\n")
              .append("Message Hash: ").append(hash).append("\n")
              .append("Recipient: ").append(recipient).append("\n")
              .append("Message: ").append(text).append("\n")
              .append("------------------------");

        return output.toString();
    }

    /**
     * Validates SA cell numbers (+27)
     * @return 
     */
    public String checkphoneNumber(String number) {

        if (number != null
                && number.startsWith("+27")
                && number.length() >= 12
                && number.matches("\\+27[0-9]+")) {

            System.out.println("Cell number accepted.");
            return "Valid";
        }

        System.out.println("Invalid cell number. Must start with +27.");
        return "Invalid";
    }

    /**
     * Saves message to JSON file
     * @param id
     * @param text
     * @param hash
     * @param recipient
     */
    public void saveMessage(String id, String text, String hash, String recipient) {

        File storage = new File("messages.json");


        String record = "[\"" + escapeJson(id) + "\",\""
            + escapeJson(text) + "\",\""
            + escapeJson(hash) + "\",\""
            + escapeJson(recipient) + "\"]";

        try {
            if (!storage.exists()) {
                storage.createNewFile();
            }

            try (FileWriter writer = new FileWriter(storage, true)) {
                writer.write(record);
                writer.write(System.lineSeparator());
            }

        } catch (IOException e) {
            System.out.println("Storage error: " + e.getMessage());
        }
    }
private String escapeJson(String value) {
    if (value == null) {
        return "";
    }

    return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
}
    /**
     * Returns updated total count
     * @param count
     * @return 
     */
    public int calculateTotalMessages(int count) {
        return count;
    }

    private static class Gson {

        public Gson() {
        }

        private char[] toJson(String[] record) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    

}  

