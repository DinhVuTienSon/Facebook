package vn.edu.usth.facebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView forgot_password;
    private TextView sign_up;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);
        forgot_password = findViewById(R.id.txtForgotPassword);
        sign_up = findViewById(R.id.txtSignUp);

        mAuth = FirebaseAuth.getInstance();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "Empty username or password!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_username , txt_password);
                }
            }
        });
    }

    private void loginUser(String username, String password) {

        mAuth.signInWithEmailAndPassword(username , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login sucessfully, navigating to newsfeed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}