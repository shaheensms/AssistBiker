package com.metacoders.assistbiker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.metacoders.assistbiker.Activities.LoginActivity;
import com.metacoders.assistbiker.Activities.MainActivity;
import com.metacoders.assistbiker.Utils.Utilities;

import es.dmoral.toasty.Toasty;

public class SplashScreen extends AppCompatActivity {

    Utilities utilities ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        utilities = new Utilities() ;


        int a =  utilities.isUserSignedIn(getApplicationContext()) ;


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(a>0)
                {
                  //  Toasty.success(getApplicationContext(), "User ALREADY SIGNED INN !!!" , Toasty.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();

                }
                else
                {
                   // Toasty.error(getApplicationContext(), "Please Log IN !!!" , Toasty.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        },1500) ;


    }
}