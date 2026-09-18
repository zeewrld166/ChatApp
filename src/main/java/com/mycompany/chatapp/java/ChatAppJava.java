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
        Scanner scanner = new Scanner (System.in);
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
            }
        }
     scanner.close();   
    }
}
