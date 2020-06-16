package com.metacoders.assistbiker.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.metacoders.assistbiker.database.CartDatabase;

import java.io.Serializable;

@Entity(tableName = CartDatabase.TABLE_NAME_CART)
public class CartDbModel {


    @PrimaryKey(autoGenerate = true)
    public int cart_id;

    public String title;

    public Float price;

    public Integer quantity;

    public Integer product_id;

    public String product_image ;

    @Ignore
    public String priority;


}
