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
    private Button sign_up;
    private TextView login_user;

    private DatabaseReference mRootRef;
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
        sign_up = findViewById(R.id.register_btn);
        login_user = findViewById(R.id.login_textView);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_first_name = first_name.getText().toString();
                String txt_sur_name = sur_name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_confirm_password = confirm_password.getText().toString();

                if (TextUtils.isEmpty(txt_first_name) || TextUtils.isEmpty(txt_sur_name)
                        || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 8){
                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else if (!txt_password.equals(txt_confirm_password)) {
                    Toast.makeText(RegisterActivity.this, "Password do not match!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txt_first_name , txt_sur_name , txt_email , txt_password);
                }
            }
        });
    }

    private void registerUser(final String first_name, final String sur_name, final String email, String password) {

        pd.setMessage("Registering new user!");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                HashMap<String , Object> map = new HashMap<>();
                map.put("firstname" , first_name);
                map.put("surname", sur_name);
                map.put("email", email);
                map.put("password", password);
                map.put("username" , first_name + sur_name);
                map.put("id" , mAuth.getCurrentUser().getUid());
                map.put("bio" , "");
                map.put("imageurl" , "default");

                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "Update the profile " +
                                    "for better expereince", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}