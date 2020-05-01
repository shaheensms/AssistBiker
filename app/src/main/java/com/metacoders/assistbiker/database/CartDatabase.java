package com.metacoders.assistbiker.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.metacoders.assistbiker.models.CartDbModel;

@Database(entities = {CartDbModel.class}, version = 2, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    public static final String DB_NAME = "biker_cart_db";
    public static final String TABLE_NAME_CART = "cart";

    public abstract Dao dao()  ;

}
