package com.example.fisioterapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText user_name, mEmail, pass_word, mPhone, alamat, umur;
    FirebaseAuth mAuth;
    Button btn2_signup;
    ProgressBar ProgressBar;
    RadioButton laki, perempuan;

    String choosedGender = "Pria";

    RadioGroup jenisKelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user_name = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.emailregister);
        pass_word = findViewById(R.id.passwordregister);
        btn2_signup = findViewById(R.id.buttonregister);
        mPhone = findViewById(R.id.phoneregister);
        ProgressBar = findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();
        alamat = findViewById(R.id.alamat);
        umur = findViewById(R.id.umur);
        laki = findViewById(R.id.pria);
        perempuan = findViewById(R.id.wanita);

        btn2_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = pass_word.getText().toString().trim();
                if (email.isEmpty()) {
                    mEmail.setError("Email is Empty");
                    mEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Enter the valid email address");
                    mEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    pass_word.setError("Enter the password");
                    pass_word.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    pass_word.setError("Length of the password should be more than 6");
                    pass_word.requestFocus();
                    return;
                }
                ProgressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "You are succesfully registered", Toast.LENGTH_SHORT).show();
                            storeUserData();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        } else {
                            Toast.makeText(Register.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        jenisKelamin = findViewById(R.id.jenisKelamin);

        jenisKelamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = jenisKelamin.getCheckedRadioButtonId();

                if (selectedId == laki.getId()) {
                    choosedGender = "Pria";
                } else if (selectedId == perempuan.getId()) {
                    choosedGender = "Wanita";
                }
            }
        });

    }

    //    create a function to store the data in the firestore database
    private void storeUserData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put("name", user_name.getText().toString());
        user.put("email", mEmail.getText().toString());
        user.put("phone", mPhone.getText().toString());
        user.put("password", pass_word.getText().toString());
        user.put("role", "patient");
        user.put("alamat", alamat.getText().toString());
        user.put("umur", umur.getText().toString());
        user.put("gender", choosedGender);
        db.collection("users").document(mAuth.getCurrentUser().getUid()).set(user);
    }

}