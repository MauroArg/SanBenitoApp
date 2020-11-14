package com.app.sanbenitoapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.sanbenitoapp.Model.Registro.User;

import java.util.List;

@Dao
public interface UserDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(User user);

    @Update
    public void update(User user);

    @Delete
    public void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user")
    public List<User> getUser();
}
