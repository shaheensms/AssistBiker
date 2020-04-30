package com.metacoders.assistbiker.Utils;

import android.content.Context;
import android.widget.Toast;

import com.metacoders.assistbiker.models.CartDbModel;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class Utilities {


    public  void  showMsg(Context context , String msg ) {

        Toasty.warning(context , msg , Toast.LENGTH_LONG ).show();

    }

    public   double  calculateTotal(List<CartDbModel> todoList)
    {
        double totalAmount = 0.0 ;

        for(CartDbModel item : todoList)
        {
            totalAmount  = totalAmount + (item.price * item.quantity) ;
        }


        return  totalAmount ;

    }
}
