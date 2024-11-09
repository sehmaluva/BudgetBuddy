package com.budgetbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText userEmail,loginPassword;
    private Button loginButton;
    private TextView signupLink;
    private BudgetDAO budgetDAO;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.loginButton);
        signupLink = findViewById(R.id.linkToSignup);

        // Initialize the database and DAO
        BudgetDatabase db = BudgetDatabase.getInstance(getApplicationContext());
        budgetDAO = db.budgetDAO();

        // Set up the login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        // Set up the signup link click listener
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void handleLogin() {
        String email = userEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user exists and password matches
        Budget user = budgetDAO.getUserByEmail(email); // You need to implement this method
        if (user != null) {
            String hashedInputPassword = user.hashPassword(password, user.generateSalt()); // Hash the input password with the stored salt
            if (hashedInputPassword.equals(user.getPassword())) {
                // Successful login
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                // Navigate to the next activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the login activity
            } else {
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

}
