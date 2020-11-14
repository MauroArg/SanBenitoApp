package com.app.sanbenitoapp.Dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.app.sanbenitoapp.Model.Carrito.Carrito;
import com.app.sanbenitoapp.Model.Pedido.SavePedido;
import com.app.sanbenitoapp.Model.Registro.Invitado;
import com.app.sanbenitoapp.Model.Registro.Registro;
import com.app.sanbenitoapp.Model.Registro.User;

@Database(entities = {
        Registro.class,
        User.class,
        Carrito.class,
        SavePedido.class,
        Invitado.class
        },version = 6)

public abstract class AppDataBase extends RoomDatabase
{
    public abstract RegistroDao getRegistroDao();
    public abstract UserDao     getUserDao();
    public abstract CarritoDao  getCarritoDao();
    public abstract PedidoDao   getPedidoDao();
    public abstract InvitadoDao getInvitadoDao();
}
