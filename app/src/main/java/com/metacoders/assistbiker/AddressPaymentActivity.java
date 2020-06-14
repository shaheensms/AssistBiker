package com.metacoders.assistbiker;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import static com.metacoders.assistbiker.R.drawable.ic_baseline_check_circle_24;
import static com.metacoders.assistbiker.R.drawable.ic_baseline_radio_button_unchecked_24;

public class AddressPaymentActivity extends AppCompatActivity {

    NiceSpinner zoneSpinner, paymentSpinner;
    List<String> dataset = new LinkedList<>(Arrays.asList("Select A Payment Method", "Cash On Delivery", "Bkash"));
    String paymentMethod;
    private CardView mCashOnCard, mBkashCard;
    private TextView mCashOnTV, mBkashTV;
    private List<zoneResponse> zoneList = new ArrayList<>();
    private List<String> newList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_payment);
        getSupportActionBar().hide();

        mCashOnCard = (CardView) findViewById(R.id.cash_on_card);
        mBkashCard = (CardView) findViewById(R.id.bkash_card);
        mCashOnTV = (TextView) findViewById(R.id.cash_on_tv);
        mBkashTV = (TextView) findViewById(R.id.bkash_tv);
        zoneSpinner = findViewById(R.id.zoneSpinner);

 //       paymentSpinner.attachDataSource(dataset);

//        paymentSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
//            @Override
//            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
//
//                paymentMethod = paymentSpinner.getSelectedItem().toString();
//
//                Toast.makeText(getApplicationContext(), paymentMethod, Toast.LENGTH_SHORT).show();
//            }
//        });

        mCashOnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCashOnTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, ic_baseline_check_circle_24, 0);
                mBkashTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, ic_baseline_radio_button_unchecked_24, 0);
            }
        });

        mBkashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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