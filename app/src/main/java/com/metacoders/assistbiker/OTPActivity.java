package com.metacoders.assistbiker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.metacoders.assistbiker.Utils.Utilities;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import in.aabhasjindal.otptextview.OtpTextView;

public class OTPActivity extends AppCompatActivity {

    String name , pass , mail , num  ;
    Utilities utilities ;
    private final static  int RC_SIGN_IN =2 ;
    FirebaseAuth.AuthStateListener mAuthListener ;
    SignInButton google_btn ;
    private OtpTextView pinView ;
    FirebaseAuth mAuth ;
    private String verificationid;
    Button registerBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        utilities = new Utilities() ;
        mAuth = FirebaseAuth.getInstance() ;
        Intent o  = getIntent();
         name = o.getStringExtra("NAME") ;
         pass =  o.getStringExtra("PASS") ;
         mail = o.getStringExtra("MAIL") ;
         num = "+88"+  o.getStringExtra("NUM")   ;


         pinView = findViewById(R.id.otp_view) ;
         registerBtn = findViewById(R.id.signUPBtn);


         registerBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String code =  pinView.getOTP();

              //  utilities.showMsg(getApplicationContext() , code );

                 if ((code.isEmpty() || code.length() < 6)){

                     //  editText.setError("Enter code...");
                     Toast.makeText(getApplicationContext() , "PLease Enter The 6 Digit Code Properly" , Toast.LENGTH_SHORT)
                             .show();
                 }
                 // progressBar.setVisibility(View.VISIBLE);
                // progressBar.setVisibility(View.VISIBLE);

                 else
                 {
                     verifyCode(code);
                 }




             }
         }) ;



        sendVerificationCode(num) ;




    }

    private void sendVerificationCode(String num) {
        utilities.showMsg(OTPActivity.this , "Asking For verfication code On " + num  );

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                num,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null)
            {
                pinView.setOTP(code);
                verifyCode(code);

            }
            else
            {

              //  progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(),"Error: wrong Code  ", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
        //    progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(OTPActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();


        }
    };

    private void verifyCode(String code){
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
            signInWithCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Toasty.success(OTPActivity.this , "NUMBER VERIFIED !!" , Toasty.LENGTH_LONG).show();

                            // now Upload The  User Data

//                            Intent intent = new Intent(OTPActivity.this, homePage.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                            finish();

                        }
                        else {

                            Toast.makeText(OTPActivity.this,"Eror: "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

}
