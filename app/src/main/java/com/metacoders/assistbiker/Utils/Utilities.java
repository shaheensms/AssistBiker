package com.metacoders.assistbiker.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.metacoders.assistbiker.models.CartDbModel;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class Utilities {

    public static SharedPreferences getPrefs(Context context){
        return  context.getSharedPreferences("USER",Context.MODE_PRIVATE);
    }

    public  void  showMsg(Context context , String msg ) {

        Toasty.warning(context , msg , Toast.LENGTH_LONG ).show();

    }

    public   double  calculateTotal(List<CartDbModel> todoList) {
        double totalAmount = 0.0 ;

        for(CartDbModel item : todoList)
        {
            totalAmount  = totalAmount + (item.price * item.quantity) ;
        }


        return  totalAmount ;

    }


    public  void deleteExistingUSER(Context context){

        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString("number","null");
        editor.putString("pass","null");
        editor.putString("name", "null");
        editor.putInt("user_id", 0);
        editor.apply();

        Log.d("TAG", "ReadExistingResname:  USER DELTED !!"  );
    }

    public  void createUser(Context context , String number , String pass ,   int user_id , String name ){

        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString("number",number);
        editor.putString("name",name);
        editor.putString("pass",pass);
        editor.putInt("user_id", user_id);

        editor.apply();
        Log.d("TAG", "ReadExistingResname: CREATED  USER DATA " );

    }

    public  int  isUserSignedIn(Context context) {
        int def = 0 ;
         String number = getPrefs(context).getString("number","null");
         String pass = getPrefs(context).getString("pass", "null")  ;
         int UserID  = getPrefs(context).getInt("user_id" , 0) ;

         if(!number.equals("null") && !pass.equals("null") && UserID!= 0) {
             Log.d("TAG", "ReadExistingResname:  USER Found "+ UserID );
             return UserID ;

         }
         else {
             Log.d("TAG", "ReadExistingResname:  USER NOT FOUND  " );
              return  def  ;

         }
    }

}
