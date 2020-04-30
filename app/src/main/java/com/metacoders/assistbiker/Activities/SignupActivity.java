package com.metacoders.assistbiker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.api.api;
import com.metacoders.assistbiker.models.ResponseModel;
import com.metacoders.assistbiker.models.Sent_Response_mobile;
import com.metacoders.assistbiker.requests.ServiceGenerator;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @Email
    private TextInputEditText mailIn;
    @NotEmpty
    private TextInputEditText nameIn ;

    @NotEmpty
    @Length(min = 11   , max = 11  , message =  "Please Use Proper Phone Number")
    private TextInputEditText mobileIn ;

    @NotEmpty
    @Password(min = 3, scheme = Password.Scheme.ANY)
    private TextInputEditText passIn;

    Validator validator ;
    Button signUP ;


   //TextInputEditText nameIn , mobileIn    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        validator = new Validator(this);
        validator.setValidationListener(SignupActivity.this);
        getSupportActionBar().hide();
        mailIn = findViewById(R.id.mail) ;
        passIn = findViewById(R.id.pass) ;
        nameIn = findViewById(R.id.name) ;
        mobileIn = findViewById(R.id.ph) ;
        signUP = findViewById(R.id.signUPBtn) ;

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();
            }
        });





    }

    @Override
    public void onValidationSucceeded() {

//        Toast.makeText(getApplicationContext() , mobileIn.getText(), Toast.LENGTH_SHORT)
//              .show();



      checkPhoneNumber() ;


    }

    private void checkPhoneNumber() {

        final ProgressDialog dialog = new ProgressDialog(SignupActivity.this);
        dialog.setMessage("Checking Phone Number !!!");
        dialog.show();
        dialog.setCancelable(false);

        api  api = ServiceGenerator.AllApi() ;





        Sent_Response_mobile model = new Sent_Response_mobile( "+88" +mobileIn.getText().toString()) ;


        Call<ResponseModel> chekNumber =  api.checkNumberValid(model) ;

        chekNumber.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {


                dialog.dismiss();

                if(response.code() == 200)
                {
                    ResponseModel model = response.body() ;

                    if(model.getMsg().equals("user_exists"))
                    {
                        Toasty.error(getApplicationContext() , "Error : Phone Number  All Ready Exists  !!!", Toasty.LENGTH_LONG)
                                .show();

                    }
                    else if (model.getMsg().equals("false"))
                    {
                        Intent i = new Intent( getApplicationContext() ,  OTPActivity.class) ;
                        i.putExtra("NAME",nameIn.getText().toString() ) ;
                        i.putExtra("PASS",passIn.getText().toString() ) ;
                        i.putExtra("MAIL",mailIn.getText().toString() ) ;
                        i.putExtra("NUM",mobileIn.getText().toString()  ) ;
                        startActivity(i);

                    }
                    else
                    {
                        Toasty.error(getApplicationContext() , "Error :  Something Went Wrong Try Again !!!", Toasty.LENGTH_LONG)
                                .show();

                    }

                }
                else
                {
                    Toasty.error(getApplicationContext() , "Error : " + response.code() , Toasty.LENGTH_LONG)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                dialog.dismiss();
                Toasty.error(getApplicationContext() , "Error : " + t.getMessage() , Toasty.LENGTH_LONG)
                        .show();
            }
        });






    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
