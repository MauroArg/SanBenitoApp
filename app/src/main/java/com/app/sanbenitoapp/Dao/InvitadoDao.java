package com.app.sanbenitoapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.sanbenitoapp.Model.Registro.Invitado;

import java.util.List;

@Dao
public interface InvitadoDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Invitado invitado);

    @Update
    public void update(Invitado invitado);

    @Delete
    public void delete(Invitado invitado);

    @Query("DELETE FROM invitado")
    void deleteAll();

    @Query("SELECT * FROM invitado")
    public List<Invitado> getInvitado();
}
