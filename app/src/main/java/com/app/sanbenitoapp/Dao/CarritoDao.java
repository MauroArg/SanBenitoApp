package com.app.sanbenitoapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.sanbenitoapp.Model.Carrito.Carrito;

import java.util.List;

@Dao
public interface CarritoDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Carrito carrito);

    @Update
    public void update(Carrito carrito);

    @Delete
    public void delete(Carrito carrito);

    @Query("DELETE FROM carrito")
    void deleteAll();

    @Query("SELECT * FROM carrito")
    public List<Carrito> getCarrito();
}
