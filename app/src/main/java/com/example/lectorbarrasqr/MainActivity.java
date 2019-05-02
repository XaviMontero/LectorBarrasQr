package com.example.lectorbarrasqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.valdesekamdem.library.mdtoast.MDToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText user;
    private EditText password;
    private Button sumit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.text_user);
        password = findViewById(R.id.text_password);
        sumit = findViewById(R.id.btn_sumit);
        sumit.setOnClickListener(this);
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
}
