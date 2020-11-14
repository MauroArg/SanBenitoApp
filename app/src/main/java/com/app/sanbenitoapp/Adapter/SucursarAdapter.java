package com.app.sanbenitoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.sanbenitoapp.Model.Sucursal.Sucursal;
import com.app.sanbenitoapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class SucursarAdapter extends RecyclerView.Adapter<SucursarAdapter.ViewHolder>
        {
    List<Sucursal> mValues;
    Context mContext;
    protected ItemListener mListener;

    public SucursarAdapter(Context context,  List<Sucursal> values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

            public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                public TextView tituloRestaurante,descripcionMenu,telefono;
                public ImageView imageView;
                public LinearLayout lContent;
                Sucursal item;

                public ViewHolder(View v) {

                    super(v);

                    v.setOnClickListener(this);
                    tituloRestaurante = (TextView) v.findViewById(R.id.titulo);
                    descripcionMenu = (TextView) v.findViewById(R.id.direccion);
                    telefono = (TextView) v.findViewById(R.id.telefono);
                    imageView = (ImageView) v.findViewById(R.id.img);
                    //lContent = (LinearLayout) v.findViewById(R.id.lContent);

                }

                public void setData(Sucursal item) {
                    this.item = item;

                    tituloRestaurante.setText(item.getNombre());
                    descripcionMenu.setText(item.getDireccion());
                    telefono.setText(item.getHorario());

                    Glide.with(mContext)
                            .load(item.getImagen())
                            .into(imageView);

                    //relativeLayout.setBackgroundColor(Color.parseColor(item.color));

                }


                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(item,imageView);
                    }
                }
            }
            @Override
            public SucursarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(mContext).inflate(R.layout.item_sucursales, parent, false);

                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(SucursarAdapter.ViewHolder viewHolder, int i) {
                viewHolder.setData(mValues.get(i));
            }


            @Override
            public int getItemCount() {

                return mValues.size();
            }

            public interface ItemListener {
                void onItemClick(Sucursal item, ImageView imageview);
            }
}

