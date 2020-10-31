package com.raysk.linearprogramming.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.raysk.linearprogramming.R;
import com.raysk.linearprogramming.graphics.DinamicTable;
import com.raysk.linearprogramming.logic.Simplex;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    double[][] matrix;
    int index;

    public PlaceholderFragment(int index){
        this.index = index;
    }

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment placeholderFragment = new PlaceholderFragment(index);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER,index);
        placeholderFragment.setArguments(bundle);

        return placeholderFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        //id de pagina
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        //dibujar tabla
        Simplex simplex = new Simplex(null);
        DinamicTable dinamicTable = new DinamicTable((TableLayout)root.findViewById(R.id.tabla),getContext(),simplex.getMatrix());
        dinamicTable.get();


        return root;
    }
}