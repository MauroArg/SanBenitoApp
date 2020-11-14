package com.app.sanbenitoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.sanbenitoapp.Model.Agenda.Agenda;
import com.app.sanbenitoapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyViewHolder> {

    private Context context;
    private List<Agenda> cartList;

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


    public AgendaAdapter(Context context, List<Agenda> cartList) {
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
        final Agenda item = cartList.get(position);
        holder.name.setText(item.getNombreMedicina());
        holder.description.setText(item.getIntervalo_repe());
        holder.precio.setText("Entre esta fecha terminara su dosis: "+item.getFecha_aviso());

        Glide.with(context)

                .load(item.getImagenMedicina())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Agenda item, int position) {
        cartList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}