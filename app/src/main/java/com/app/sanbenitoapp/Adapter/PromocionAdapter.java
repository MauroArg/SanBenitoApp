package com.app.sanbenitoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.sanbenitoapp.Model.Promocion.Promocion;
import com.app.sanbenitoapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class PromocionAdapter  extends RecyclerView.Adapter<PromocionAdapter.ViewHolder>{
    List<Promocion> mValues;
    Context mContext;
    protected ItemListener mListener;

    public PromocionAdapter(Context context, List<Promocion> values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener = itemListener;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tituloRestaurante,descripcionMenu,telefono;
        public ImageView imageView;
        public LinearLayout lContent;
        Promocion item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            tituloRestaurante = (TextView) v.findViewById(R.id.titulo);
            descripcionMenu = (TextView) v.findViewById(R.id.direccion);
            telefono = (TextView) v.findViewById(R.id.telefono);
            imageView = (ImageView) v.findViewById(R.id.img);
            //lContent = (LinearLayout) v.findViewById(R.id.lContent);

        }

        public void setData(Promocion item) {
            this.item = item;

            tituloRestaurante.setText(item.getNombre());
            descripcionMenu.setText(item.getDescripcion());
            telefono.setText("Hasta: "+item.getFecha_limite());

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
    public PromocionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_promocion, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PromocionAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setData(mValues.get(i));
    }


    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(Promocion item, ImageView imageview);
    }
}
