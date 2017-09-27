package com.example.adamgoldberg.beautifulbulldog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

public class NewBulldogActivity extends AppCompatActivity {

    private ImageButton addImage;
    private EditText name;
    private EditText age;
    private Button save;

    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bulldog);

        addImage = (ImageButton) findViewById(R.id.addImage);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        save = (Button) findViewById(R.id.save);

        realm = Realm.getDefaultInstance();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().matches("")
                        && !age.getText().toString().matches("")
                        && addImage.getDrawable() != null) {
                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm){
                            Bulldog bulldog = new Bulldog();
                            bulldog.setAge(age.getText().toString());
                            bulldog.setName(name.getText().toString());
                            bulldog.setId(realm.where(Bulldog.class).findAllSorted("id").last().getId() + 1);

                            BitmapDrawable image = (BitmapDrawable) addImage.getDrawable();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();
                            //set Image from image array
                            bulldog.setImage(imageInByte);

                            realm.copyToRealm(bulldog);
                            finish();
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addImage.setImageBitmap(imageBitmap);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //close the realm instance.
        realm.close();
    }
}
