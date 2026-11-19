/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp.java;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class ChatAppJava {

    public static void main(String[] args) {
        //Message object created first so the same Scanner is used everywhere
        Message message = new Message();
        Scanner scanner = message.userInput;

        System.out.println("  REGISTRATION AND LOGIN ");
        System.out.println("====================");
        
        //Get user's first name 
        System.out.println("Enter Your Name :");
        String firstName= scanner.nextLine();
        
        //Get user's Last name 
        System.out.println("Enter Your Last Name:");
        String lastName =scanner.nextLine();
        
        //Get username 
        System.out.println("Enter a username: ");
        String username =scanner.nextLine();
        
        //Get password 
        System.out.println("Enter a password :");
        String password =scanner.nextLine();
        
        //Get cellphone number 
        System.out.println("Enter your South African Cellphone Number :");
        String cellPhoneNumber=scanner.nextLine();
  
        //Create Registration Object 
        Registration registration =new Registration (firstName ,lastName,username, password ,cellPhoneNumber);
        
        //Register the user 
        String registrationMessage = registration.RegisterUser(username, password);
        
        System.out.println();
        System.out.println(registrationMessage);
        
        //continue to login only and only if the registration was a success
        if (registration.checkcellPhoneNumber(cellPhoneNumber)
                &&registration.checkUserName(username)
                ){
            if (registration.checkPasswordComplexity(password)) {
                System.out.println();
                System.out.println("=======================");
                System.out.println("   LOGIN  ");
                System.out.println("========================");
                
                System.out.println("Enter your username: ");
                String loginUsername=scanner.nextLine();
                
                System.out.println("Enter your password :");
                String loginPassword= scanner.nextLine();
                
                boolean loginSuccessful = registration.loginUser(firstName,lastName, username, password, loginUsername, loginPassword);
                System.out.println();
                System.out.println(registration.returnLoginStatus(loginSuccessful));

                //QuickChat only opens after a successful login
                if (loginSuccessful) {
                    message.setSender(firstName + " " + lastName);
                    runQuickChat(message, scanner);
                }
            }
        }
     scanner.close();   
    }

    /**
     * QuickChat main menu and sending messages
     */
    public static void runQuickChat(Message message, Scanner scanner) {

        System.out.println();
        System.out.println("Welcome to QuickChat.");

        //Read the stored messages from the JSON file into the arrays
        message.loadStoredMessages();

        //User decides how many messages they want to enter
        int howMany = 0;
        while (howMany < 1) {
            System.out.println("How many messages do you want to enter?");
            try {
                howMany = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                howMany = 0;
            }

            if (howMany < 1) {
                System.out.println("Please enter a whole number of 1 or more.");
            }
        }

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.println("Choose an option:");
            String option = scanner.nextLine().trim();

            if (option.equals("1")) {

                if (messagesEntered >= howMany) {
                    System.out.println("You have already entered all " + howMany + " messages.");
                } else {
                    while (messagesEntered < howMany) {
                        String id = Message.MessageIdGenerator.generateMessageID();

                        System.out.println("Enter recipient cell number (+27...):");
                        String recipient = scanner.nextLine().trim();
                        while (message.checkphoneNumber(recipient).equals("Invalid")) {
                            System.out.println("Enter recipient cell number (+27...):");
                            recipient = scanner.nextLine().trim();
                        }

                        System.out.println("Enter your message:");
                        String text = scanner.nextLine();
                        while (!message.checkMessageLength(text)) {
                            System.out.println("Enter your message:");
                            text = scanner.nextLine();
                        }

                        String hash = message.generateMessageHash(id, messagesEntered, text);
                        message.selectMessageAction(id, text, hash, recipient);

                        messagesEntered = messagesEntered + 1;
                    }

                    System.out.println();
                    System.out.println("Total messages sent: " + message.getTotalMessages());
                }

            } else if (option.equals("2")) {
                System.out.println("Coming Soon.");

            } else if (option.equals("3")) {
                System.out.println("Goodbye.");
                running = false;

            } else if (option.equals("4")) {
                storedMessagesMenu(message, scanner);

            } else {
                System.out.println("Please choose 1, 2, 3 or 4.");
            }
        }
    }

    /**
     * Part 3 - Stored Messages menu
     */
    public static void storedMessagesMenu(Message message, Scanner scanner) {

        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("===== STORED MESSAGES =====");
            System.out.println("1) Display the sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search for all messages for a recipient");
            System.out.println("5) Delete a message using the message hash");
            System.out.println("6) Display a report of all stored messages");
            System.out.println("0) Back to main menu");
            System.out.println("Choose an option:");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println(message.displaySenderAndRecipient());

            } else if (choice.equals("2")) {
                System.out.println(message.displayLongestStoredMessage());

            } else if (choice.equals("3")) {
                System.out.println("Enter the message ID:");
                String id = scanner.nextLine().trim();

                String found = message.searchByMessageID(id);

                if (found.equals("Message ID not found.")) {
                    System.out.println(found);
                } else {
                    System.out.println("Recipient: " + message.getRecipientByID(id));
                    System.out.println("Message: " + found);
                }

            } else if (choice.equals("4")) {
                System.out.println("Enter the recipient cell number:");
                String recipient = scanner.nextLine().trim();
                System.out.println(message.searchByRecipient(recipient));

            } else if (choice.equals("5")) {
                System.out.println("Enter the message hash:");
                String hash = scanner.nextLine().trim().toUpperCase();
                System.out.println(message.deleteMessageByHash(hash));

            } else if (choice.equals("6")) {
                System.out.println(message.displayReport("Stored"));

            } else if (choice.equals("0")) {
                back = true;

            } else {
                System.out.println("Please choose 0, 1, 2, 3, 4, 5 or 6.");
            }
        }
    }
}
