package com.metacoders.assistbiker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
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
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.Utils.Utilities;
import com.metacoders.assistbiker.api.api;
import com.metacoders.assistbiker.models.Response_register;
import com.metacoders.assistbiker.models.Sent_Response_register;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Dialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getSupportActionBar().hide();
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


            Toasty.error(getApplicationContext(),"verification Failed  "  + e.getMessage() + " Try Again !!",Toast.LENGTH_LONG).show();
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

            Toasty.error(OTPActivity.this, "Verification Code is wrong", Toast.LENGTH_SHORT).show();

        }
    }


    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){


                            RegisterNewUser() ;

                           // Toasty.success(getApplicationContext(),"SUCCEED !!!!!!!!! " ,Toasty.LENGTH_LONG).show();



                        }else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(),"error In verifying OTP",Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }

    private void RegisterNewUser() {


        triggerDialouge(); // showing the new user

        api  api = ServiceGenerator.AllApi() ;

        // build the model
//String customer_email, String customer_pass, String customer_contact, String customer_contact2, String customer_image, String customer_name, String customer_address
        Sent_Response_register response_register = new Sent_Response_register( mail  , pass , num , "null" , "null" , name , "null");

        Call<Response_register> RegisterCall = api.postUserRegister(response_register) ;

        RegisterCall.enqueue(new Callback<Response_register>() {
            @Override
            public void onResponse(Call<Response_register> call, Response<Response_register> response) {


                if(response.code() ==200) {

                    // check the response

                    Response_register resp = response.body();

                    if(resp.getMsg().equals("successfull"))
                    {
                        String userID =  resp.getData() ;

                        dialog.dismiss();
                        Toasty.success(getApplicationContext() , "Users Registers User ID - " + userID  , Toasty.LENGTH_LONG)
                                .show();

                        Intent i = new Intent(getApplicationContext() , MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                    else {
                        RegisterNewUser();
                    }



                }
                else {
                     // request again
                     RegisterNewUser();

                }



            }

            @Override
            public void onFailure(Call<Response_register> call, Throwable t) {
                // failed request again
                RegisterNewUser();


            }
        });





    }

    private  void  triggerDialouge()
    {

        dialog = new Dialog(OTPActivity.this );
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.otp_dialouge);

        TextView dialogueTitle = dialog.findViewById(R.id.progressTitle) ;
        dialog.setCancelable(false);
        dialog.show();




    }



}








