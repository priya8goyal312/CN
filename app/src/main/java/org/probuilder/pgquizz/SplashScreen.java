package org.probuilder.pgquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user==null)
                {
                    //when app open user have to login or create new account
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                    finish();

                }else
                {
                    //when app open user alredy login
                    startActivity(new Intent(SplashScreen.this,CounselingActivity.class));
                    finish();

                }
            }
        },3000);

    }
}