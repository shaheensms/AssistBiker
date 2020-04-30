package com.metacoders.assistbiker.database;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.metacoders.assistbiker.models.CartDbModel;

import java.util.List;


@androidx.room.Dao
public interface Dao {

    @Insert
    void insertCartList(List<CartDbModel> cartList);
    @Insert
    long insertCartItem(CartDbModel cart);

    @Query("SELECT * FROM " + CartDatabase.TABLE_NAME_CART)
    List<CartDbModel> fetchAllTodos();

    @Query("SELECT * FROM " + CartDatabase.TABLE_NAME_CART + " WHERE product_id = :product_ID")
    CartDbModel fetchTodoListById(int product_ID);

    @Update
    int updateCart(CartDbModel cart);

    @Delete
    int deleteCartItem(CartDbModel cart);
}
