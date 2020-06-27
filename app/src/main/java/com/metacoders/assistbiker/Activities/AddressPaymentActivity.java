package com.metacoders.assistbiker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.Utils.Utilities;
import com.metacoders.assistbiker.models.zoneResponse;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.metacoders.assistbiker.R.drawable.ic_baseline_check_circle_24;
import static com.metacoders.assistbiker.R.drawable.ic_baseline_radio_button_unchecked_24;

public class AddressPaymentActivity extends AppCompatActivity {

    NiceSpinner zoneSpinner, paymentSpinner;
    String paymentMethod;
    private CardView mCashOnCard, mBkashCard;
    private TextView mCashOnTV, mBkashTV;
    private List<zoneResponse> zoneList = new ArrayList<>();
    private List<String> newList = new ArrayList<>();
    String zone  , delivery_adress ;
    MaterialButton NextBtn ;
    EditText deliveryAdressinput ;
    float  price = -1000 ;
    Utilities utilities ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_payment);
        getSupportActionBar().hide();


        utilities = new Utilities() ;

        deliveryAdressinput = findViewById(R.id.address_ed);
        mCashOnCard = (CardView) findViewById(R.id.cash_on_card);
        mBkashCard = (CardView) findViewById(R.id.bkash_card);
        mCashOnTV = (TextView) findViewById(R.id.cash_on_tv);
        mBkashTV = (TextView) findViewById(R.id.bkash_tv);
        zoneSpinner = findViewById(R.id.zoneSpinner);

        NextBtn = findViewById(R.id.proceedToCheckOut) ;

        deliveryAdressinput.setText(utilities.getSavedAdress(getApplicationContext()));

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delivery_adress = deliveryAdressinput.getText().toString();

                if(delivery_adress.isEmpty() || price<0 || zone.equals("Select Delivery Zone"))
                {
                    Toasty.error(getApplicationContext(), "Please Fill The Form !!" , Toasty.LENGTH_SHORT).show();

                }
                else
                {
                    Intent o = new Intent(getApplicationContext() , CheckOutActivity.class);
                    o.putExtra("deliveryZone" , zone) ;
                    o.putExtra("zoneCharge", price) ;
                    o.putExtra("deliverAddress",delivery_adress ) ;
                    o.putExtra("paymentType",paymentMethod ) ;
                    startActivity(o);
                }




            }
        });




 //       paymentSpinner.attachDataSource(dataset);

        zoneSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, final int position, long id) {



                if(position != 0)
                {
                    zone =zoneList.get(position-1).getZone_name() ;
                    price  = zoneList.get(position-1).getZone_charge() ;
                }


                Toast.makeText(getApplicationContext(), zone + " Price "+  price , Toast.LENGTH_SHORT).show();
            }
        });

        mCashOnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod = "Cash On Delivery" ;

                mCashOnTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, ic_baseline_check_circle_24, 0);
                mBkashTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, ic_baseline_radio_button_unchecked_24, 0);
            }
        });

        mBkashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod = "Bkash" ;
                mBkashTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, ic_baseline_check_circle_24, 0);
                mCashOnTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, ic_baseline_radio_button_unchecked_24, 0);
            }
        });

        loadZoneFromApi();
    }

    public void loadZoneFromApi() {
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
                    newList.add("Select Delivery Zone");

                    Toast.makeText(getApplicationContext(), zoneList.size() + " ", Toast.LENGTH_SHORT).show();
                    //int limit = zoneList.size() ;

                    for (int i = 0; i < zoneList.size(); i++) {
                        newList.add(zoneList.get(i).getZone_name() + " Charge = " + zoneList.get(i).getZone_charge());
                    }

                    zoneSpinner.attachDataSource(newList);

                } else {
                    // response didnot come through

                }

            }

            @Override
            public void onFailure(Call<List<zoneResponse>> call, Throwable t) {

            }
        });

    }
}