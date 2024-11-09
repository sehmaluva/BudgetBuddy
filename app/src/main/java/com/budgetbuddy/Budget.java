package com.budgetbuddy;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Entity(tableName = "Users")
public class Budget {

    private String firstName;
    public String lastName;

    @PrimaryKey
    @NonNull // Ensure this field is not nullable
    private String email;

    private String password;
    private String salt; // Added salt field
    private String phoneNumber;

    public Budget(String lastName, String firstName, @NonNull String email, String password, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.salt = generateSalt(); // Generate salt on user creation
        this.password = hashPassword(password, this.salt); // Hash password with salt
        this.phoneNumber = phoneNumber;
    }

    String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes of salt
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes()); // Add salt to the hash
            md.update(password.getBytes()); // Add password to the hash
            byte[] hashedPassword = md.digest();
            return Base64.getEncoder().encodeToString(hashedPassword); // Store as Base64
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password; // Be cautious with exposing passwords
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSalt() {
        return salt; // Add getter for salt if needed
    }

    public void setSalt(String salt) {
        this.salt = salt; // Add setter for salt if needed
    }
}
