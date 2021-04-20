package com.example.buscustomerapplicationv2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.buscustomerapplicationv2.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import static android.R.layout.simple_list_item_1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FromFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FromFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FromFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FromFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FromFragment newInstance(String param1, String param2) {
        FromFragment fragment = new FromFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_from, container, false);
    EditText input = view.findViewById(R.id.et_from);
        ListView listView=view.findViewById(R.id.listview);


        assert getArguments() != null;
        ArrayList<String> arrayList=getArguments().getStringArrayList("list");

        // Adding items to listview
        assert arrayList != null;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(),
                simple_list_item_1,
                arrayList);
        listView.setAdapter(adapter);
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setEnabled(false);
//                listView.getChildAt(i).setClickable(false);
//                listView.getChildAt(i).setEnabled(false);
                Fragment fragment=new ToFragment();
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("list", arrayList);
                bundle.putInt("position",i);
                bundle.putString("value",listView.getAdapter().getItem(i).toString());

                fragment.setArguments(bundle);

                FragmentManager fm=getParentFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right,R.animator.hyperspace_slide_in_left,R.animator.hyperspace_slide_out_right);
                ft.replace(R.id.fragment_container_view_tag,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

    return view;
    }
}