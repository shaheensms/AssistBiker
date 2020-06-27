package com.metacoders.assistbiker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.Utils.Utilities;
import com.metacoders.assistbiker.api.api;
import com.metacoders.assistbiker.models.Response_login;
import com.metacoders.assistbiker.models.Sent_Response_login;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mailIn , passIn   ;
    Button loginBtn  ;
    Utilities utilities  ;
    TextView registerBtn ;
    ProgressBar pbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailIn = findViewById(R.id.email) ;
        passIn = findViewById(R.id.pass) ;
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn) ;
        pbar = findViewById(R.id.progress_bar);

        pbar.setVisibility(View.GONE);


        utilities = new Utilities() ;


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext() , SignupActivity.class) ;
                startActivity(i);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail  = "+88"+ mailIn.getText().toString() ;
                String pass = passIn.getText().toString() ;






                if(mail.isEmpty() || pass.isEmpty())
                {
                    utilities.showMsg(getApplicationContext(), "Please Fill The Data" );
                }
                else
                {
                    sendLogInData(mail , pass)  ;

                }

            }
        });





    }

    private void sendLogInData(String mail, String pass) {
        pbar.setVisibility(View.VISIBLE);
        api  api = ServiceGenerator.AllApi() ;
        //creating the sending response

        Sent_Response_login sent_response_login = new Sent_Response_login(mail , pass ) ;

        Call<Response_login> response = api.postUserLoagin(sent_response_login) ;

        response.enqueue(new Callback<Response_login>() {
            @Override
            public void onResponse(Call<Response_login> call, Response<Response_login> response) {

                if (response.isSuccessful())
                {
                    if(response.code() == 200 )
                    {
                        Response_login responsee =  response.body() ;


                        if(responsee.getMsg().equals("true"))
                        {
                            // user is authnticated
//
//                       Toasty.success(getApplicationContext() , "User ID : " + responsee.getCustomer_id() +
//                              " Name :   " +  responsee.getCustomer_address() , Toasty.LENGTH_LONG  , false).show();


                        SaveTheUser(responsee.getCustomer_id()+"" , responsee.getCustomer_name() ,pass ,mail , responsee.getCustomer_address()  ) ;


                        }
                        else
                        {
                            pbar.setVisibility(View.GONE);
                            Toasty.error(getApplicationContext() , "Wrong Email or Password", Toasty.LENGTH_LONG  , false)
                               .show();
                        }


//                        Toasty.success(getApplicationContext() , "User ID : " + responsee.getCustomer_id() +
//                                "MSG  " +  responsee.getMsg() , Toasty.LENGTH_LONG  , false)
//                                .show();


                    }
                    else
                    {
                        pbar.setVisibility(View.GONE);
                        Toasty.error(getApplicationContext() , "Server Is Busy Please Try Again !!" , Toasty.LENGTH_LONG  , false)
                                .show();
                    }

                }
                else
                    {

                        pbar.setVisibility(View.GONE);
                        Toasty.error(getApplicationContext() , "Server Is Busy Please Try Again" , Toasty.LENGTH_LONG  , false)
                                .show();
                }


            }

            @Override
            public void onFailure(Call<Response_login> call, Throwable t) {
                pbar.setVisibility(View.GONE);
                Toasty.error(getApplicationContext() , "Server Is Busy Please Try Again" , Toasty.LENGTH_LONG  , false)
                        .show();
            }
        });



    }

    private void SaveTheUser(  String customer_id , String customer_name, String pass , String number ,String adress) {

        // here saving the user in shared prefs

       // Toasty.success(getApplicationContext() , "LOGGED IN", Toasty.LENGTH_LONG  , false).show();
        //Context context , String number , String pass ,   int user_id , String name
        utilities.createUser(getApplicationContext() , number,pass,Integer.parseInt(customer_id) , customer_name , adress);

        Log.d("TAG", "SaveTheUser: "  + adress);
        pbar.setVisibility(View.GONE);
       Intent o = new Intent(getApplicationContext() , MainActivity.class) ;
       startActivity(o);
       finish();



    }
}
