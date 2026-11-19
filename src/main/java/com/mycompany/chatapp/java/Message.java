package com.mycompany.chatapp.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Message {

    Scanner userInput = new Scanner(System.in);
    private int totalMessages = 0;

    
    private String sender = "Developer";

    // file the stored messages are saved to and read from  
    
    
    private String storageFile = "messages.json";

    // ===============================
    // PART 3 ARRAYS
    // ===============================
    private ArrayList<String> sentMessages = new ArrayList<>();
    private ArrayList<String> disregardedMessages = new ArrayList<>();
    private ArrayList<String> storedMessages = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();

    
    private ArrayList<String> messageRecipients = new ArrayList<>();
    private ArrayList<String> messageTexts = new ArrayList<>();
    private ArrayList<String> messageFlags = new ArrayList<>();

    /**
     * enable  user to choose message action
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

        String choice = "";

        while (!choice.equals("0") && !choice.equals("1") && !choice.equals("2")) {
            System.out.print("User: ");
            choice = userInput.nextLine().trim();

            if (!choice.equals("0") && !choice.equals("1") && !choice.equals("2")) {
                System.out.println("Please enter 0, 1 or 2.");
            }
        }

        int userSelection = Integer.parseInt(choice);

        switch (userSelection) {
            case 0 -> {
                System.out.println("Message successfully sent");
                System.out.println(displayOnlySentMessages(msgId, msgContent, msgHash, receiver));
                addMessage(msgId, receiver, msgContent, msgHash, "Sent");
                calculateTotalMessages(1);
            }

            case 1 -> {
                addMessage(msgId, receiver, msgContent, msgHash, "Disregarded");
                System.out.println("Press 0 to delete the message");
                String deleteChoice = userInput.nextLine().trim();

                if (deleteChoice.equals("0")) {
                    System.out.println("Message deleted.");
                } else {
                    System.out.println("Message not deleted.");
                }
            }

            case 2 -> {
                saveMessage(msgId, msgContent, msgHash, receiver);
                System.out.println("Message successfully stored");
            }
        }

        return actionLabels[userSelection];
    }

    /**
     * Generates a random 10-digit ID
     */
    public static class MessageIdGenerator {

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
     * makes message hash
     *
     */
    public String generateMessageHash(String msgId, int index, String text) {
        String start = msgId.substring(0, 2);
        String[] words = text.trim().split("\\s+");

        // remove punctuation so "tonight?" becomes "tonight"
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");

        return (start + ":" + index + ":" + first + last).toUpperCase();
    }

    /**
     * checks  message length
     * @param text
     * @return
     */
    public boolean checkMessageLength(String text) {

        if (text.length() <= 250) {
            System.out.println("Message sent");
            return true;
        }

        System.out.println("Please enter a message of less than 250 characters.");
        return false;
    }

    /**
     * Displays one message in this sequence
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
     * validate  SA cell numbers code  (+27)
     * @return
     */
    public String checkphoneNumber(String number) {

        if (number != null
                && number.startsWith("+27")
                && number.length() >= 12
                && number.length() <= 13
                && number.matches("\\+27[0-9]+")) {

            System.out.println("Cell number accepted.");
            return "Valid";
        }

        System.out.println("Invalid cell number. Must start with +27.");
        return "Invalid";
    }

    // ===============================
    // PART 3 - ARRAYS
    // ===============================

    /**
     * Add a message to the arrays.
     */
    public void addMessage(String id, String recipient, String text, String hash, String flag) {

        messageIDs.add(id);
        messageHashes.add(hash);
        messageRecipients.add(recipient);
        messageTexts.add(text);
        messageFlags.add(flag);

        if (flag.equals("Sent")) {
            sentMessages.add(text);
        } else if (flag.equals("Disregarded")) {
            disregardedMessages.add(text);
        } else if (flag.equals("Stored")) {
            storedMessages.add(text);
        }
    }

    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<String> getDisregardedMessages() {
        return disregardedMessages;
    }

    public ArrayList<String> getStoredMessages() {
        return storedMessages;
    }

    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public ArrayList<String> getMessageIDs() {
        return messageIDs;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setStorageFile(String storageFile) {
        this.storageFile = storageFile;
    }

    // ===============================
    // PART 3 - STORED MESSAGES MENU FEATURES
    // ===============================

    /**
     * a. Display the sender and recipient of all stored messages
     */
    public String displaySenderAndRecipient() {

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals("Stored")) {
                if (output.length() > 0) {
                    output.append("\n");
                }
                output.append("Sender: ").append(sender)
                      .append(" | Recipient: ").append(messageRecipients.get(i));
            }
        }

        if (output.length() == 0) {
            return "No stored messages.";
        }

        return output.toString();
    }

    /**
     * Display the longest stored message
     */
    public String displayLongestStoredMessage() {

        if (storedMessages.size() == 0) {
            return "No stored messages.";
        }

        String longest = storedMessages.get(0);

        for (int i = 1; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).length() > longest.length()) {
                longest = storedMessages.get(i);
            }
        }

        return longest;
    }

    /**
     * c. Search for a message ID and return the message
     */
    public String searchByMessageID(String id) {

        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return messageTexts.get(i);
            }
        }

        return "Message ID not found.";
    }

    /**
     * c. Search for a message ID and return the recipient
     */
    public String getRecipientByID(String id) {

        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return messageRecipients.get(i);
            }
        }

        return "Message ID not found.";
    }

    /**
     * d. Search all the sent or stored messages for a recipient
     */
    public String searchByRecipient(String recipient) {

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < messageRecipients.size(); i++) {
            boolean sameRecipient = messageRecipients.get(i).equals(recipient);
            boolean sentOrStored = messageFlags.get(i).equals("Sent")
                    || messageFlags.get(i).equals("Stored");

            if (sameRecipient && sentOrStored) {
                if (output.length() > 0) {
                    output.append("\n");
                }
                output.append(messageTexts.get(i));
            }
        }

        if (output.length() == 0) {
            return "No messages found for " + recipient;
        }

        return output.toString();
    }

    /**
     * e. Delete a message using the message hash
     */
    public String deleteMessageByHash(String hash) {

        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {

                String text = messageTexts.get(i);
                String flag = messageFlags.get(i);

                if (flag.equals("Sent")) {
                    sentMessages.remove(text);
                } else if (flag.equals("Disregarded")) {
                    disregardedMessages.remove(text);
                } else if (flag.equals("Stored")) {
                    storedMessages.remove(text);
                }

                messageIDs.remove(i);
                messageHashes.remove(i);
                messageRecipients.remove(i);
                messageTexts.remove(i);
                messageFlags.remove(i);

                
                if (flag.equals("Stored")) {
                    writeStoredFile();
                }

                return "Message: \"" + text + "\" successfully deleted.";
            }
        }

        return "Message hash not found.";
    }

    /**
     * f. Report with the full details of all messages with the given flag
     * Use "Stored" for stored messages or "Sent" for sent messages
     */
    public String displayReport(String flag) {

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals(flag)) {
                output.append(displayOnlySentMessages(
                        messageIDs.get(i),
                        messageTexts.get(i),
                        messageHashes.get(i),
                        messageRecipients.get(i)));
                output.append("\n");
            }
        }

        if (output.length() == 0) {
            return "No " + flag.toLowerCase() + " messages.";
        }

        return output.toString().trim();
    }

    // ===============================
    // PART 3 - JSON FILE
    // ===============================

    /**
     * Stores a message: adds it to the stored array and saves the JSON file
     * @param id
     * @param text
     * @param hash
     * @param recipient
     */
    public void saveMessage(String id, String text, String hash, String recipient) {
        addMessage(id, recipient, text, hash, "Stored");
        writeStoredFile();
    }

    /**
     * One stored message as it appears in the JSON file (used by Gson)
     */
    private static class StoredMessage {

        String messageID;
        String message;
        String messageHash;
        String recipient;
    }

    /**
     * Writes all stored messages to the JSON file using Gson
     */
    private void writeStoredFile() {

        ArrayList<StoredMessage> toSave = new ArrayList<>();

        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals("Stored")) {
                StoredMessage item = new StoredMessage();
                item.messageID = messageIDs.get(i);
                item.message = messageTexts.get(i);
                item.messageHash = messageHashes.get(i);
                item.recipient = messageRecipients.get(i);
                toSave.add(item);
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        try {
            Files.writeString(new File(storageFile).toPath(), gson.toJson(toSave));
        } catch (IOException e) {
            System.out.println("Storage error: " + e.getMessage());
        }
    }

    /**
     * Reads the JSON file into the stored messages array using Gson
     */
    public void loadStoredMessages() {

        File storage = new File(storageFile);

        if (!storage.exists()) {
            return;
        }

        try {
            String content = Files.readString(storage.toPath());
            StoredMessage[] saved = new Gson().fromJson(content, StoredMessage[].class);

            // an empty file gives null
            if (saved == null) {
                return;
            }

            for (int i = 0; i < saved.length; i++) {
                // do not load the same message twice
                if (!messageIDs.contains(saved[i].messageID)) {
                    addMessage(saved[i].messageID, saved[i].recipient,
                            saved[i].message, saved[i].messageHash, "Stored");
                }
            }

        } catch (IOException e) {
            System.out.println("Storage error: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.out.println("Could not read " + storageFile + ". Delete the file and try again.");
        }
    }

    /**
     * Adds to the running total and returns it
     * @param count
     * @return
     */
    public int calculateTotalMessages(int count) {
        totalMessages = totalMessages + count;
        return totalMessages;
    }

    public int getTotalMessages() {
        return totalMessages;
    }
}
