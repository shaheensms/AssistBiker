package com.metacoders.assistbiker;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.models.zoneResponse;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressPaymentActivity extends AppCompatActivity {

    NiceSpinner zoneSpinner , paymentSpinner  ;
    List<String> dataset = new LinkedList<>(Arrays.asList( "Select A Payment Method" ,  "Cash On Delivery", "Bkash"));
    String paymentMethod ;
    private List<zoneResponse> zoneList = new ArrayList<>();
    private List<String> newList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_payment);
        getSupportActionBar().hide();
        paymentSpinner = (NiceSpinner) findViewById(R.id.paymentMethodSpinner);
        zoneSpinner = findViewById(R.id.zoneSpinner) ;

        paymentSpinner.attachDataSource(dataset);



        paymentSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {

                paymentMethod = paymentSpinner.getSelectedItem().toString() ;

                Toast.makeText(getApplicationContext() ,paymentMethod , Toast.LENGTH_SHORT).show();

            }
        });

            loadZoneFromApi();
    }

    public  void loadZoneFromApi(){
        Call<List<zoneResponse>> call = ServiceGenerator
                .AllApi()
                .getZone();

        call.enqueue(new Callback<List<zoneResponse>>() {
            @Override
            public void onResponse(Call<List<zoneResponse>> call, Response<List<zoneResponse>> response) {

                if (response.isSuccessful() & response.body() != null && response.code() == 200) {
                    newList.clear();
                    zoneList.clear();
                    zoneList = response.body();
                    newList.add("Select Delivery Zone") ;

                    Toast.makeText(getApplicationContext(), zoneList.size() +" " , Toast.LENGTH_SHORT).show();
                   //int limit = zoneList.size() ;

                   for(int i = 0 ; i< zoneList.size() ; i++ ){

                       newList.add(zoneList.get(i).getZone_name() + " Charge = " + zoneList.get(i).getZone_charge()  ) ;
                   }



                    zoneSpinner.attachDataSource(newList);

                }
                else
                {
                    // response didnot come through

                }

            }

            @Override
            public void onFailure(Call<List<zoneResponse>> call, Throwable t) {

            }
        }) ;

    }
}