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
 * Use the {@link FragmentCesta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCesta extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "cesta";

    // TODO: Rename and change types of parameters
    private ArrayList <Producto> cesta = new ArrayList<>();

    public FragmentCesta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentCesta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCesta newInstance(ArrayList <Producto> param1) {
        FragmentCesta fragment = new FragmentCesta();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cesta = (ArrayList <Producto>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cesta, container, false);
        AdaptadorCesta.OnItemClickListenerCesta listener = (AdaptadorCesta.OnItemClickListenerCesta) getActivity();
        RecyclerView rvCatalogo = view.findViewById(R.id.rvCesta);

        //hace visible el recyclreView y aplica un padding-botom de 135dp, y se transporma de px a dp mediante el código de abajo
        int value = 170;
        int dpValue = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                FragmentCesta.this.getResources().getDisplayMetrics());

        rvCatalogo.setPadding(0, 0, 0, dpValue);
        rvCatalogo.setVisibility(View.VISIBLE);

        //crea y aplica el adaptador y el diseño al recyclerView
        AdaptadorCesta adaptadorCesta = new AdaptadorCesta(cesta, listener);
        rvCatalogo.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rvCatalogo.setAdapter(adaptadorCesta);

        return view;
    }
}