package com.seng324;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountValidatorTest {
    @BeforeAll
    static void setupAll() {
        System.out.println("=== AccountValidator Test Suite Started ===");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("=== AccountValidator Test Suite Finished ===");
    }

    @BeforeEach
    void setupEach(TestInfo testInfo) {
        System.out.println("Running: " + testInfo.getDisplayName());
    }

    @AfterEach
    void teardownEach(TestInfo testInfo) {
        System.out.println("Completed: " + testInfo.getDisplayName());
    }

    @Test
    @Order(1)
    @DisplayName("TC01 - Valid first name returns true (EP: valid partition)")
    void tc01_validFirstName() {
        assertTrue(AccountValidator.isValidFirstName("Alice"));
    }

    @Test
    @Order(2)
    @DisplayName("TC02 - Empty first name returns false (EP: invalid partition)")
    void tc02_emptyFirstName() {
        assertFalse(AccountValidator.isValidFirstName(""));
    }

    @Test
    @Order(3)
    @DisplayName("TC03 - First name with digits returns false (EP: invalid partition)")
    void tc03_firstNameWithDigits() {
        assertFalse(AccountValidator.isValidFirstName("Alice123"));
    }

    @Test
    @Order(4)
    @DisplayName("TC04 - First name of 1 character returns true (BVA: min boundary)")
    void tc04_firstNameMinBoundary() {
        assertTrue(AccountValidator.isValidFirstName("A"));
    }

    @Test
    @Order(5)
    @DisplayName("TC05 - First name of 50 characters returns true (BVA: max boundary)")
    void tc05_firstNameMaxBoundary() {
        assertTrue(AccountValidator.isValidFirstName("A".repeat(50)));
    }

    @Test
    @Order(6)
    @DisplayName("TC06 - First name of 51 characters returns false (BVA: above max)")
    void tc06_firstNameAboveMax() {
        assertFalse(AccountValidator.isValidFirstName("A".repeat(51)));
    }

    @Test
    @Order(7)
    @DisplayName("TC07 - First name with XSS payload returns false (Robustness)")
    void tc07_firstNameXssPayload() {
        assertFalse(AccountValidator.isValidFirstName("<script>alert(1)</script>"));
    }

    @Test
    @Order(8)
    @DisplayName("TC08 - Null first name returns false (Null input)")
    void tc08_nullFirstName() {
        assertFalse(AccountValidator.isValidFirstName(null));
    }

    @Test
    @Order(9)
    @DisplayName("TC09 - Valid last name returns true (EP: valid partition)")
    void tc09_validLastName() {
        assertTrue(AccountValidator.isValidLastName("Smith"));
    }

    @Test
    @Order(10)
    @DisplayName("TC10 - Empty last name returns false (EP: invalid partition)")
    void tc10_emptyLastName() {
        assertFalse(AccountValidator.isValidLastName(""));
    }

    @Test
    @Order(11)
    @DisplayName("TC11 - Last name with digits returns false (EP: invalid partition)")
    void tc11_lastNameWithDigits() {
        assertFalse(AccountValidator.isValidLastName("Smith123"));
    }

    @Test
    @Order(12)
    @DisplayName("TC12 - Last name with special characters returns false (EP: invalid partition)")
    void tc12_lastNameWithSpecialChars() {
        assertFalse(AccountValidator.isValidLastName("O'Brien"));
    }

    @Test
    @Order(13)
    @DisplayName("TC13 - Last name of 1 character returns true (BVA: min boundary)")
    void tc13_lastNameMinBoundary() {
        assertTrue(AccountValidator.isValidLastName("S"));
    }

    @Test
    @Order(14)
    @DisplayName("TC14 - Last name of 50 characters returns true (BVA: max boundary)")
    void tc14_lastNameMaxBoundary() {
        assertTrue(AccountValidator.isValidLastName("S".repeat(50)));
    }

    @Test
    @Order(15)
    @DisplayName("TC15 - Last name of 51 characters returns false (BVA: above max)")
    void tc15_lastNameAboveMax() {
        assertFalse(AccountValidator.isValidLastName("S".repeat(51)));
    }

    @Test
    @Order(16)
    @DisplayName("TC16 - Last name with SQL injection payload returns false (Robustness)")
    void tc16_lastNameSqlInjection() {
        assertFalse(AccountValidator.isValidLastName("O'Brian; DROP TABLE Users;--"));
    }

    @Test
    @Order(17)
    @DisplayName("TC17 - Null last name returns false (Null input)")
    void tc17_nullLastName() {
        assertFalse(AccountValidator.isValidLastName(null));
    }

    @Test
    @Order(18)
    @DisplayName("TC18 - Valid email returns true (EP: valid partition)")
    void tc18_validEmail() {
        assertTrue(AccountValidator.isValidEmail("student@university.edu"));
    }

    @Test
    @Order(19)
    @DisplayName("TC19 - Email missing @ returns false (EP: invalid partition)")
    void tc19_emailMissingAt() {
        assertFalse(AccountValidator.isValidEmail("studentuniversity.edu"));
    }

    @Test
    @Order(20)
    @DisplayName("TC20 - Empty email returns false (EP: invalid partition)")
    void tc20_emptyEmail() {
        assertFalse(AccountValidator.isValidEmail(""));
    }

    @Test
    @Order(21)
    @DisplayName("TC21 - Email without domain extension returns false (EP: invalid partition)")
    void tc21_emailMissingDomainExtension() {
        assertFalse(AccountValidator.isValidEmail("user@domain"));
    }

    @Test
    @Order(22)
    @DisplayName("TC22 - Email without local part returns false (EP: invalid partition)")
    void tc22_emailMissingLocalPart() {
        assertFalse(AccountValidator.isValidEmail("@university.edu"));
    }

    @Test
    @Order(23)
    @DisplayName("TC23 - Email with spaces returns false (EP: invalid partition)")
    void tc23_emailWithSpaces() {
        assertFalse(AccountValidator.isValidEmail("user name@domain.com"));
    }

    @Test
    @Order(24)
    @DisplayName("TC24 - Null email returns false (Null input)")
    void tc24_nullEmail() {
        assertFalse(AccountValidator.isValidEmail(null));
    }

    @Test
    @Order(25)
    @DisplayName("TC25 - Valid date of birth returns true (EP: valid partition)")
    void tc25_validDob() {
        assertTrue(AccountValidator.isValidDateOfBirth("15/06/2000"));
    }

    @Test
    @Order(26)
    @DisplayName("TC26 - DOB with wrong format returns false (EP: invalid partition)")
    void tc26_dobWrongFormat() {
        assertFalse(AccountValidator.isValidDateOfBirth("2000-06-15"));
    }

    @Test
    @Order(27)
    @DisplayName("TC27 - DOB with future year returns false (EP + BVA: above boundary)")
    void tc27_dobFutureYear() {
        assertFalse(AccountValidator.isValidDateOfBirth("01/01/2099"));
    }

    @Test
    @Order(28)
    @DisplayName("TC28 - Impossible date (Feb 31st) returns false (EP: invalid partition)")
    void tc28_impossibleDate() {
        assertFalse(AccountValidator.isValidDateOfBirth("31/02/2025"));
    }

    @Test
    @Order(29)
    @DisplayName("TC29 - DOB of today returns true (BVA: at upper boundary)")
    void tc29_dobToday() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        assertTrue(AccountValidator.isValidDateOfBirth(today));
    }

    @Test
    @Order(30)
    @DisplayName("TC30 - DOB exactly 120 years ago returns true (BVA: lower boundary)")
    void tc30_dobExactly120YearsAgo() {
        String dob = LocalDate.now().minusYears(120).format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        assertTrue(AccountValidator.isValidDateOfBirth(dob));
    }

    @Test
    @Order(31)
    @DisplayName("TC31 - DOB older than 120 years returns false (BVA: below lower boundary)")
    void tc31_dobOlderThan120Years() {
        String dob = LocalDate.now().minusYears(120).minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        assertFalse(AccountValidator.isValidDateOfBirth(dob));
    }

    @Test
    @Order(32)
    @DisplayName("TC32 - Valid password returns true (EP: valid partition)")
    void tc32_validPassword() {
        assertTrue(AccountValidator.isValidPassword("MyPass@123"));
    }

    @Test
    @Order(33)
    @DisplayName("TC33 - Password with no uppercase returns false (EP: invalid partition)")
    void tc33_passwordNoUppercase() {
        assertFalse(AccountValidator.isValidPassword("secure@1pass"));
    }

    @Test
    @Order(34)
    @DisplayName("TC34 - Password without digit returns false (EP: invalid partition)")
    void tc34_passwordNoDigit() {
        assertFalse(AccountValidator.isValidPassword("Secure@Pass"));
    }

    @Test
    @Order(35)
    @DisplayName("TC35 - Password with no special char returns false (EP: invalid partition)")
    void tc35_passwordNoSpecialChar() {
        assertFalse(AccountValidator.isValidPassword("SecurePass1"));
    }

    @Test
    @Order(36)
    @DisplayName("TC36 - Password of 7 characters returns false (BVA: below min)")
    void tc36_passwordBelowMin() {
        assertFalse(AccountValidator.isValidPassword("Sc@1Abc"));
    }

    @Test
    @Order(37)
    @DisplayName("TC37 - Password of exactly 8 characters returns true (BVA: at min)")
    void tc37_passwordAtMin() {
        assertTrue(AccountValidator.isValidPassword("Secure@1"));
    }

    @Test
    @Order(38)
    @DisplayName("TC38 - Password of 63 characters returns true (BVA: below max)")
    void tc38_passwordBelowMax() {
        String pass = "A1@" + "a".repeat(60);
        assertTrue(AccountValidator.isValidPassword(pass));
    }

    @Test
    @Order(39)
    @DisplayName("TC39 - Password of exactly 64 characters returns true (BVA: at max)")
    void tc39_passwordAtMax() {
        String pass = "A1@" + "a".repeat(61);
        assertTrue(AccountValidator.isValidPassword(pass));
    }

    @Test
    @Order(40)
    @DisplayName("TC40 - Password of 65 characters returns false (BVA: above max)")
    void tc40_passwordAboveMax() {
        String pass = "A1@" + "a".repeat(62);
        assertFalse(AccountValidator.isValidPassword(pass));
    }

    @Test
    @Order(41)
    @DisplayName("TC41 - Matching confirm password returns true (EP: valid partition)")
    void tc41_confirmPasswordMatch() {
        assertTrue(AccountValidator.isPasswordMatch("Secure@1", "Secure@1"));
    }

    @Test
    @Order(42)
    @DisplayName("TC42 - Mismatched confirm password returns false (EP: invalid partition)")
    void tc42_confirmPasswordMismatch() {
        assertFalse(AccountValidator.isPasswordMatch("Secure@1", "WrongPass"));
    }

    @Test
    @Order(43)
    @DisplayName("TC43 - Null confirm password returns false (Null input)")
    void tc43_nullConfirmPassword() {
        assertFalse(AccountValidator.isPasswordMatch("Secure@1", null));
    }

    @Test
    @Order(44)
    @DisplayName("TC44 - Both password and confirm password null returns false (Null input)")
    void tc44_bothNullPasswords() {
        assertFalse(AccountValidator.isPasswordMatch(null, null));
    }
}