package com.example.proyectoud3;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdaptadorProducto.OnItemClickListenerCatalogo, AdaptadorCesta.OnItemClickListenerCesta{

    ArrayList <Producto> productos; // se almacenan los productos totales del catálogo
    ArrayList <Producto> cesta = new ArrayList<>(); //se almacenan los productos que se agregan a la cesta

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

        //asociar menu contextual
        TextView textView1 = (TextView) findViewById(R.id.tvCabecera);

        // Asociamos un menú contextual al textView
        registerForContextMenu(textView1);

        //se añade el onClick Listener al tv para el menu popup
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(view);
            }
        });

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);


        productos = cargarProductos (); //se cargan los productos y se guardan en la lista

        ocultarSwitch(); //se oculta el switch ya que al no mostrar productos al principio de la ejecución, no tiene sentido mostrar el filtro

        //se crea el switch y se crea el Listener
        Switch switch1 = findViewById(R.id.switchTodo);
        switch1.setChecked(false);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //en caso de estar activo, solo tendrá que mostrar los productos disponibles (ud. restantes > 0)
                if (isChecked){
                    ArrayList <Producto> disponibles = new ArrayList <Producto> (); //lista que guarda los productos dispononibles
                    for(Producto p : productos){
                        //si las ud. restantes > 0 se agrega a la lista de disponibles
                        if(p.getUdRestantes() > 0){
                            disponibles.add(p);
                        }
                    }

                    cargarRecyclerViewCatalogo(disponibles); //carga el recyclerView del catálogo actualizado con los productos disponibles

                }
                else {
                    //en caso de no estar activo, se cargarán todos los productos del catálogo
                    cargarRecyclerViewCatalogo(productos); //carga el recyclerView con todoo el catálogo

                }

            }

        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        //se añaden las opciones del tabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 1){

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), getString(R.string.proximamente), Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Acción al deseleccionar una pestaña
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Acción al volver a seleccionar una pestaña
            }

        });

    }

    //Método para asociar un menú emergente popup al pulsar en una vista
    public void showPopUpMenu(View view) {

        PopupMenu popupMenu = new PopupMenu(this,view);

        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.mi_menu, popupMenu.getMenu());

        // Manejador de clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.op1){
                    Toast.makeText(MainActivity.this, getString(R.string.opcion1), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.opcion2), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        // mostrarlo
        popupMenu.show();

    }

    // Método para el menú contextual, donde sew asocia el menú contrextual al textView
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Context Menu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mi_menu, menu);
    }

    // Método para gestionar los eventos de los elementos del menú contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.op1) {

            Toast.makeText(MainActivity.this, getString(R.string.opcion1), Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(MainActivity.this, getString(R.string.opcion2), Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    //metodo que gestiona el click de botón de añadir al carrito
    @Override
    public void onItemClickCatalogo(View view, int position) {
        Producto producto = productos.get(position); //crea el producto en base a los datos del cardView donde se hizo click
        if(producto.getUdRestantes() > 0){
            cesta.add(producto);
            Toast.makeText(MainActivity.this, producto.getNombre() + " " + getString(R.string.txtAgregadoCorrecto), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.txtNoHay)  + " " + producto.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    //método que gestiona el click de botón eliminar del carrito
    @Override
    public void onItemClickCesta(View view, int position) {

        //si la cesta no está vacía ejecuta el bloque de código
        if(!cesta.isEmpty()){

            //borra el producto de la cesta
            Producto producto = cesta.get(position);
            cesta.remove(producto);

            //carca el recyclerView con la cesta actualizada
            cargarRecyclerViewCesta(cesta);

            //lanza snackBar con mensaje de información
            Snackbar.make(findViewById(R.id.main), producto.getNombre()  + " " +  getString(R.string.txtEliminar), Snackbar.LENGTH_SHORT).show();

            //si la cesta desopues de eliminar algun producto queda vacía, entra a este bloque de código
            if(cesta.isEmpty()){

                //hace visible la etiqueta de información
                TextView tv_cestaVacia = findViewById(R.id.tv_cestaVacia);
                tv_cestaVacia.setVisibility(View.VISIBLE);

                //hace invidible el recyclerView, y cambia el padding para que la etiqueta aparezca arriba
                RecyclerView rvCatalogo = findViewById(R.id.rvCatalogo);
                rvCatalogo.setPadding(0, 0, 0, 0);
                rvCatalogo.setVisibility(View.INVISIBLE);

            }

        }

    }

    //método que gestiona el adaptador que utiliza el recyclerView para saber si se está en el catálogo o en la cesta
    public void cambiarVista(View view) {

        Button boton = findViewById(view.getId());
        Log.i(getString(R.string.prueba), boton.getText().toString());
        Log.i(getString(R.string.prueba), getString(R.string.btnCatalogo));

        /*
            si el botón tiene de texto 'catálogo' (o su valor equivalente, dependiendo del idioma),
            quiere decir que tiene que mostrar el catálogo, ejecuta este bloque de código
         */
        if(boton.getText().toString().equals(getString(R.string.btnCatalogo))){

            //si el arrayList de productos está vacío no se carga
            if(!productos.isEmpty()){

                //muestra el switch con filtro, carga el recyclerView con el catálogo y cambia el texto del botón
                mostrarSwitch();
                cargarRecyclerViewCatalogo(productos);
                boton.setText(getString(R.string.btnCesta));

            }

        } else {

            /*
                en el caso de que el texto no sea 'catálogo' (o su valor equivalente, dependiendo del idioma),
                quiere decir que tiene que mostrar la cesta
             */

            //si la cesta no está vacía ejecuta el bloque de código
            if(!cesta.isEmpty()){

                //oculta el switch, carga el recyclerView con los productos en la cesta y cambia el texto del botón
                ocultarSwitch();
                cargarRecyclerViewCesta(cesta);
                boton.setText(getString(R.string.btnCatalogo));

            } else {
                //lanza toast de información en caso de estar la cesta vacía
                Toast.makeText(MainActivity.this, getString(R.string.cestaVacia), Toast.LENGTH_SHORT).show();
            }

        }

    }

    //método que sirve para ocultar el switch del filtro
    private void ocultarSwitch(){

        TextView tvTodo = findViewById(R.id.tvSwitchTodo);
        TextView tvDisponible = findViewById(R.id.tvSwitchDisponible);
        Switch switch1 = findViewById(R.id.switchTodo);

        tvTodo.setVisibility(View.INVISIBLE);
        tvDisponible.setVisibility(View.INVISIBLE);
        switch1.setVisibility(View.INVISIBLE);

    }

    //método que sirve para mostrar el switch
    private void mostrarSwitch(){

        TextView tvTodo =findViewById(R.id.tvSwitchTodo);
        TextView tvDisponible =findViewById(R.id.tvSwitchDisponible);
        Switch switch1 = findViewById(R.id.switchTodo);

        tvTodo.setVisibility(View.VISIBLE);
        tvDisponible.setVisibility(View.VISIBLE);
        switch1.setVisibility(View.VISIBLE);
    }

    //método que carga el recyclerView con el adaptador del catálogo
    private void cargarRecyclerViewCatalogo(ArrayList <Producto> productos){

        RecyclerView rvCatalogo = findViewById(R.id.rvCatalogo);

        //hace visible el recyclreView y aplica un padding-botom de 135dp, y se transporma de px a dp mediante el código de abajo
        int value = 180;
        int dpValue = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                MainActivity.this.getResources().getDisplayMetrics());

        rvCatalogo.setPadding(0, 0, 0, dpValue);
        rvCatalogo.setVisibility(View.VISIBLE);

        int orientation = MainActivity.this.getResources().getConfiguration().orientation;

        if(orientation == 1){ //vertical
            //crea y aplica el adaptador y el diseño al recyclerView
            AdaptadorProducto adaptadorProducto = new AdaptadorProducto(productos, MainActivity.this);
            rvCatalogo.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            rvCatalogo.setAdapter(adaptadorProducto);
        } else if (orientation == 2){ //horizontal
            //crea y aplica el adaptador y el diseño al recyclerView
            AdaptadorProducto adaptadorProducto = new AdaptadorProducto(productos, MainActivity.this);
            rvCatalogo.setLayoutManager(new GridLayoutManager(MainActivity.this, 5));
            rvCatalogo.setAdapter(adaptadorProducto);
        }

    }

    //método que carga el recyclerView con el adaptador de la cesta
    private void cargarRecyclerViewCesta(ArrayList <Producto> cesta){

        RecyclerView rvCatalogo = findViewById(R.id.rvCatalogo);

        //hace visible el recyclreView y aplica un padding-botom de 135dp, y se transporma de px a dp mediante el código de abajo
        int value = 180;
        int dpValue = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                MainActivity.this.getResources().getDisplayMetrics());

        rvCatalogo.setPadding(0, 0, 0, dpValue);
        rvCatalogo.setVisibility(View.VISIBLE);

        //crea y aplica el adaptador y el diseño al recyclerView
        AdaptadorCesta adaptadorCesta = new AdaptadorCesta(cesta, MainActivity.this);
        rvCatalogo.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rvCatalogo.setAdapter(adaptadorCesta);

    }

    //método que devuelve el arrayList con todos los productos del catálogo
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