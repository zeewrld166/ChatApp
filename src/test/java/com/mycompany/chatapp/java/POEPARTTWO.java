/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp.java;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class POEPARTTWO {

    public static void main(String[] args) {

        //object calling
        Login log = new Login();
        //Scanner object
        Scanner scan = new Scanner(System.in);
        //displaying the message
        System.out.println("*************************************************\n Welcome user please enter the following details to register \n *************************************************");

        //asking the user for the input
        System.out.print("Enter your First name:");
        String firstName = scan.nextLine();
        log.setName(firstName);

        System.out.print("Enter your Last name:");
        String LastName = scan.nextLine();
        log.setLastname(LastName);

        System.out.print("Enter your Username:");
        String UserName = scan.nextLine();
        log.setUsername(UserName);
        //using conditional statements to check if the username meets the conditions

        if (log.checkUsernameComplexity(UserName)) {
            System.out.println("Username is correctly formatted");
        } else {
            System.out.println("Username is not correctly formatted");
        }

        System.out.print("Enter your Password:");
        String Password = scan.nextLine();
        log.setPassword(Password);
        //using conditional statements to check if the password meets the conditions
        if (log.CheckPasswordComplexity(Password)) {
            System.out.println("password is correctly formatted");
        } else {
            System.out.println("password is not correctly formatted");
        }

        System.out.print("Enter your phone number:");
        String PhoneNumber = scan.nextLine();
        log.setPhoneNumber(PhoneNumber);
        //using conditional statements to check if the phonenumber meets the conditions
        if (log.CheckPhoneNumberComplexity(PhoneNumber)) {
            System.out.println("phone number is correctly formatted");
        } else {
            System.out.println("phone number is not correctly formatted");
        }

        //register the user
        String registration = log.RegisterUser(UserName, Password, PhoneNumber);
        System.out.println("Registration: " + registration);
        //checking if the user has registered before loging in
        if ("Registered successfully".equals(registration)) {
            System.out.println("*****************\n LOGIN \n*****************");
            //login user
            System.out.print("Enter username to login:");
            String Secondusername = scan.next();
            System.out.print("Enter password to login:");
            String Secondpassword = scan.next();
            //display  login Status
            boolean loginSuccessful = log.LoginUser(Secondusername, Secondpassword);
            String loginMessage = log.ReturnLoginStatus(loginSuccessful);

            //checking if the user has logged in successfully
            if (loginSuccessful) {
                System.out.println(loginMessage);

                int userChoice = 0;
                Scanner inputReader = new Scanner(System.in);

                System.out.println("Welcome to EasyKanban");
                while (userChoice != 3) {

                    System.out.println("""
                               
                               Choose an option:
                               1. Send Messages
                               2. View Sent Messages
                               3. Exit""");

                    System.out.print("User: ");
                    userChoice = inputReader.nextInt();
                    inputReader.nextLine(); // clear input buffer

                    switch (userChoice) {
                        case 1 ->
                            processMessages();

                        case 2 ->
                            System.out.println("This feature will be added later.");

                        case 3 -> {
                            System.out.println("Application closed. Goodbye!");
                            System.exit(0);
                        }

                        default ->
                            System.out.println("Invalid selection. Try again.");
                    }
                }
            } else {
                System.out.println("password or username is incorrect");
            }

        } else {
            System.out.println("Registration unsuccessful! Please ensure that the requirements are met");
        }

    }

    /**
     * Handles capturing and processing messages
     */
    public static void processMessages() {

        Scanner reader = new Scanner(System.in);
        Message messageHandler = new Message();

        System.out.print("Enter number of messages to process: ");
        int totalMessages = reader.nextInt();
        reader.nextLine();

        // Arrays for storing message details
        String[] contactNumbers = new String[totalMessages];
        String[] generatedIds = new String[totalMessages];
        String[] messageTexts = new String[totalMessages];
        String[] generatedHashes = new String[totalMessages];
        String[] actionResults = new String[totalMessages];

        int sentCounter = 0;

        for (int index = 0; index < totalMessages; index++) {

            // Validate recipient
            do {
                System.out.print("Enter recipient number for message " + (index + 1) + ": ");
                contactNumbers[index] = reader.nextLine();
            } while (!messageHandler.checkphoneNumber(contactNumbers[index]).equals("Valid"));

            // Validate message
            do {
                System.out.print("Enter message text: ");
                messageTexts[index] = reader.nextLine();
            } while (!messageHandler.checkMessageLength(messageTexts[index]));

            // Generate ID and Hash
            String id = com.mycompany.chatapp.java.Message.MessageIdGenerator.generateMessageID();
            System.out.println("Message ID: " + generatedIds[index]);

            generatedHashes[index] = messageHandler.generateMessageHash(
                    generatedIds[index], index, messageTexts[index]
            );
            System.out.println("Message Hash: " + generatedHashes[index]);

            // Message action
            actionResults[index] = messageHandler.selectMessageAction(
                    generatedIds[index],
                    messageTexts[index],
                    generatedHashes[index],
                    contactNumbers[index]
            );

            sentCounter = messageHandler.calculateTotalMessages(sentCounter + 1);
        }

        System.out.println("\nTotal messages processed: " + sentCounter);
    }
}
