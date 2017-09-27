package com.example.adamgoldberg.beautifulbulldog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText emailField;
    private EditText passwordField;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        emailField = (EditText)findViewById(R.id.email_field);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                login("cs188", "MyBeautifulBulldogApp", emailField.getText().toString());
            }
        });

    }

    private void login(final String email, final String password, final String username) {
        if(SyncUser.currentUser()!= null) {

            Realm realm = Realm.getDefaultInstance();
            if(realm != null) {
                realm.close();
                Realm.deleteRealm(realm.getConfiguration());

                SyncUser.currentUser().logout();
            }


        }
        SyncCredentials myCredentials = SyncCredentials.usernamePassword(email, password, false);
        SyncUser.loginAsync(myCredentials, "http://52.205.194.154:9080", new SyncUser.Callback() {
            @Override
            public void onSuccess(SyncUser user) {
                SyncConfiguration configuration = new SyncConfiguration
                        .Builder(user, "realm://52.205.194.154:9080/~/bulldog")
                        .disableSSLVerification().waitForInitialRemoteData().schemaVersion((long) 12.0).build();
                Realm.setDefaultConfiguration(configuration);

                Realm.getInstanceAsync(configuration, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {

                        //successfully created a user
                        final User user = new User();
                        user.setUsername(username);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(user);
                            }
                        });

                        realm.close();

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(ObjectServerError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}