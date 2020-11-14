package com.app.sanbenitoapp.Rest;

import com.app.sanbenitoapp.Model.Agenda.AgendaResponse;
import com.app.sanbenitoapp.Model.Agenda.DeleteAgendaBody;
import com.app.sanbenitoapp.Model.Agenda.GetAgendaBody;
import com.app.sanbenitoapp.Model.Agenda.SetAgendaBody;
import com.app.sanbenitoapp.Model.Chat.UpdateChatStatusBody;
import com.app.sanbenitoapp.Model.Chat.UpdateChatStatusResponse;
import com.app.sanbenitoapp.Model.Login.LoginBody;
import com.app.sanbenitoapp.Model.Password.NewBody;
import com.app.sanbenitoapp.Model.Password.NewResponse;
import com.app.sanbenitoapp.Model.Password.TokenBody;
import com.app.sanbenitoapp.Model.Password.TokenResponse;
import com.app.sanbenitoapp.Model.Password.VeryBody;
import com.app.sanbenitoapp.Model.Password.VeryResponse;
import com.app.sanbenitoapp.Model.Producto.ProductoResponse;
import com.app.sanbenitoapp.Model.Pedido.DetallePedidoBody;
import com.app.sanbenitoapp.Model.Pedido.PedidoBody;
import com.app.sanbenitoapp.Model.Pedido.PedidoResponse;
import com.app.sanbenitoapp.Model.Pedido.SavePedidoBody;
import com.app.sanbenitoapp.Model.Pedido.SavePedidoResponse;
import com.app.sanbenitoapp.Model.Promocion.PromocionResponse;
import com.app.sanbenitoapp.Model.Registro.FacebookBody;
import com.app.sanbenitoapp.Model.Registro.RegistroBody;
import com.app.sanbenitoapp.Model.Registro.RegistroResponse;
import com.app.sanbenitoapp.Model.Sucursal.SucursalResponse;
import com.app.sanbenitoapp.Model.TokenPush.UpdateTokenPushBody;
import com.app.sanbenitoapp.Model.TokenPush.UpdateTokenPushResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface
{
    //------------------------GET--------------------------------------
    // Obtiene sucursales
    @GET("getSucursales")
    @Headers("Content-Type: application/json")
    Call<SucursalResponse> getSucursales();

    // Obtiene promociones
    @GET("getPromociones")
    @Headers("Content-Type: application/json")
    Call<PromocionResponse> getPromociones();

    //Obtiene medicamentos
    @GET("getMedicamentos/cliente/{iduser}/{token}/usando/{id}/{buscar}")
    @Headers("Content-Type: application/json")
    Call<ProductoResponse> getMedicamentos(@Path("id") String id, @Path("buscar") String buscar, @Path("token") String token, @Path("iduser") String iduser);

    //Obtiene productos segun lo que se busca
    @GET("getSearchData/{iduser}/{token}/{nombre}/{tipo}")
    @Headers("Content-Type: application/json")
    Call<ProductoResponse> getSearchData(@Path("iduser") String id, @Path("token") String token, @Path("nombre") String nombre, @Path("tipo") String tipo);

    // Obtiene productos segun tipo
    @GET("getSucPromMeds1/{iduser}/{token}/{n1}/{opcion}")
    @Headers("Content-Type: application/json")
    Call<ProductoResponse> getSucPromMeds1(@Path("iduser") String iduser, @Path("token") String token,@Path("n1") int n1, @Path("opcion") int opcion);



    //------------------------POST---------------------------------------
    //Regitro de usuario
    @POST("setRegistration")
    @Headers("Content-Type: application/json")
    Call<RegistroResponse> setRegistration(@Body RegistroBody body);

    //Login de usuario
    @POST("getAccessData")
    @Headers("Content-Type: application/json")
    Call<RegistroResponse> getAccessData(@Body LoginBody body);

    //Guardar pedido
    @POST("savePedido")
    @Headers("Content-Type: application/json")
    Call<SavePedidoResponse> savePedido(@Body SavePedidoBody body);

    //Agrega Agenda
    @POST("setAgenda")
    @Headers("Content-Type: application/json")
    Call<AgendaResponse> setAgenda(@Body SetAgendaBody body);

    //Filtra y recibe agendas por usuario
    @POST("getAgendasByUser")
    @Headers("Content-Type: application/json")
    Call<AgendaResponse> getAgendasByUser(@Body GetAgendaBody body);

    //Borra agendas
    @POST("deleteAgenda")
    @Headers("Content-Type: application/json")
    Call<AgendaResponse> deleteAgenda(@Body DeleteAgendaBody body);

    //Obtiene el detalle del pedido
    @POST("getDetallePedido")
    @Headers("Content-Type: application/json")
    Call<SavePedidoResponse> getDetallePedido(@Body DetallePedidoBody body);

    //Obtiene los pedidos activos
    @POST("getActivePedidos")
    @Headers("Content-Type: application/json")
    Call<PedidoResponse> getActivePedidos(@Body PedidoBody body);

    //Login por medio de facebook
    @POST("getDatosAccesoFacebook")
    @Headers("Content-Type: application/json")
    Call<RegistroResponse> getDatosAccesoFacebook(@Body FacebookBody body);

    //Envia un correo para codigo de recuperacion
    @POST("getNewRecoveryToken")
    @Headers("Content-Type: application/json")
    Call<TokenResponse> getNewRecoveryToken(@Body TokenBody body);

    //Envia el codigo recibido al correo
    @POST("verifyCodeRecoveryPassword")
    @Headers("Content-Type: application/json")
    Call<VeryResponse> veryCodeRecoveryPassword(@Body VeryBody body);

    //Cambia la contraseña
    @POST("setNewPassword")
    @Headers("Content-Type: application/json")
    Call<NewResponse> setNewPassword(@Body NewBody body);


    //-----------------------------------PUT-------------------------------------
    //Actualiza el estado del chat
    @PUT("updateChatStatus")
    @Headers("Content-Type: application/json")
    Call<UpdateChatStatusResponse> updateChatStatus(@Body UpdateChatStatusBody body);

    //Actualiza el token de push notification
    @PUT("updateTokenPush")
    @Headers("Content-Type: application/json")
    Call<UpdateTokenPushResponse> updateTokenPush(@Body UpdateTokenPushBody body);
}
