package com.budgetbuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText fName,lName,password1,password2,userEmail,phoneNumber;
    Button signup;
    CheckBox termsAndConditions;
    BudgetDAO budgetDao;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        password1 = findViewById(R.id.signupPassword);
        password2 = findViewById(R.id.confirmPassoword);
        userEmail = findViewById(R.id.signupEmail);
        signup = findViewById(R.id.Signup);
        termsAndConditions = findViewById(R.id.termsAndConditions);

        BudgetDatabase db = BudgetDatabase.getInstance(getApplicationContext());
        budgetDao = db.budgetDAO();

        // Set up the sign-up button click listener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup();
            }
        });

    }
    // Initialize the database and DAO



    private void handleSignup() {
        String firstName = fName.getText().toString().trim();
        String lastName = lName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String password = password1.getText().toString().trim();
        String confirmPassword = password2.getText().toString().trim();
        String phoneNumber = this.phoneNumber.getText().toString().trim();

        // Validate inputs
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!termsAndConditions.isChecked()) {
            Toast.makeText(this, "You must accept the terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check password strength
        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must contain at least 8 characters, including an uppercase letter, a number, and a special character.", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if user already exists
        if (budgetDao.userExists(email) > 0) {
            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Budget object
        Budget newUser = new Budget(lastName, firstName, email, password, phoneNumber); // Phone number can be set later

        // Insert the new user into the database
        new Thread(() -> {
            budgetDao.insertUser(newUser);
            runOnUiThread(() -> {
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity
            });
        }).start();
    }

    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) return false;

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) return false;

        // Check for at least one digit
        if (!password.matches(".*[0-9].*")) return false;

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) return false;

        return true;
    }

}
