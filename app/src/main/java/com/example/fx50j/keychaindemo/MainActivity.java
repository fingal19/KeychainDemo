package com.example.fx50j.keychaindemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//ListView 数组

    MyDatabaseHelper dbhelper = new MyDatabaseHelper(this,"data",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //add事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Activity.class);
                startActivityForResult(intent, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //oncreate方法里面读取
        ListView listView = (ListView) findViewById(R.id.listview);
        final ArrayList<KeyChainData> list = new ArrayList<KeyChainData>();
        MyAdapter adapter = new MyAdapter(this,0,list);
        listView.setAdapter(adapter);

        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query("data",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String key = cursor.getString(cursor.getColumnIndex("key"));
                String remake = cursor.getString(cursor.getColumnIndex("remake"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                KeyChainData keyChaindata = new KeyChainData(name,email,key,remake,date);
                list.add(keyChaindata);
                adapter.notifyDataSetChanged();
            }while (cursor.moveToNext());
        }
        cursor.close();


        //list_item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView_item = (ListView) parent;
                KeyChainData data = (KeyChainData) listView_item.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this,Item_Activity.class);
                intent.putExtra("name",data.getname());
                intent.putExtra("email",data.getemail());
                intent.putExtra("key",data.getKey());
                intent.putExtra("remake",data.getRemake());
                intent.putExtra("date",data.getDate());
                startActivityForResult(intent, 2);
            }
        });

    }

    //从add_activity中获得数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    String email = data.getStringExtra("email");
                    String key = data.getStringExtra("key");
                    String remake = data.getStringExtra("remake");
                    String date = data.getStringExtra("date");

                    SQLiteDatabase db = dbhelper.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("email",email);
                    values.put("key",key);
                    values.put("remake", remake);
                    values.put("date",date);
                    db.insert("data", null, values);

                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    String email = data.getStringExtra("email");
                    String key = data.getStringExtra("key");
                    String remake = data.getStringExtra("remake");
                    String date_old = data.getStringExtra("date_old");
                    String date_new = data.getStringExtra("date_new");

                    SQLiteDatabase db = dbhelper.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("email",email);
                    values.put("key",key);
                    values.put("remake",remake);
                    values.put("date",date_new);

                    db.update("data",values,"date=?",new String[]{date_old});
                }
                if (resultCode == RESULT_FIRST_USER){
                    String date = data.getStringExtra("date");
                    SQLiteDatabase db = dbhelper.getReadableDatabase();
                    db.delete("data","date=?",new String[]{date});
                }
            default:
        }
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        ListView listView = (ListView) findViewById(R.id.listview);
        ArrayList<KeyChainData> list = new ArrayList<KeyChainData>();
        MyAdapter adapter = new MyAdapter(this,0,list);
        listView.setAdapter(adapter);

        Cursor cursor = db.query("data",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String key = cursor.getString(cursor.getColumnIndex("key"));
                String remake = cursor.getString(cursor.getColumnIndex("remake"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                KeyChainData keyChaindata = new KeyChainData(name,email,key,remake,date);
                list.add(keyChaindata);
                adapter.notifyDataSetChanged();
            }while (cursor.moveToNext());
        }
        cursor.close();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 构造适配器
     */
    class KeyChainData {
        String name;
        String email;
        String key;
        String remake;
        String date;
        public KeyChainData(String name,String email,String key,String remake,String date){
            this.name = name;
            this.email = email;
            this.key = key;
            this.remake = remake;
            this.date = date;
        }
        public String getname(){
            return name;
        }
        public String getemail(){
            return email;
        }
        public String getKey(){
            return key;
        }
        public String getRemake(){
            return remake;
        }
        public String getDate(){
            return date;
        }
    }
    class MyAdapter extends ArrayAdapter<KeyChainData> {
        List<KeyChainData> list;
        public MyAdapter(Context context,int resource,List<KeyChainData> objects){
            super(context,resource,objects);
            this.list = objects;
        }

        @Override
        public long getItemId(int position) {
            return position ;
        }

        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_view,null);
            }else {
                view = convertView;
            }
            KeyChainData data = list.get(position);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView email = (TextView) view.findViewById(R.id.email);
//            TextView key = (TextView) view.findViewById(R.id.key);
//            TextView remake = (TextView) view.findViewById(R.id.remake);
            TextView date = (TextView) view.findViewById(R.id.date);

            name.setText(data.getname());
            email.setText(data.getemail());
//            key.setText(data.getKey());
//            remake.setText(data.getRemake());
            date.setText(data.getDate());
            return view;
        }
    }
    /**
     * 创建数据库
     */
    class MyDatabaseHelper extends SQLiteOpenHelper {

        public static final String CREATE_DATA = "create table data(" +
                "id integer primary key autoincrement," +
                "name text," +
                "email text," +
                "key text," +
                "remake text," +
                "date text)";
        public MyDatabaseHelper (Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATA);
            Toast.makeText(MainActivity.this, "data数据库创建成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

