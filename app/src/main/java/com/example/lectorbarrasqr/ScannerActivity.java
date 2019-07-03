package com.example.lectorbarrasqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lectorbarrasqr.model.PostService;
import com.example.lectorbarrasqr.model.Productos;
import com.google.zxing.Result;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {
    ZXingScannerView mScannerView;
    Button scannear;
    EditText codigoBarras;
    EditText descripcion;

    ListView list;
    ArrayList<String> titles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanner);
        scannear = findViewById(R.id.btn_scannear);
        descripcion = findViewById(R.id.text_decripcion);
        codigoBarras = findViewById(R.id.txt_codigo);
        scannear.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        MDToast mdToast = MDToast.makeText(this, rawResult.getText() ,  1000, 1);
        mdToast.show();


        codigoBarras.setText(rawResult.getText());
        descripcion.setText(rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
        startActivity(new Intent(this,ScannerActivity.class));
        this.finish();




    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/prueba/api/v1.0/producto/011/busca")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<Productos>> call = postService.getPost();

        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                for(Productos post : response.body()) {
                    titles.add(post.getId());
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), post.getProNombre() ,  1000, 1);
                    mdToast.show();
                }
             }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }
}
