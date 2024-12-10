package com.example.proyectoud3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCatalogo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCatalogo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "catalogo";

    // TODO: Rename and change types of parameters
    private ArrayList <Producto> catalogo = new ArrayList<>();

    public FragmentCatalogo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCatalogo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCatalogo newInstance(String param1, String param2) {
        FragmentCatalogo fragment = new FragmentCatalogo();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            catalogo = (ArrayList <Producto> ) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalogo, container, false);
        AdaptadorProducto.OnItemClickListenerCatalogo listener = (AdaptadorProducto.OnItemClickListenerCatalogo) getActivity();
        RecyclerView rvCatalogo = view.findViewById(R.id.rvCatalogo);

        //hace visible el recyclreView y aplica un padding-botom de 135dp, y se transporma de px a dp mediante el código de abajo
        int value = 135;
        int dpValue = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                FragmentCatalogo.this.getResources().getDisplayMetrics());

        rvCatalogo.setPadding(0, 0, 0, dpValue);
        rvCatalogo.setVisibility(View.VISIBLE);

        //crea y aplica el adaptador y el diseño al recyclerView
        AdaptadorProducto adaptadorProducto = new AdaptadorProducto(catalogo, listener);
        rvCatalogo.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rvCatalogo.setAdapter(adaptadorProducto);

        return view;
    }
}