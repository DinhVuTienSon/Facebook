package vn.edu.usth.facebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText first_name;
    private EditText sur_name;
    private EditText email;
    private EditText password;
    private EditText confirm_password;


    private FirebaseAuth mAuth;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        first_name = findViewById(R.id.firstname);
        sur_name = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.rePassword);

        Button sign_up = findViewById(R.id.register_btn);
        TextView loginUser = findViewById(R.id.login_textView);


        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);


        loginUser.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        sign_up.setOnClickListener(v -> {
            String txt_first_name = first_name.getText().toString();
            String txt_sur_name = sur_name.getText().toString();
            String txt_email = email.getText().toString();
            String txt_password = password.getText().toString();
            String txt_confirm_password = confirm_password.getText().toString();

            if (TextUtils.isEmpty(txt_first_name) || TextUtils.isEmpty(txt_sur_name)
                    || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
            } else if (txt_password.length() < 8) {
                Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
            } else if (!txt_password.equals(txt_confirm_password)) {
                Toast.makeText(RegisterActivity.this, "Password do not match!", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(txt_first_name, txt_sur_name, txt_email, txt_password);
            }
        });
    }

    private void registerUser(final String first_name, final String sur_name, final String email, String password) {
        pd.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            pd.dismiss();
            Toast.makeText(RegisterActivity.this, "Registration successful! Logging in", Toast.LENGTH_SHORT).show();


            // Perform login immediately after registration
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Successfully logged in, proceed to the main activity
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle login failure if needed
                    Toast.makeText(RegisterActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            });
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}