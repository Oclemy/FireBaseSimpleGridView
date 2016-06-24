package com.tutorials.hp.firebasesimplegridview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tutorials.hp.firebasesimplegridview.m_FireBase.FirebaseHelper;
import com.tutorials.hp.firebasesimplegridview.m_Model.Spacecraft;

public class MainActivity extends AppCompatActivity {

    GridView gv;
    ArrayAdapter<String> adapter;
    DatabaseReference db;
    FirebaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gv= (GridView) findViewById(R.id.gv);

        //SETUP FIREBASE
        db= FirebaseDatabase.getInstance().getReference();
        helper=new FirebaseHelper(db);

        //adapter
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,helper.retrieve());
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, helper.retrieve().get(position), Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 displayInputDialog();
            }
        });
    }

    private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Rirebase");
        d.setContentView(R.layout.input_dialog);

        final EditText nameEditTxt= (EditText) d.findViewById(R.id.nameEditText);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String name=nameEditTxt.getText().toString();

                //SET DATA
                Spacecraft s=new Spacecraft();
                s.setName(name);

                //VALIDATE
                if(name != null && name.length()>0)
                {
                    if(helper.save(s))
                    {
                        nameEditTxt.setText("");
                        adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,helper.retrieve());
                        gv.setAdapter(adapter);

                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Name Cannot be empty", Toast.LENGTH_SHORT).show();
                }


            }
        });

        d.show();
    }


}



















