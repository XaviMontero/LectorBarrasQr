package com.example.lectorbarrasqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
    String API_BASE_URL="http://172.16.35.97:8080/prueba/api/v1.0/producto/";
    ListView list;
    Productos p = null;
    EditText txt_codigo,text_nombre,text_decripcion,text_precio_unitario,txt_stok,ubic,precio_mayorista;


    ArrayList<String> titles = new ArrayList<>();
    private void referece() {
        txt_codigo=findViewById(R.id.txt_codigo);
        text_nombre= findViewById(R.id.text_nombre);
        text_decripcion=findViewById(R.id.text_decripcion);
        text_precio_unitario=findViewById(R.id.text_precio_unitario);
        txt_stok=findViewById(R.id.txt_stok);
        ubic=findViewById(R.id.ubic);
        precio_mayorista=findViewById(R.id.precio_mayorista);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referece();
        setContentView(R.layout.activity_scanner);
        scannear = findViewById(R.id.btn_scannear);
        descripcion = findViewById(R.id.text_decripcion);
        codigoBarras = findViewById(R.id.txt_codigo);
        scannear.setOnClickListener(this);
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("COD_BARRA");
                MDToast mdToast = MDToast.makeText(this, newString ,  1000, 1);
                mdToast.show();
                getPosts();
                if(p!=null){
                    txt_codigo.setText(p.getId());
                    text_nombre.setText(p.getProNombre());
                    text_decripcion.setText(p.getProUbi());
                    text_precio_unitario.setText(String.valueOf(p.getProPreVen()));
                    txt_stok.setText(String.valueOf(p.getProSto()));
                    ubic.setText(String.valueOf(p.getProObs()));
                    precio_mayorista.setText(String.valueOf(p.getProPreMay()));
                }else {
                    MDToast mdToasts = MDToast.makeText(this, "No existe producto" ,  1000, 1);
                    mdToasts.show();
                }
                newString= null;

            }
        } else {
            newString= (String) savedInstanceState.getSerializable("COD_BARRA");
            MDToast mdToast = MDToast.makeText(this, newString ,  1000, 1);
            mdToast.show();
            getPosts();
            if(p!=null){
                txt_codigo.setText(p.getId());
                text_nombre.setText(p.getProNombre());
                text_decripcion.setText(p.getProUbi());
                text_precio_unitario.setText(String.valueOf(p.getProPreVen()));
                txt_stok.setText(String.valueOf(p.getProSto()));
                ubic.setText(String.valueOf(p.getProObs()));
                precio_mayorista.setText(String.valueOf(p.getProPreMay()));
            }else {
                MDToast mdToasts = MDToast.makeText(this, "No existe producto" ,  1000, 1);
                mdToasts.show();
            }
            newString= null;
        }


    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            mScannerView.stopCamera();           // Stop camera on pause

        }catch (NullPointerException exc){
            MDToast mdToast = MDToast.makeText(this, "Gracias" ,  1000, 1);
            mdToast.show();
        }
    }

    @Override
    public void handleResult(Result rawResult) {




        codigoBarras.setText(rawResult.getText());
        descripcion.setText(rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);

        Intent i = new Intent( this, ScannerActivity.class);
        String strName = rawResult.getText();
        i.putExtra("COD_BARRA", strName);
        startActivity(i);
        this.finish();




    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<Productos>> call = postService.getPost("011");
            String ruta = postService.toString();


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



    @Override
    public void onClick(View v) {

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume

    }
}
