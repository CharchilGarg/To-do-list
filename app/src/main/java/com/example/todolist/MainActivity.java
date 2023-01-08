package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText item;
    Button button;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.list);

        itemList = FileHelper.readData(MainActivity.this);

        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1
                ,android.R.id.text1,itemList);

        listView.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = item.getText().toString();
                item.setText("");

                itemList.add(s);
                FileHelper.writedata(itemList,MainActivity.this);
                arrayAdapter.notifyDataSetChanged();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want delete this item");
                alert.setCancelable(false);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemList.remove(p);
                        FileHelper.writedata(itemList,getApplicationContext());
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });

    }
}