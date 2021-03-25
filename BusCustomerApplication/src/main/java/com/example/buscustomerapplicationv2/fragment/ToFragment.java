package com.example.buscustomerapplicationv2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.NewQrBookTicketActivity;
import com.example.buscustomerapplicationv2.adapter.MyArrayAdapter;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;
import static android.content.ContentValues.TAG;
import static java.util.Objects.requireNonNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ToFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToFragment newInstance(String param1, String param2) {
        ToFragment fragment = new ToFragment();
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
        View view = inflater.inflate(R.layout.fragment_to, container, false);

        EditText inputText = view.findViewById(R.id.et_to);
        TextView fromTV = view.findViewById(R.id.tv_from);
        ListView lv = view.findViewById(R.id.listview_to);

        ArrayList<String> arrayList = new ArrayList<String>(requireNonNull(requireArguments().getStringArrayList("list")));
        assert getArguments() != null;
        int positions = getArguments().getInt("position");
        String value=getArguments().getString("value");


        // Adding items to listview
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(requireActivity(),
                        simple_list_item_1,
                        arrayList);

        lv.setAdapter(adapter);
//        lv.getAdapter().getItemId(positions);
//lv.getAdapter().get

        fromTV.setText(String.valueOf(value));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (!lv.getAdapter().getItem(i).toString().equals(value)) {
                    Intent intent = new Intent(getActivity(), NewQrBookTicketActivity.class);
                    intent.putExtra("from", fromTV.getText().toString());
                    String to = (String) lv.getAdapter().getItem(i);
                    intent.putExtra("to", to);
                    Log.w(TAG, "to " + to);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    requireActivity().finish();
                }
            }
        });



        inputText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        for (int i = 0; i <= lv.getLastVisiblePosition() - lv.getFirstVisiblePosition(); i++)
        {
            if (lv.getChildAt(i-lv.getFirstVisiblePosition()).toString().equals(value)){
//            lv.getItemAtPosit
                lv.getChildAt(i - lv.getFirstVisiblePosition()).setEnabled(false);
            adapter.getItemViewType(i);
//            lv.getAdapter().
            Log.w(TAG, "onCreateView: "+lv.getChildAt(i) );
        }
        }

            return view;
    }


}