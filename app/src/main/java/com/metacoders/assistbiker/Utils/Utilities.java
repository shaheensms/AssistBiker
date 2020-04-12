package com.metacoders.assistbiker.Utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Utilities {


    public  void  showMsg(Context context , String msg ) {

        Toasty.warning(context , msg , Toast.LENGTH_LONG ).show();

    }
}
