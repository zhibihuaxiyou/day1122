package com.bwie.day1122;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * author：张腾
 * date：2018/11/22
 */
public class LiuShiActivity extends AppCompatActivity {

    private String names[] = {"黄焖鸡", "麻辣烫", "盖饭", "披萨", "鸡丁饭", "米线", "饺子", "披萨", "武汉黄焖鸡", "黄焖鸡", "黄焖鸡"};

    private ImageView delete;
    private EditText editText;
    private Button button;
    private FlowView hot_flow, main_flow;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flow);
        editText = findViewById(R.id.main_edit);
        button = findViewById(R.id.main_butt);
        hot_flow = findViewById(R.id.hot);
        main_flow = findViewById(R.id.main_flow);
        delete = findViewById(R.id.delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.rightMargin = 5;
                lp.topMargin = 5;
                lp.bottomMargin = 5;
                lp.leftMargin = 5;
                String edit_text = editText.getText().toString();
                list.add(edit_text);
                TextView textView = new TextView(LiuShiActivity.this);
                textView.setText(list.get(list.size() - 1));
                textView.setTextColor( Color.BLACK);
                main_flow.addView(textView, lp);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new ArrayList<>();
                main_flow.removeAllViews();
            }
        });
        initData();
        main_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LiuShiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 2;
        lp.leftMargin = 5;
        for (int i = 0; i < names.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(names[i]);
            textView.setTextColor(Color.BLACK);
            hot_flow.addView(textView, lp);
        }
    }
}

