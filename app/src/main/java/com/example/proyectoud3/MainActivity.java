package com.example.proyectoud3;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdaptadorProducto.OnItemClickListenerCatalogo, AdaptadorCesta.OnItemClickListenerCesta{

    ArrayList <Producto> productos;
    ArrayList <Producto> cesta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productos = cargarProductos ();

        ocultarSwitch();

        Switch switch1 = findViewById(R.id.switchTodo);
        switch1.setChecked(false);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ArrayList <Producto> disponibles = new ArrayList <Producto> ();
                    for(Producto p : productos){
                        if(p.getUdRestantes() > 0){
                            disponibles.add(p);
                        }
                    }

                    cargarRecyclerViewCatalogo(disponibles);

                }
                else {

                    cargarRecyclerViewCatalogo(productos);

                }

            }

        });

    }

    @Override
    public void onItemClickCatalogo(View view, int position) {
        Producto producto = productos.get(position);
        if(producto.getUdRestantes() > 0){
            cesta.add(producto);
            Toast.makeText(MainActivity.this, producto.getNombre() + " añadido correctamente a la cesta", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "No hay " + producto.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClickCesta(View view, int position) {

        if(!cesta.isEmpty()){

            Producto producto = cesta.get(position);
            cesta.remove(producto);

            cargarRecyclerViewCesta(cesta);

            Toast.makeText(MainActivity.this, producto.getNombre() + " eliminado correctamente de la cesta", Toast.LENGTH_SHORT).show();

            if(cesta.isEmpty()){

                TextView tv_cestaVacia = findViewById(R.id.tv_cestaVacia);
                tv_cestaVacia.setVisibility(View.VISIBLE);

                RecyclerView rvCatalogo = findViewById(R.id.rvCatalogo);
                rvCatalogo.setPadding(0, 0, 0, 0);
                rvCatalogo.setVisibility(View.INVISIBLE);

            }

        }

    }

    public void cambiarVista(View view) {

        Button boton = findViewById(view.getId());
        Log.i("PRUEBA", boton.getText().toString());
        Log.i("PRUEBA", getString(R.string.btnCatalogo));

        if(boton.getText().toString().equals(getString(R.string.btnCatalogo))){

            Log.i("PRUEBA", "ENTRA A CATALOGO");

            if(!productos.isEmpty()){

                mostrarSwitch();

                cargarRecyclerViewCatalogo(productos);

                boton.setText(getString(R.string.btnCesta));

            }

        } else {

            Log.i("PRUEBA", "ENTRA A CESTA");

            if(!cesta.isEmpty()){

                ocultarSwitch();

                cargarRecyclerViewCesta(cesta);

                boton.setText(getString(R.string.btnCatalogo));

            } else {
                Toast.makeText(MainActivity.this, "La cesta está vacía", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void ocultarSwitch(){

        TextView tvTodo =findViewById(R.id.tvSwitchTodo);
        TextView tvDisponible =findViewById(R.id.tvSwitchDisponible);
        Switch switch1 = findViewById(R.id.switchTodo);

        tvTodo.setVisibility(View.INVISIBLE);
        tvDisponible.setVisibility(View.INVISIBLE);
        switch1.setVisibility(View.INVISIBLE);

    }

    private void mostrarSwitch(){

        TextView tvTodo =findViewById(R.id.tvSwitchTodo);
        TextView tvDisponible =findViewById(R.id.tvSwitchDisponible);
        Switch switch1 = findViewById(R.id.switchTodo);

        tvTodo.setVisibility(View.VISIBLE);
        tvDisponible.setVisibility(View.VISIBLE);
        switch1.setVisibility(View.VISIBLE);
    }

    private void cargarRecyclerViewCatalogo(ArrayList <Producto> productos){

        RecyclerView rvCatalogo = findViewById(R.id.rvCatalogo);

        int value = 111;
        int dpValue = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                MainActivity.this.getResources().getDisplayMetrics());

        rvCatalogo.setPadding(0, 0, 0, dpValue);
        rvCatalogo.setVisibility(View.VISIBLE);

        AdaptadorProducto adaptadorProducto = new AdaptadorProducto(productos, MainActivity.this);
        rvCatalogo.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rvCatalogo.setAdapter(adaptadorProducto);

    }

    private void cargarRecyclerViewCesta(ArrayList <Producto> cesta){

        RecyclerView rvCatalogo = findViewById(R.id.rvCatalogo);
        rvCatalogo.setPadding(0, 0, 0, 128);
        rvCatalogo.setVisibility(View.VISIBLE);

        AdaptadorCesta adaptadorCesta = new AdaptadorCesta(cesta, MainActivity.this);
        rvCatalogo.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rvCatalogo.setAdapter(adaptadorCesta);

    }

    private ArrayList <Producto> cargarProductos () {

        ArrayList <Producto> catalogo = new ArrayList<>(Arrays.asList(new Producto[]{
                new Producto("Pan de molde", 1.50, R.drawable.pan_de_molde, 15),
                new Producto("Leche", 0.99, R.drawable.leche, 20),
                new Producto("Huevos", 2.30, R.drawable.huevos, 0),
                new Producto("Arroz", 1.10, R.drawable.arroz, 12),
                new Producto("Pasta", 1.20, R.drawable.pasta, 8),
                new Producto("Harina", 0.85, R.drawable.harina, 0),
                new Producto("Azúcar", 1.00, R.drawable.azucar, 25),
                new Producto("Sal", 0.60, R.drawable.sal, 18),
                new Producto("Aceite de oliva", 4.50, R.drawable.aceite_de_oliva, 5),
                new Producto("Aceite de girasol", 3.20, R.drawable.aceite_de_girasol, 10),
                new Producto("Café", 2.50, R.drawable.cafe, 7),
                new Producto("Té", 1.80, R.drawable.te, 12),
                new Producto("Agua embotellada", 0.50, R.drawable.agua_embotellada, 30),
                new Producto("Refrescos", 1.20, R.drawable.refrescos, 0),
                new Producto("Jugo natural", 1.50, R.drawable.jugo_natural, 8),
                new Producto("Mantequilla", 1.70, R.drawable.mantequilla, 10),
                new Producto("Queso", 2.80, R.drawable.queso, 5),
                new Producto("Yogur", 0.90, R.drawable.yogur, 20),
                new Producto("Carne", 5.50, R.drawable.carne, 3),
                new Producto("Pescado", 6.00, R.drawable.pescado, 2),
                new Producto("Manzanas", 1.20, R.drawable.manzanas, 15),
                new Producto("Zanahorias", 0.80, R.drawable.zanahorias, 18),
                new Producto("Patatas", 0.70, R.drawable.patatas, 22),
                new Producto("Cereal", 2.90, R.drawable.cereal, 7),
                new Producto("Lentejas", 1.50, R.drawable.lentejas, 10),
                new Producto("Detergente", 4.00, R.drawable.detergente, 5),
                new Producto("Papel higiénico", 3.20, R.drawable.papel_higienico, 12),
                new Producto("Pasta de dientes", 2.00, R.drawable.pasta_de_dientes, 0),
                new Producto("Jabón líquido", 3.50, R.drawable.jabon_liquido, 8),
                new Producto("Papel de cocina", 1.80, R.drawable.papel_de_cocina, 0)
        }));

        return catalogo;

    }

}