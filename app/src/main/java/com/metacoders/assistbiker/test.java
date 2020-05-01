package com.metacoders.assistbiker;

import com.metacoders.assistbiker.models.NewsFeedModel;
import com.metacoders.assistbiker.models.ProductsModel;

import java.util.List;

public class test {


    public  List<NewsFeedModel> news  ;

    public  List<ProductsModel> products  ;


    public test(List<NewsFeedModel> news, List<ProductsModel> products) {
        this.news = news;
        this.products = products;
    }
}
