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

    //atributos del adaptador
    ArrayList <Producto> cesta;
    OnItemClickListenerCesta onItemClickListenerCesta;

    //constructor del adaptador
    public AdaptadorCesta (ArrayList<Producto> cesta, OnItemClickListenerCesta onItemClickListenerCesta) {
        this.cesta = cesta;
        this.onItemClickListenerCesta = onItemClickListenerCesta;
    }

    //se crea el viewHolder con la clase CestaViewHolder
    @NonNull
    @Override
    public AdaptadorCesta.CestaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdaptadorCesta.CestaViewHolder cestaViewHolder =
                new AdaptadorCesta.CestaViewHolder(
                        //se infla el layout del recyclerView con el xml deseado
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_cesta,parent,false)
                );
        return cestaViewHolder;
    }

    //vincula los datos del producto con cada textView del diseño
    @Override
    public void onBindViewHolder(@NonNull AdaptadorCesta.CestaViewHolder holder, int position) {
        Producto producto = cesta.get(position);
        holder.imageView.setImageResource(producto.getFoto());
        holder.tv_nombre.setText(producto.getNombre());
        holder.tv_precio.setText(String.valueOf(producto.getPrecio()));
        holder.tv_unidades.setText(String.valueOf(producto.getUdRestantes()));
    }

    //devuelve la cantidad de items de la lista
    @Override
    public int getItemCount() {
        return cesta.size();
    }

    //clase viewHolder
    public class CestaViewHolder extends RecyclerView.ViewHolder{

        //se declaran las vistas del diseño
        ImageView imageView;
        TextView tv_nombre;
        TextView tv_precio;
        TextView tv_unidades;
        Button btn_eliminar;

        //constructor
        public CestaViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.fotoProducto);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_precio = itemView.findViewById(R.id.tv_precio);
            tv_unidades = itemView.findViewById(R.id.tv_restantes);
            btn_eliminar = itemView.findViewById(R.id.btnEliminar);

            //se añade el listener al botón
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

    //interfaz que define el método onClick del botón de eliminar de la cesta
    public interface OnItemClickListenerCesta {
        void onItemClickCesta(View view, int position);
    }

}
