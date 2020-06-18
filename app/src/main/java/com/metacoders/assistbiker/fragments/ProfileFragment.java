package com.metacoders.assistbiker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.api.api;
import com.metacoders.assistbiker.models.Response_register;
import com.metacoders.assistbiker.models.Sent_Response_register;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment {

    Context context;
    private View view;
    private CircleImageView mProfilePic;
    private TextView mProfileNameTV, mProfileEmailTV;
    private CardView mMyOrdersCard, mNotificationsCard, mChangePassCard, mLogoutCard;
    private TextView mPiEditTV, mCiEditTV, mAdEditTV;
    private TextView mUsernameTV, mUserPhoneTV, mUserEmailTV, mUserAddressTV;
    private List<Sent_Response_register> profileInfoList = new ArrayList<>();
    String oldPassword  , oldImage , oldeName  ;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = view.getContext();

        initializations();
        loadProfile();
        onclickActions();

        return view;
    }

    private void onclickActions() {

        mPiEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mCiEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mAdEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {

        TextInputEditText mName, mNumber, mEmail, mAddress , mNumber2 ;
        Button mOk, mCancel;

        // creating a dialogue with custom design
        final Dialog profileDialogue = new Dialog(context);
        profileDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        profileDialogue.setContentView(R.layout.dialog_profile_information);

        mName = (TextInputEditText) profileDialogue.findViewById(R.id.name);
        mNumber = (TextInputEditText) profileDialogue.findViewById(R.id.phone);
        mEmail = (TextInputEditText) profileDialogue.findViewById(R.id.email);
        mAddress = (TextInputEditText) profileDialogue.findViewById(R.id.address);
        mNumber2 = profileDialogue.findViewById(R.id.phone2) ;
        mOk = (Button) profileDialogue.findViewById(R.id.ok_button);
        mCancel = (Button) profileDialogue.findViewById(R.id.cancel_button);

        // Add button  on  design the use as normal ......
        profileDialogue.show();

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String phone = mNumber.getText().toString();
                String email = mEmail.getText().toString();
                String address = mAddress.getText().toString();
                String phone2 = mNumber2.getText().toString() ;


                mProfileNameTV.setText(name);
                mProfileEmailTV.setText(email);
                mUsernameTV.setText(name);
                mUserPhoneTV.setText(phone);
                mUserEmailTV.setText(email);
                mUserAddressTV.setText(address);


                profileDialogue.dismiss();

                updateProfile(name, phone, email, address, phone2);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDialogue.dismiss();
            }
        });
    }

    private void updateProfile(String name, String phone, String email, String address, String phone2) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(phone)) {
            api api = ServiceGenerator.AllApi();
//String customer_email, String customer_pass, String customer_contact, String customer_contact2, String customer_image, String customer_name, String customer_address) {

            // build the model
            // TODO customer id HERE
           Sent_Response_register response_register = new Sent_Response_register("2",email , oldPassword, phone, phone2 ,  oldImage, name, address);

            Call<Response_register> updateCall = api.postUserUpdate(response_register);

            updateCall.enqueue(new Callback<Response_register>() {
                @Override
                public void onResponse(Call<Response_register> call, Response<Response_register> response) {

                    if(response.isSuccessful() && response.code() == 200)
                    {

                        Response_register responseRegister = response.body() ;


                        if(responseRegister.getMsg().equals("successfull"))
                        {

                            Toasty.success(context , "Profile Updated !!", Toasty.LENGTH_SHORT).show();

                        }
                        else
                        {
                            // something went wrong !!!
                            Toasty.error(context , "Something Error  !!", Toasty.LENGTH_SHORT).show();
                            Log.d("TAG", "onResponse: " +  response.errorBody());
                        }


                    }
                    else {


                        Log.d("TAG", "onResponse: " +  response.errorBody());
                    }

                }

                @Override
                public void onFailure(Call<Response_register> call, Throwable t) {
                    Log.d("TAG", "onResponse: " +  t.getMessage());
                }
            });
        }
    }

    private void loadProfile() {
        Call<List<Sent_Response_register>> call = ServiceGenerator
                .AllApi()
                .getProfile(2); // TODO change the id of the user


        call.enqueue(new Callback<List<Sent_Response_register>>() {
            @Override
            public void onResponse(Call<List<Sent_Response_register>> call, Response<List<Sent_Response_register>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    profileInfoList = response.body();

                    Sent_Response_register profile = profileInfoList.get(0);

                    mProfileNameTV.setText(profile.getCustomer_name());
                    mProfileEmailTV.setText(profile.getCustomer_email());
                    mUsernameTV.setText(profile.getCustomer_name());
                    mUserEmailTV.setText(profile.getCustomer_email());
                    mUserPhoneTV.setText(profile.getCustomer_contact());
                    mUserAddressTV.setText(profile.getCustomer_address());
                    oldPassword = profile.getCustomer_pass() ;
                    oldImage = profile.getCustomer_image() ;
                    oldeName = profile.getCustomer_name() ;


                    Log.d(TAG, "onResponse: Profile" + profileInfoList.toString());
                } else {
                    Toasty.error(context, response.errorBody().toString() + " " + response.code(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sent_Response_register>> call, Throwable t) {
                Toasty.error(context, t.getMessage() + " ", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void initializations() {
        mProfilePic = view.findViewById(R.id.profile_pic);
        mProfileNameTV = view.findViewById(R.id.profile_name_txt);
        mProfileEmailTV = view.findViewById(R.id.profile_email_txt);

        mUsernameTV = view.findViewById(R.id.user_name_txt);
        mUserPhoneTV = view.findViewById(R.id.user_phone_txt);
        mUserEmailTV = view.findViewById(R.id.user_email_txt);
        mUserAddressTV = view.findViewById(R.id.user_address_txt);

        mPiEditTV = view.findViewById(R.id.ps_edit_txt);
        mCiEditTV = view.findViewById(R.id.ci_edit_txt);
        mAdEditTV = view.findViewById(R.id.ad_edit_txt);

        mMyOrdersCard = view.findViewById(R.id.my_order_card);
        mNotificationsCard = view.findViewById(R.id.notification_card);
        mChangePassCard = view.findViewById(R.id.change_pass_card);
        mLogoutCard = view.findViewById(R.id.logout_card);
    }

//    @Override
//    public void updateText(String name, String phone, String email, String address) {
//        mProfileNameTV.setText(name);
//        mProfileEmailTV.setText(email);
//        mUsernameTV.setText(name);
//        mUserEmailTV.setText(email);
//        mUserPhoneTV.setText(phone);
//        mUserAddressTV.setText(address);
//    }
}