package com.metacoders.assistbiker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class SignupActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @Email
    private TextInputEditText mailIn;
    @NotEmpty
    private TextInputEditText nameIn , mobileIn;

    @NotEmpty
    @Password(min = 3, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
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

        Toast.makeText(this, "message", Toast.LENGTH_LONG).show();
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
