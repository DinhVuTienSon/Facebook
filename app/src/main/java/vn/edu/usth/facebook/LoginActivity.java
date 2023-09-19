package vn.edu.usth.facebook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private boolean isLoggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set your activity's layout here

        Button loginButton = findViewById(R.id.btnLogin); // Replace with your actual button ID
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulate a successful login
                isLoggedIn = true;
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String defaultUsername = "Admin";
        String defaultPassword = "Admin";

        // Retrieve the entered username and password
        EditText usernameEditText = findViewById(R.id.editUsername);
        EditText passwordEditText = findViewById(R.id.editPassword);

        String enteredUsername = usernameEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        if (enteredUsername.equals(defaultUsername) && enteredPassword.equals(defaultPassword)) {
            // Username and password match the default ones, consider it a successful login
            isLoggedIn = true;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Username and password do not match, show an error message or handle it accordingly
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean userIsLoggedIn() {
        // Check the login status here (e.g., check the isLoggedIn flag)
        return isLoggedIn;
    }
}
