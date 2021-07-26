package com.example.spotifykaraokeee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.FirebaseDatabase;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class LogInPage extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets no title and makes splash screen into full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();//hides the action bar with title
        setContentView(R.layout.activity_log_in_page);

        //our instance of logInPage button
        loginButton = (Button)findViewById(R.id.logInPageButton);
        //sets onClickListener to these buttons
        loginButton.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        inputEmail=(EditText)findViewById(R.id.editTextTextEmailAddress);
        inputPassword=(EditText)findViewById(R.id.editTextTextPassword2);

    }
    //overridden onClick method to allow transition to HomePage
    @Override
    public void onClick(View v) {
        LogInPage.this.authenticateUser();
    }
    public void authenticateUser(){
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogInPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    if (password.length() < 6) {
                        showToast("Password too short, enter minimum 6 characters");
                    }
                    else {
                        LogInPage.this.showToast("Authentication failed. " + task.getException());
                    }
                }
                else {
                    Intent intent = new Intent(LogInPage.this, NavigationHomePage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }
}