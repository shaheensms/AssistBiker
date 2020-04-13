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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
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
            mCallBack  = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
           // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.

            pinView.setOTP(phoneAuthCredential.getSmsCode());

            signInWithPhoneAuthCredentials(phoneAuthCredential);
        }



        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(getApplicationContext(),"verification Failed",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            verificationid = s  ;
            super.onCodeSent(s, forceResendingToken);
            verificationid = s  ;



        }
    };

    private void verifyCode(String code){
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
            signInWithPhoneAuthCredentials(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }


    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toasty.success(getApplicationContext(),"SUCC " ,Toasty.LENGTH_LONG).show();



                        }else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(),"error In verifying OTP",Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }



}








