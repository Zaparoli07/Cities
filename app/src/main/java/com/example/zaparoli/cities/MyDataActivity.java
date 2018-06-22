package com.example.zaparoli.cities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class MyDataActivity extends Fragment{

    public MyDataActivity(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_my_data,null,false);

        Button TESTE = (Button) view.findViewById(R.id.TESTE);

        TESTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Custom Toast From Fragment", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

}
