package com.app.sanbenitoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanbenitoapp.Model.Producto.Producto;
import com.app.sanbenitoapp.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder>
{
    private Context mContext;
    private List<Producto> productoList;
    protected ProductoAdapter.ItemListener mListener;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

    public ProductoAdapter(Context context, List<Producto> values, ProductoAdapter.ItemListener itemListener) {
        mContext = context;
        productoList = values;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imgMenu;
        TextView tituloMenu;
        TextView precioMenu;
        Producto producto;


        public ViewHolder(@NonNull View itemView)  {
            super(itemView);
            imgMenu    = itemView.findViewById(R.id.imgMenu);
            tituloMenu = itemView.findViewById(R.id.tituloMenu);
            precioMenu = itemView.findViewById(R.id.precioMenu);
            itemView.setOnClickListener(this);


        }

        public void setData(Producto item)
        {
            this.producto = item;
            tituloMenu.setText(item.getNombre());
            precioMenu.setText(nf.format(Double.parseDouble(item.getPrecio())));
            Glide.with(mContext)
                    .load(item.getImagen())
                    .into(imgMenu);

        }

        @Override
        public void onClick(View view) {
            if (mListener != null)
            {
                mListener.onItemClick(producto);
            }
        }
    }


    public ProductoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_producto,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position)
    {
        holder.setData(productoList.get(position));
    }

    public int getItemCount() {
        return productoList.size();
    }

    public interface ItemListener
    {
        void onItemClick(Producto item);
    }

    public void addProducto(List<Producto> productos)
    {
        for(Producto pro: productos)
        {
            productoList.add(pro);
        }
        notifyDataSetChanged();
    }
}
