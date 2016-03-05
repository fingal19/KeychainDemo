package com.example.fx50j.keychaindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by FX50J on 2016/3/5.
 */
public class Item_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String key = intent.getStringExtra("key");
        final String remake = intent.getStringExtra("remake");
        final String date = intent.getStringExtra("date");

        final TextView name_et = (TextView) findViewById(R.id.et_name);
        final TextView email_et = (TextView) findViewById(R.id.et_email);
        final TextView key_et = (TextView) findViewById(R.id.et_key);
        final TextView remake_et = (TextView) findViewById(R.id.et_remake);
        final TextView date_et = (TextView) findViewById(R.id.et_date);

        name_et.setText(name);
        email_et.setText(email);
        key_et.setText(key);
        remake_et.setText(remake);
        date_et.setText(date);

        Button del = (Button) findViewById(R.id.del_sql);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("date",date);
//                intent.putExtra("email",email);
//                intent.putExtra("key",key);
//                intent.putExtra("remake",remake);
                setResult(RESULT_FIRST_USER,intent);
                finish();
            }
        });

        Button et_sql = (Button) findViewById(R.id.edit_sql);
        et_sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Item_Activity.this,Edit_Activity.class);

                String name_1  = name_et.getText().toString();
                String email_1 = email_et.getText().toString();
                String key_1 = key_et.getText().toString();
                String remake_1 = remake_et.getText().toString();
                String date_1 = date_et.getText().toString();

                intent.putExtra("name",name_1);
                intent.putExtra("email",email_1);
                intent.putExtra("key",key_1);
                intent.putExtra("remake",remake_1);
                intent.putExtra("date",date_1);

                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 3:
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent();
                    String name = data.getStringExtra("name");
                    String email = data.getStringExtra("email");
                    String key = data.getStringExtra("key");
                    String remake = data.getStringExtra("remake");
                    String date_old = data.getStringExtra("date_old");
                    String date_new = data.getStringExtra("date_new");

                    TextView name_et = (TextView) findViewById(R.id.et_name);
                    TextView email_et = (TextView) findViewById(R.id.et_email);
                    TextView key_et = (TextView) findViewById(R.id.et_key);
                    TextView remake_et = (TextView) findViewById(R.id.et_remake);
                    TextView date_et = (TextView) findViewById(R.id.et_date);

                    name_et.setText(name);
                    email_et.setText(email);
                    key_et.setText(key);
                    remake_et.setText(remake);
                    date_et.setText(date_new);

                    intent.putExtra("name", name);
                    intent.putExtra("email",email);
                    intent.putExtra("key",key);
                    intent.putExtra("remake", remake);
                    intent.putExtra("date_old",date_old);
                    intent.putExtra("date_new",date_new);
                    setResult(RESULT_OK, intent);
                }
        }
    }
}

