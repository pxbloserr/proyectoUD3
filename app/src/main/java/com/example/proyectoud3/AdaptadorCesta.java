package com.example.proyectoud3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorCesta extends RecyclerView.Adapter <AdaptadorCesta.CestaViewHolder> {

    ArrayList <Producto> cesta;
    OnItemClickListenerCesta onItemClickListenerCesta;

    public AdaptadorCesta (ArrayList<Producto> cesta, OnItemClickListenerCesta onItemClickListenerCesta) {
        this.cesta = cesta;
        this.onItemClickListenerCesta = onItemClickListenerCesta;
    }

    @NonNull
    @Override
    public AdaptadorCesta.CestaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdaptadorCesta.CestaViewHolder cestaViewHolder =
                new AdaptadorCesta.CestaViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_cesta,parent,false)
                );
        return cestaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCesta.CestaViewHolder holder, int position) {
        Producto producto = cesta.get(position);
        holder.imageView.setImageResource(producto.getFoto());
        holder.tv_nombre.setText(producto.getNombre());
        holder.tv_precio.setText(String.valueOf(producto.getPrecio()));
        holder.tv_unidades.setText(String.valueOf(producto.getUdRestantes()));
    }

    @Override
    public int getItemCount() {
        return cesta.size();
    }

    public class CestaViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv_nombre;
        TextView tv_precio;
        TextView tv_unidades;
        Button btn_eliminar;

        public CestaViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.fotoProducto);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_precio = itemView.findViewById(R.id.tv_precio);
            tv_unidades = itemView.findViewById(R.id.tv_restantes);
            btn_eliminar = itemView.findViewById(R.id.btnEliminar);

            btn_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListenerCesta != null) {
                        onItemClickListenerCesta.onItemClickCesta(v, getAdapterPosition());
                    }
                }
            });

        }

    }

    public interface OnItemClickListenerCesta {
        void onItemClickCesta(View view, int position);
    }

}
