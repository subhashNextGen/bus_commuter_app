package com.example.buscustomerapplicationv2.helper;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class IncreamentDecreament {


    private TextView textView;
    private int number;
    private View v;

    public IncreamentDecreament(Context context) {
    }

    public void increase(TextView text) {
        textView = text;
        number = Integer.parseInt(textView.getText().toString());
        if (number < 10) {
            number = number + 1;
        }
        display(number);
    }

    private void display(int number) {
        textView.setText(String.valueOf(number));
    }

    public void decrease(TextView text) {
        textView = text;

        number = Integer.parseInt(textView.getText().toString());
        if (number != 0) {
            number = number - 1;
        }
        display(number);
    }


}
