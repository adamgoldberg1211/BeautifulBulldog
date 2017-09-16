package com.example.adamgoldberg.beautifulbulldog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;

public class BulldogActivity extends AppCompatActivity {

    private TextView textView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        textView = (TextView) findViewById(R.id.textView);
        realm = Realm.getDefaultInstance();
        String id = (String) getIntent().getStringExtra("bulldog");

        Bulldog bulldog = realm.where(Bulldog.class).equalTo("id", id).findFirst();
        textView.setText(bulldog.getName());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close the Realm instance.
        realm.close();
    }
}
