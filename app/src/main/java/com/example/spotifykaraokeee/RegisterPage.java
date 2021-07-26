package com.example.spotifykaraokeee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener{

    Button signUpButton;
    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private static final String TAG = "RegisterPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets no title and makes splash screen into full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();//hides the action bar with title
        setContentView(R.layout.activity_register_page);

        //our instance of our signUp button
        signUpButton = (Button)findViewById(R.id.registerPageButton);
        signUpButton.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        inputEmail=(EditText)findViewById(R.id.editTextTextEmailAddress);
        inputPassword=(EditText)findViewById(R.id.editTextTextPassword2);

    }
    //overridden onClick method to allow transition to HomePage
    @Override
    public void onClick(View v) {
        RegisterPage.this.registerUser();
        /*final RegisterPage registerPage = this;

        Intent intent = new Intent(registerPage, NavigationHomePage.class);
        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(registerPage, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
        startActivity(intent, bundle);*/
    }
    private void registerUser(){
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            showToast("Enter email address!");
            return;
        }
        if(TextUtils.isEmpty(password)){
            showToast("Enter Password!");
            return;
        }
        if(password.length() < 6){
            showToast("Password too short, enter minimum 6 characters");
            return;
        }
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "New user registration: " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    RegisterPage.this.showToast("Authentication failed. " + task.getException());
                }
                else {
                    RegisterPage.this.startActivity(new Intent(RegisterPage.this, NavigationHomePage.class));
                    RegisterPage.this.finish();
                }
            }
        });
    }//register user
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }
}