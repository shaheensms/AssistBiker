package com.metacoders.assistbiker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileEditDialog extends AppCompatDialogFragment {

    private TextInputEditText mName, mNumber, mEmail, mAddress;
    private ProfileDialogListener listener;
    private  Context context ;


    public ProfileEditDialog(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_profile_information, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setTitle("Edit Profile Information")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = mName.getText().toString();
                        String phone = mNumber.getText().toString();
                        String email = mEmail.getText().toString();
                        String address = mAddress.getText().toString();

                        listener.updateText(name, phone, email, address);
                    }
                });

        mName = (TextInputEditText) view.findViewById(R.id.name);
        mNumber = (TextInputEditText) view.findViewById(R.id.phone);
        mEmail = (TextInputEditText) view.findViewById(R.id.email);
        mAddress = (TextInputEditText) view.findViewById(R.id.address);

        return builder.create();
    }

    public interface ProfileDialogListener {
        void updateText(String name, String phone, String email, String address);
    }
}
