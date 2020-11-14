package com.app.sanbenitoapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.sanbenitoapp.Model.Registro.Registro;

import java.util.List;

@Dao
public interface RegistroDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Registro registro);

    @Update
    public void update(Registro registro);

    @Delete
    public void delete(Registro registro);

    @Query("DELETE FROM registro")
    void deleteAll();

    @Query("SELECT * FROM registro")
    public List<Registro> getRegistro();
}
