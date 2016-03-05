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
public class Edit_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_edit);

        Intent intent = getIntent();

        final EditText et_name_et = (EditText) findViewById(R.id.et_name_et);
        final EditText et_email_et = (EditText) findViewById(R.id.et_email_et);
        final EditText et_key_et = (EditText) findViewById(R.id.et_key_et);
        final EditText et_remake_et = (EditText) findViewById(R.id.et_remake_et);

        String name_1 = intent.getStringExtra("name");
        String email_1 = intent.getStringExtra("email");
        String key_1 = intent.getStringExtra("key");
        String remake_1 = intent.getStringExtra("remake");
        final String date_old = intent.getStringExtra("date");

        et_name_et.setText(name_1);
        et_email_et.setText(email_1);
        et_key_et.setText(key_1);
        et_remake_et.setText(remake_1);

        Button btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                String name  = et_name_et.getText().toString();
                String email = et_email_et.getText().toString();
                String key = et_key_et.getText().toString();
                String remake = et_remake_et.getText().toString();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String date_new = formatter.format(curDate);

                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("key",key);
                intent.putExtra("remake",remake);
                intent.putExtra("date_old",date_old);
                intent.putExtra("date_new",date_new);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}

