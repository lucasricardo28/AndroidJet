package com.ricardo.ricardo.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ricardo.ricardo.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //ESCOLDER ACTION BAR
        getSupportActionBar().hide();

        //FAZER UMA ATUALIZACAO NA INTERFACE
        new Handler().postDelayed(new Runnable() {
            //Exibindo splash com um timer.
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        }, 3000);
    }
}
