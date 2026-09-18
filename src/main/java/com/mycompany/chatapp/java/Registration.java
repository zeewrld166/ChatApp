/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp.java;

/**
 *
 * @author Student
 */
public class Registration {

    //Variables used to store the user's registration details
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    //Constructor 
    public Registration(String firstName, String lastName, String username, String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;

    }
//checks whether the username contains an underscore 
//and is no more than five characters long.  

    public boolean checkUserName(String username) {

        return username.contains("_") && username.length() <= 5;
    }

    //Checks whether the password meets the required complexity.
    public boolean checkPasswordComplexity(String password) {
        String password_regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        boolean passwordChecker;
        //Password must contain at least 8 charaters, a uppercase, a special character, a number 
        if (password.matches(password_regex)) {
            passwordChecker = true;
        } else {
            passwordChecker = false;
        }

        return passwordChecker;

    }

    public String RegisterUser(String username, String password) {
        if (checkUserName(username) && checkPasswordComplexity(password)) {
            System.out.println("The two above conditions have been met and the user has been registered successfully");
            return "User registered successfully";
        } else {
            return "Username or password is incorrectly formatted";
        }
    }

    public boolean checkcellPhoneNumber(String cellPhoneNumber) {
    // South African cellphone number with international code // Example: +27821234567
        String phonePattern = "^\\+27[6-8][0-9]{8}$";
        if (cellPhoneNumber.matches(phonePattern)) {
            System.out.println("Cell phone number successfully added");
            return true;
        } else {
            System.out.println("Cell phone incorrectly formatted or does not contain international code");
            return false;
        }
    }

    public boolean loginUser(String name, String surname, String username, String password, String enteredUsername, String enteredPassword) {
        if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
            System.out.println("Welcome " + name + " " + surname + " it is great to see you again.");
            return true;
        } else {
            System.out.println("Username or Password is incorrect please try again");
            return false;
        }
    }

    public String returnLoginStatus(boolean loginSuccessful) {
        String outcome;
        if (loginSuccessful) {
            outcome = "A successful login";

        } else {
            outcome = "A failed login";

        }
        return outcome;
    }
}
