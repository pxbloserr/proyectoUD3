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

public class AdaptadorProducto extends RecyclerView.Adapter <AdaptadorProducto.ProductoViewHolder> {

    ArrayList <Producto> catalogo;
    OnItemClickListenerCatalogo onItemClickListenerCatalogo;

    public AdaptadorProducto(ArrayList<Producto> catalogo, OnItemClickListenerCatalogo onItemClickListenerCatalogo) {
        this.catalogo = catalogo;
        this.onItemClickListenerCatalogo = onItemClickListenerCatalogo;
    }

    @NonNull
    @Override
    public AdaptadorProducto.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdaptadorProducto.ProductoViewHolder productoViewHolder =
                new ProductoViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_producto,parent,false)
                );
        return productoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProducto.ProductoViewHolder holder, int position) {
        Producto producto = catalogo.get(position);
        holder.imageView.setImageResource(producto.getFoto());
        holder.tv_nombre.setText(producto.getNombre());
        holder.tv_precio.setText(String.valueOf(producto.getPrecio()));
        holder.tv_unidades.setText(String.valueOf(producto.getUdRestantes()));
    }

    @Override
    public int getItemCount() {
        return catalogo.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv_nombre;
        TextView tv_precio;
        TextView tv_unidades;
        Button btn_agregar;

        public ProductoViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.fotoProducto);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_precio = itemView.findViewById(R.id.tv_precio);
            tv_unidades = itemView.findViewById(R.id.tv_restantes);
            btn_agregar = itemView.findViewById(R.id.btnAgregar);

            btn_agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListenerCatalogo != null) {
                        onItemClickListenerCatalogo.onItemClickCatalogo(v, getAdapterPosition());
                    }
                }
            });

        }

    }

    public interface OnItemClickListenerCatalogo {
        public void onItemClickCatalogo (View view, int position);

    }

}
