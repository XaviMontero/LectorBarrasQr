package com.example.lectorbarrasqr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lectorbarrasqr.model.PostService;
import com.example.lectorbarrasqr.model.Productos;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CHANGE_CONFIGURATION;
import static android.Manifest.permission.INTERNET;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private EditText user;
    private EditText password;
    private Button sumit;
    String API_BASE_URL="http://172.16.35.97:8080/prueba/api/v1.0/producto/011/busca/";
    ListView list;
    Productos p = null;

    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.text_user);
        password = findViewById(R.id.text_password);
        sumit = findViewById(R.id.btn_sumit);
        sumit.setOnClickListener(this);

        if(Utility.checkAndRequestPermissions(this)){
            getPosts();
        }else {
            Utility.showDialogOK(this,"Acede alos permisos");
        }
        // getPosts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // do work
                }else{
                    // take action
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_sumit:
                if (password.getText().length()>=8){
                    if (user.getText().length()>=8){

                        Intent intent = new Intent(this, ScannerActivity.class);
                        startActivity(intent);

                    }else{
                        MDToast mdToast = MDToast.makeText(this, "Error User 8 caracteres minimo",  100, 3);
                        mdToast.show();

                    }
                }else {
                    MDToast mdToast = MDToast.makeText(this, "Error Password 8 caracteres minimo",  100, 3);
                   mdToast.show();



                }

                break;
        }

    }
    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<Productos>> call = postService.getPost();

        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                for(Productos post : response.body()) {
                    titles.add(post.getId());
                   p=post;

                }

            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
            }
        });

    }

}
