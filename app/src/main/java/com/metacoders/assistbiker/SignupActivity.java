package com.metacoders.assistbiker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMax;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

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
//                .show();



        Intent i = new Intent( getApplicationContext() ,  OTPActivity.class) ;
        i.putExtra("NAME",nameIn.getText().toString() ) ;
        i.putExtra("PASS",passIn.getText().toString() ) ;
        i.putExtra("MAIL",mailIn.getText().toString() ) ;
        i.putExtra("NUM",mobileIn.getText().toString()  ) ;
        startActivity(i);

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
