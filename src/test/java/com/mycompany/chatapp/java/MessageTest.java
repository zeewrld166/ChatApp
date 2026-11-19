package com.mycompany.chatapp.java;

import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message class
 */
public class MessageTest {

    Message message;
    Path testFile;

    /**
     * Runs before every test: makes a fresh Message object and adds the
     * test data from the brief (messages 1 to 5).
  
     */
    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        message = new Message();
        testFile = tempDir.resolve("test-messages.json");
        message.setStorageFile(testFile.toString());

        // Test Data Message 1 - Sent
        message.addMessage("1000000001", "+27834557896", "Did you get the cake?",
                message.generateMessageHash("1000000001", 0, "Did you get the cake?"), "Sent");

        // Test Data Message 2 - Stored
        message.addMessage("1000000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.",
                message.generateMessageHash("1000000002", 1, "Where are you? You are late! I have asked you to be on time."), "Stored");

        // Test Data Message 3 - Disregarded
        message.addMessage("1000000003", "+27834484567", "Yohoooo, I am at your gate.",
                message.generateMessageHash("1000000003", 2, "Yohoooo, I am at your gate."), "Disregarded");

        // Test Data Message 4 - Sent (the brief gives 0838884567 for the developer entry, used here as the message ID)
        message.addMessage("0838884567", "Developer", "It is dinner time!",
                message.generateMessageHash("0838884567", 3, "It is dinner time!"), "Sent");

        // Test Data Message 5 - Stored
        message.addMessage("1000000005", "+27838884567", "Ok, I am leaving without you.",
                message.generateMessageHash("1000000005", 4, "Ok, I am leaving without you."), "Stored");
    }

    // ===============================
    // PART 3 - ARRAY TESTS
    // ===============================
    @Test
    void testSentMessagesArrayCorrectlyPopulated() {
        assertEquals(Arrays.asList("Did you get the cake?", "It is dinner time!"),
                message.getSentMessages());
    }

    @Test
    void testDisregardedMessagesArrayCorrectlyPopulated() {
        assertEquals(Arrays.asList("Yohoooo, I am at your gate."),
                message.getDisregardedMessages());
    }

    @Test
    void testStoredMessagesArrayCorrectlyPopulated() {
        assertEquals(Arrays.asList("Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."),
                message.getStoredMessages());
    }

    @Test
    void testMessageHashAndIdArraysPopulated() {
        assertEquals(Arrays.asList("10:0:DIDCAKE", "10:1:WHERETIME", "10:2:YOHOOOOGATE",
                "08:3:ITTIME", "10:4:OKYOU"),
                message.getMessageHashes());

        assertEquals(Arrays.asList("1000000001", "1000000002", "1000000003",
                "0838884567", "1000000005"),
                message.getMessageIDs());
    }

    // ===============================
    // PART 3 - MENU FEATURE TESTS
    // ===============================
    @Test
    void testDisplayLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                message.displayLongestStoredMessage());
    }

    @Test
    void testSearchForMessageID() {
        assertEquals("It is dinner time!", message.searchByMessageID("0838884567"));
    }

    @Test
    void testSearchForMessageIDNotFound() {
        assertEquals("Message ID not found.", message.searchByMessageID("9999999999"));
    }

    @Test
    void testSearchMessagesForRecipient() {
        assertEquals("Where are you? You are late! I have asked you to be on time.\n"
                + "Ok, I am leaving without you.",
                message.searchByRecipient("+27838884567"));
    }

    @Test
    void testDeleteMessageUsingHash() {
        String result = message.deleteMessageByHash("10:1:WHERETIME");

        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                result);
        assertEquals(Arrays.asList("Ok, I am leaving without you."), message.getStoredMessages());
    }

    @Test
    void testDeleteMessageHashNotFound() {
        assertEquals("Message hash not found.", message.deleteMessageByHash("00:0:NOPE"));
    }

    @Test
    void testDisplaySenderAndRecipient() {
        message.setSender("Kyle");
        assertEquals("Sender: Kyle | Recipient: +27838884567\n"
                + "Sender: Kyle | Recipient: +27838884567",
                message.displaySenderAndRecipient());
    }

    @Test
    void testDisplayReport() {
        String expected = "------------------------\n"
                + "Message ID: 1000000001\n"
                + "Message Hash: 10:0:DIDCAKE\n"
                + "Recipient: +27834557896\n"
                + "Message: Did you get the cake?\n"
                + "------------------------\n"
                + "\n"
                + "------------------------\n"
                + "Message ID: 0838884567\n"
                + "Message Hash: 08:3:ITTIME\n"
                + "Recipient: Developer\n"
                + "Message: It is dinner time!\n"
                + "------------------------";

        assertEquals(expected, message.displayReport("Sent"));
    }

    // ===============================
    // PART 3 - JSON FILE TEST
    // ===============================
    @Test
    void testStoredMessagesReadFromJsonFile() {
        // save two messages to the file
        message.saveMessage("2000000001", "Hi Mike, see you at 5", "20:0:HI5", "+27718693002");
        message.saveMessage("2000000002", "She said \"yes\"", "20:1:SHEYES", "+27718693003");

        // a brand new Message object reads them back from the same file
        Message another = new Message();
        another.setStorageFile(testFile.toString());
        another.loadStoredMessages();

        assertEquals(Arrays.asList("Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you.",
                "Hi Mike, see you at 5",
                "She said \"yes\""),
                another.getStoredMessages());
        assertEquals("Hi Mike, see you at 5", another.searchByMessageID("2000000001"));
    }

    // ===============================
    // PART 2 TESTS
    // ===============================
    @Test
    void testMessageLengthValid() {
        String msg = "Hi Mike, can you join us for dinner tonight?";
        assertTrue(message.checkMessageLength(msg));
    }

    @Test
    void testMessageLengthExactly250() {
        String msg = "A".repeat(250);
        assertTrue(message.checkMessageLength(msg));
    }

    @Test
    void testMessageLengthInvalid() {
        String msg = "A".repeat(300);
        assertFalse(message.checkMessageLength(msg));
    }

    @Test
    void testValidCellNumber() {
        assertEquals("Valid", message.checkphoneNumber("+27718693002"));
    }

    @Test
    void testInvalidCellNumberMissingCode() {
        assertEquals("Invalid", message.checkphoneNumber("08575975889"));
    }

    @Test
    void testInvalidCellNumberWithLetters() {
        assertEquals("Invalid", message.checkphoneNumber("+27ABC12345"));
    }

    @Test
    void testMessageHashGeneration() {
        String hash = message.generateMessageHash("0012345678", 0, "Hi Mike tonight");
        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    void testMessageHashIgnoresPunctuation() {
        String hash = message.generateMessageHash("0012345678", 0, "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    void testSecondMessageHash() {
        String hash = message.generateMessageHash("0012345678", 1, "Hi Keegan payment received");
        assertEquals("00:1:HIRECEIVED", hash);
    }

    @Test
    void testGenerateMessageIdLength() {
        String id = Message.MessageIdGenerator.generateMessageID();
        assertEquals(10, id.length());
    }

    @Test
    void testGenerateMessageIdNumeric() {
        String id = Message.MessageIdGenerator.generateMessageID();
        assertTrue(id.matches("\\d+"));
    }

    @Test
    void testCalculateTotalMessages() {
        assertEquals(2, message.calculateTotalMessages(2));
    }

    @Test
    void testTotalMessagesAccumulates() {
        message.calculateTotalMessages(1);
        message.calculateTotalMessages(1);
        assertEquals(2, message.getTotalMessages());
    }
}
