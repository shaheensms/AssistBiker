package com.metacoders.assistbiker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.metacoders.assistbiker.adapter.SliderAdapterExample;
import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.models.sliderItem;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailActivity extends AppCompatActivity {

    private  static  String TAG = "ProductDetailActivity" ;

    ProductsModel singleProduct ;
    HtmlTextView descriptionView ;
    MaterialButton gotocart , adtocart ;
    TextView title  , price  ;
    ProgressBar progressBar  ;
    SliderView sliderView ;
    SliderAdapterExample adapter ;
    ArrayList<sliderItem> sliderItems  ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
      //  getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_product_detail);

        sliderItems = new ArrayList<>();
        // init view
        title = findViewById(R.id.title) ;
        descriptionView = findViewById(R.id.desc) ;
        gotocart = findViewById(R.id.gotoCartBtn) ;
        adtocart = findViewById(R.id.addToCart) ;
        progressBar = findViewById(R.id.pbar);
        price = findViewById(R.id.price) ;
        sliderView = findViewById(R.id.imageSlider) ;
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.startAutoCycle();

        Intent i = getIntent();
        singleProduct = (ProductsModel) i.getSerializableExtra("PRODUCT") ;
      //  Log.d(TAG , singleProduct.getDate()+ " " + singleProduct.getProduct_title() ) ;





        if(singleProduct == null)
        {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductDetailActivity.this) ;
            alertDialog.setTitle("ERROR!!");
            alertDialog.setMessage("Something Went Wrong !! . Try Again");
            alertDialog.setCancelable(false) ;

            alertDialog.setPositiveButton("GO Back ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }) ;

            AlertDialog alertDialog1 = alertDialog.create();
            alertDialog1.show();


        }
        else
        {
            loadTheViews(singleProduct) ;
        }






    }

    private void loadTheViews(ProductsModel singleProduct) {

        title.setText(singleProduct.getProduct_title());
        price.setText(singleProduct.getProduct_price()+ " BDT");

        sliderItems.add(new sliderItem("https://images.unsplash.com/photo-1501746877-14782df58970?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")) ;
        sliderItems.add(new sliderItem("https://images.unsplash.com/photo-1550825488-28d37063a95e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1000&q=80"));
        sliderItems.add(new sliderItem("data:image/png;base64,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"));
        adapter = new SliderAdapterExample(ProductDetailActivity.this , sliderItems) ;
        sliderView.setSliderAdapter(adapter );


        // converting the HTML
        String description  = singleProduct.getProduct_desc() ;

        try
        {
            int bindex = description.indexOf("<div class=\"separator\"");
            int findex = description.indexOf("/></a></div>") + 12;
            String str = description.substring(bindex, findex);

            description = description.replaceFirst(str, "");

        }
        catch (Exception e )
        {
            Log.d(TAG , "ERROR" + e  ) ;
            description = singleProduct.getProduct_desc() ;

        }

        descriptionView.setHtml(description);
        progressBar.setVisibility(View.GONE);
        descriptionView.setVisibility(View.VISIBLE);

    }
}
