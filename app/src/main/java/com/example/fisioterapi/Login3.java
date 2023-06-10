package com.example.fisioterapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.Executor;

public class Login3 extends AppCompatActivity {
    private EditText mEmail, password_;
    ProgressBar btn_bar;
    FirebaseAuth mAuth;
    Button btn3_signup;
    ImageView fingerprint;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        mEmail = findViewById(R.id.email2);
        password_ = findViewById(R.id.pass3);
        btn_bar = findViewById(R.id.progressBar);
        btn3_signup = findViewById(R.id.log3);
        fingerprint = findViewById(R.id.fingerprint);

        mAuth = FirebaseAuth.getInstance();

        btn3_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = password_.getText().toString().trim();
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
                    password_.setError("Enter the password");
                    password_.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    password_.setError("Length of the password should be more than 6");
                    password_.requestFocus();
                    return;
                }
                btn_bar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(Login3.this, "Admin Login", Toast.LENGTH_SHORT).show();
                            subscribeToTopic();
                            SharedPreferences sharedPreferences = getSharedPreferences("user_admin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), Mainadmin.class));
                            finishAffinity();

                        } else {
                            Toast.makeText(Login3.this, "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                            btn_bar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        BiometricManager biometricManager = BiometricManager.from(this);
        Executor executor = ContextCompat.getMainExecutor(this);
        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (biometricManager.canAuthenticate()) {
                    case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                        Toast.makeText(getApplicationContext(), "Device Doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                    case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                        Toast.makeText(getApplicationContext(), "Not working", Toast.LENGTH_SHORT).show();
                    case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                        Toast.makeText(getApplicationContext(), "Device Doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                }
                biometricPrompt = new BiometricPrompt(Login3.this, executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        SharedPreferences sharedPreferences = getSharedPreferences("user_admin", MODE_PRIVATE);
                        if (sharedPreferences.getString("email", "").isEmpty()) {
                            Toast.makeText(Login3.this, "Please Login Using Email and Password First!", Toast.LENGTH_SHORT).show();
                            btn_bar.setVisibility(View.GONE);
                            return;
                        } else {
                            String email = sharedPreferences.getString("email", "");
                            String password = sharedPreferences.getString("password", "");
                            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login3.this, "User Login", Toast.LENGTH_SHORT).show();
                                        subscribeToTopic();
                                        startActivity(new Intent(getApplicationContext(), Mainadmin.class));
                                        SharedPreferences sharedPreferences = getSharedPreferences("user_admin", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("email", email);
                                        editor.putString("password", password);
                                        editor.apply();
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(Login3.this, "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                                        btn_bar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });
                promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("HELLO").setDescription("Use FIngerprint To Login").setDeviceCredentialAllowed(true).build();

                biometricPrompt.authenticate(promptInfo);

            }
        });


    }

    //    func to subscribe to topic "emergency"
    public void subscribeToTopic() {
        firebaseMessaging.subscribeToTopic("admin").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "Subscribed";
                if (!task.isSuccessful()) {
                    msg = "Failed";
                }
                Toast.makeText(Login3.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}