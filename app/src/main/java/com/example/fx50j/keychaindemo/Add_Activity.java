package com.example.fx50j.keychaindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FX50J on 2016/3/5.
 */
public class Add_Activity extends AppCompatActivity {
    //add_item
    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        et1 = (EditText) findViewById(R.id.edit1);
        et2 = (EditText) findViewById(R.id.edit2);
        et3 = (EditText) findViewById(R.id.edit3);
        et4 = (EditText) findViewById(R.id.edit4);

        Button add = (Button) findViewById(R.id.save);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et1.getText().toString();
                String email = et2.getText().toString();
                String key = et3.getText().toString();
                String remake = et4.getText().toString();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String date = formatter.format(curDate);

                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("key",key);
                intent.putExtra("remake",remake);
                intent.putExtra("date",date);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
