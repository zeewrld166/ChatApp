/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Message class Tests logic-based methods only
 */
public class MessageTest {

    Message message = new Message();

    // ===============================
    // MESSAGE LENGTH TESTS (250 chars)
    // ===============================
    @Test
    void testMessageLengthValid() {
        String msg = "Hi Mike, can you join us for dinner tonight?";
        assertTrue(message.checkMessageLength(msg));
    }

    @Test
    void testMessageLengthInvalid() {
        String msg = "A".repeat(300); // longer than 250 chars
        assertFalse(message.checkMessageLength(msg));
    }

    // ===============================
    // CELL NUMBER VALIDATION TESTS
    // ===============================
    @Test
    void testValidCellNumber() {
        String result = message.checkphoneNumber("+27718693002");
        assertEquals("Valid", result);
    }

    @Test
    void testInvalidCellNumberMissingCode() {
        String result = message.checkphoneNumber("08575975889");
        assertEquals("Invalid", result);
    }

    @Test
    void testInvalidCellNumberWithLetters() {
        String result = message.checkphoneNumber("+27ABC12345");
        assertEquals("Invalid", result);
    }

    // ===============================
    // MESSAGE HASH TESTS
    // ===============================
    @Test
    void testMessageHashGeneration() {
        String messageId = "0012345678";
        String messageText = "Hi Mike tonight";

        String hash = message.generateMessageHash(messageId, 0, messageText);

        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    void testMultipleMessageHashesNotNull() {
        String messageId = "0012345678";

        String[] messages = {
            "Hi Mike tonight",
            "Hi Keegan payment received"
        };

        for (int i = 0; i < messages.length; i++) {
            String hash = message.generateMessageHash(messageId, i, messages[i]);
            assertNotNull(hash);
            assertTrue(hash.contains(":"));
        }
    }

    // ===============================
    // MESSAGE ID TEST
    // ===============================
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

    // ===============================
    // STORE MESSAGE (NO EXCEPTION)
    // ===============================
    @Test
    void testStoreMessageDoesNotThrow() {
        assertDoesNotThrow(() -> {
            message.saveMessage(
                    "0012345678",
                    "Hi Mike",
                    "00:0:HIMIKE",
                    "+27718693002"
            );
        });
    }

    // ===============================
    // TOTAL MESSAGE COUNT
    // ===============================
    @Test
    void testCalculateTotalMessages() {
        int total = message.calculateTotalMessages(2);
        assertEquals(2, total);
    }
}
