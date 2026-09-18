/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class RegistrationTest {

    public RegistrationTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of checkUserName method, of class Registration.
     */
    @Test
    public void testCheckUserNameCorrect() {
        System.out.println("checkUserName");

        String username = "kyl_1";
        Registration instance = new Registration("", "", username, "", "");

        assertTrue(instance.checkUserName(username));
    }

    @Test
    public void testCheckUserNameIncorrectly() {
        System.out.println("checkUserName");

        String username = "kyle!!!!!!!";
        Registration instance = new Registration("", "", username, "", "");

        assertFalse(instance.checkUserName(username));
    }

    /**
     * Test of checkPasswordComplexity method, of class Registration.
     */
    @Test
    public void testCheckPasswordComplexityCorrect() {
        System.out.println("checkPasswordComplexity");

        String password = "Ch&&sec@ke99!";
        Registration instance = new Registration("", "", "", password, "");
        assertTrue(instance.checkPasswordComplexity(password));
    }

    @Test
    public void testCheckPasswordComplexityIncorrect() {
        System.out.println("checkPasswordComplexity");

        String password = "password";
        Registration instance = new Registration("", "", "", password, "");
        assertFalse(instance.checkPasswordComplexity(password));
    }

    /**
     * Test of RegisterUser method, of class Registration.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("RegisterUser");

        String username = "kyl_1";
        String password = "Ch&&sec@ke99!";
        Registration instance = new Registration(" ", "", username, password, "");

        String expResult = "User registered successfully";
        String result = instance.RegisterUser(username, password);

        assertEquals(expResult, result);
    }

    /**
     * Test of checkcellPhoneNumber method, of class Registration.
     */
    @Test
    public void testCheckcellPhoneNumberCorrect() {
        System.out.println("checkcellPhoneNumber");

        String cellPhoneNumber = "+27838969976";
        Registration instance = new Registration("", "", "", "", cellPhoneNumber);

        assertTrue(instance.checkcellPhoneNumber(cellPhoneNumber));
    }

    @Test
    public void testCheckcellPhoneNumberIncorrect() {
        System.out.println("checkcellPhoneNumber");

        String cellPhoneNumber = "08966553";
        Registration instance = new Registration("", "", "", "", cellPhoneNumber);

        assertFalse(instance.checkcellPhoneNumber(cellPhoneNumber));
    }

    /**
     * Test of loginUser method, of class Registration.
     */
    @Test
    public void testLoginUserCorrect() {
        System.out.println("loginUser");

        String name = "Kyle";
        String surname = "Smith";
        String username = "kyl_1";
        String password = "Ch&&sec@ke99!";
        String enteredUsername = "kyl_1";
        String enteredPassword = "Ch&&sec@ke99!";

        Registration instance = new Registration(name, surname, username, password, "");

        assertTrue(instance.loginUser(
                name,
                surname,
                username,
                password,
                enteredUsername,
                enteredPassword
        ));
    }

    @Test
    public void testLoginUserIncorrect() {
        System.out.println("loginUser");

        String name = "Kyle";
        String surname = "Smith";
        String username = "kyl_1";
        String password = "Ch&&sec@ke99!";
        String enteredUsername = "wrong_username";
        String enteredPassword = "wrong_password";

        Registration instance = new Registration(name, surname, username, password, "");

        assertFalse(instance.loginUser(
                name,
                surname,
                username,
                password,
                enteredUsername,
                enteredPassword
        ));
    }

    /**
     * Test of returnLoginStatus method, of class Registration.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        String name = "Kyle";
        String surname = "Smith";
        String username = "kyl_1";
        String password = "Ch&&sec@ke99!";
        String enteredUsername = "wrong_username";
        String enteredPassword = "wrong_password";

        Registration instance = new Registration(name, surname, username, password, "");
        boolean loginSuccessful = true;
        String result = instance.returnLoginStatus(loginSuccessful);

        assertEquals( "A successful login", result);
    }

}
