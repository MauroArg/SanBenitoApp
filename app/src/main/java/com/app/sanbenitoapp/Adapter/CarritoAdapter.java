package com.app.sanbenitoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.sanbenitoapp.Model.Carrito.Carrito;
import com.app.sanbenitoapp.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.MyViewHolder>
{
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
    private Context context;
    private List<Carrito> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price,comentario,precio,cantidad;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            price = view.findViewById(R.id.price);
            comentario = view.findViewById(R.id.comentario);
            precio = view.findViewById(R.id.precio);
            cantidad = view.findViewById(R.id.cantidad);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }


    public CarritoAdapter(Context context, List<Carrito> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrito, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Carrito item = cartList.get(position);
        holder.name.setText(item.getNombre()+"  "+item.getId());
        holder.description.setText(item.getDescripcion());
        holder.comentario.setText(item.getComentario());
        holder.precio.setText("Precio unitario: " + nf.format(Double.parseDouble(item.getPrecio())));
        holder.cantidad.setText("Cantidad: " + item.getCantidad());

        Double precio = Double.parseDouble(item.getPrecio())* item.getCantidad();
        holder.price.setText(nf.format(precio));

        Glide.with(context)
                .load(item.getImagen())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        cartList.remove(position);
        notifyItemRemoved(position);
    }
}

