package com.app.sanbenitoapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.sanbenitoapp.Model.Pedido.SavePedido;

import java.util.List;

@Dao
public interface PedidoDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(SavePedido pedido);

    @Update
    public void update(SavePedido pedido);

    @Delete
    public void delete(SavePedido pedido);

    @Query("DELETE FROM pedido")
    void deleteAll();

    @Query("SELECT * FROM pedido")
    public List<SavePedido> getPedido();
}
