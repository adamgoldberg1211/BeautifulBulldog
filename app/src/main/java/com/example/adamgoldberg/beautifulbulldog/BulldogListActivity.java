package com.example.adamgoldberg.beautifulbulldog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;

public class BulldogListActivity extends AppCompatActivity {
    private ListView bulldogList;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog_list);

        bulldogList = (ListView) findViewById(R.id.bulldog_list);
        realm = Realm.getDefaultInstance();

        BulldogArrayAdapter adapter = new BulldogArrayAdapter(this, realm.where(Bulldog.class).findAll());
        bulldogList.setAdapter(adapter);

        bulldogList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                final Bulldog bulldog = (Bulldog) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), BulldogActivity.class);
                intent.putExtra("bulldog", bulldog.getId());
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close the Realm instance.
        realm.close();
    }
}
