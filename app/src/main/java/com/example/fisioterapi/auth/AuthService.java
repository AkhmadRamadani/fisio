package com.example.fisioterapi.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fisioterapi.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import javax.annotation.Nullable;

public class AuthService {
    static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    UserModel userData;

    //    logoutFunction
    public void logout(Context context) {


        firebaseAuth.signOut();
        unsubscribeFromTopic("admin");
        unsubscribeFromTopic("user");
        unsubscribeFromTopic("dokter");
        unsubscribeFromTopic("emergency");
    }

    //    unsubscribe from topic
    public void unsubscribeFromTopic(String topic) {
        firebaseMessaging.unsubscribeFromTopic(topic);
    }

    public boolean login(Context context, String email, String password, String role) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                Log.d("loginfunc", "login: " + task.getResult());
                if (task.isSuccessful()) {
                    if (role.equals("admin")) {
                        subscribeToTopic("admin");
                        subscribeToTopic("emergency");
                    } else if (role.equals("user")) {
                        subscribeToTopic("user");
                    } else if (role.equals("doctor")) {
                        subscribeToTopic("dokter");
                    }
//                    getUserData(context, task.getResult().getUser().getUid()).addOnCompleteListener(task1 -> {
//                        if (task1.isSuccessful()) {
//                            Log.d("loginfunc", "login: " + task1.getResult());
//                        } else {
//                            Log.e("loginfunc", "login: " + task1.getException());
//                        }
//                    });
                }
            });
            return true;

        } catch (Exception e) {
            Log.e("AuthService", "login: " + e.getMessage());
            return false;
        }
    }

    private void getUserData(Context context, String uid) {
        db.collection("users").document(uid).get().addOnCompleteListener(task -> {
            Log.d("getUserData", "getUserData: " + task.getResult());
            if (task.isSuccessful()) {
                try {
                    UserModel userModel = new UserModel(task.getResult().get("name").toString(), task.getResult().get("password").toString(), task.getResult().get("umur").toString(), task.getResult().get("role").toString(), task.getResult().get("gender").toString(), task.getResult().get("phone").toString(), task.getResult().get("name").toString(), task.getResult().get("email").toString(), task.getResult().get("alamat").toString(), task.getResult().get("hospital_id").toString());
                    Gson gson = new Gson();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", gson.toJson(userModel));
                    editor.apply();
                    Log.d("Save User Data", "getUserData: " + gson.toJson(userModel));
                } catch (Exception e) {
                    Log.e("AuthService", "getUserData: " + e.getMessage());
                }
            } else {
                Log.e("AuthService", "getUserData: " + task.getException());
            }
        });
    }

    public void subscribeToTopic(String topic) {
        firebaseMessaging.subscribeToTopic(topic);
    }

    public UserModel getDataUser() {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        userData = gson.fromJson(sharedPreferences.getString("user", ""), UserModel.class);
//        Log.d("getDataUser", "getDataUser: " + userData);
//        return userData;
        try {
            db.collection("users").document(firebaseAuth.getUid()).get().addOnCompleteListener(task -> {
                        if (task.getResult() != null) {
                            this.userData = new UserModel(
                                    firebaseAuth.getUid(),
                                    task.getResult().get("password").toString(),
                                    task.getResult().get("umur").toString(),
                                    task.getResult().get("role").toString(),
                                    task.getResult().get("gender").toString(),
                                    task.getResult().get("phone").toString(),
                                    task.getResult().get("name").toString(),
                                    task.getResult().get("email").toString(),
                                    task.getResult().get("address").toString(),
                                    task.getResult().get("hospital_id").toString()
                            );
                            Log.d("getDataUser", "getDataUser: " + userData);
                        } else {
                            Log.e("getDataUser", "getDataUser: " + task.getException());

                        }
                    }
            );
            return userData;
        } catch (Exception e) {
            Log.e("getDataUser", "getDataUser: " + e.getMessage());
            return null;
        }
    }
}
