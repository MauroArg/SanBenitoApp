package com.app.sanbenitoapp.Util;

import androidx.room.Room;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Dao.CarritoDao;
import com.app.sanbenitoapp.Dao.InvitadoDao;
import com.app.sanbenitoapp.Dao.PedidoDao;
import com.app.sanbenitoapp.Dao.RegistroDao;
import com.app.sanbenitoapp.Dao.UserDao;
import com.app.sanbenitoapp.Model.Carrito.Carrito;
import com.app.sanbenitoapp.Model.Pedido.Pedido;
import com.app.sanbenitoapp.Model.Pedido.SavePedido;
import com.app.sanbenitoapp.Model.Registro.Invitado;
import com.app.sanbenitoapp.Model.Registro.Registro;
import com.app.sanbenitoapp.Model.Registro.User;
import com.app.sanbenitoapp.Rest.ApiInterface;

import java.util.List;

public class GlobalVariables
{
    //Base de datos room
    public static AppDataBase db;

    //Tabla de registro
    public static RegistroDao registrodb;
    public static List<Registro> registroList;

    //Tabla de user
    public static UserDao userdb;
    public static List<User> userList;


    //Tabla pedido
    public static PedidoDao pedidodb;
    public static List<SavePedido> pedidoList;

    //Tabla carrito
    public static CarritoDao carritodb;
    public static List<Carrito> carritoList;

    //Tabla de invitado
    public static InvitadoDao invitadodb;
    public static List<Invitado> invitadoList;

    //Usuario invitado
    public static Invitado invitado = new Invitado("-1","defaultGuestToken");

    //Consumo de API REST
    public static ApiInterface apiService;
}
