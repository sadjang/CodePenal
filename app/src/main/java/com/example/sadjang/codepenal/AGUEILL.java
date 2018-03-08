package com.example.sadjang.codepenal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AGUEILL extends AppCompatActivity {

    EditText editTextn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agueill);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CODE PENAL DU CAMEROUN");
        LinearLayout recherArt = (LinearLayout) findViewById(R.id.recherArt);
        LinearLayout detail = (LinearLayout) findViewById(R.id.detail);
        LinearLayout apropos = (LinearLayout) findViewById(R.id.apropos);
        LinearLayout sommaire = (LinearLayout) findViewById(R.id.sommaire);
        ImageView imageSearch = ( ImageView) findViewById(R.id.imageSearch);
        editTextn = (EditText) findViewById(R.id.editTextn);
        recherArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AGUEILL.this,Search_Article.class);
                startActivity(i);
            }

        });
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AGUEILL.this,Detail_Article.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
            }

        });
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AGUEILL.this,Detail_Article.class);
                String numero=editTextn.getText().toString();
                if(numero.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Entrez un Numero d'article", Toast.LENGTH_LONG).show();
                }else {
                    int numer = Integer.parseInt(numero);
                    if((numer>0)&&(numer<373)) {
                        i.putExtra("Numero", numero);
                        startActivity(i);
                        // overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
                    }else{
                        Toast.makeText(getApplicationContext(),"Cet Article n'existe pas encore", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });

        apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AGUEILL.this,About.class);
                startActivity(i);
                // overridePendingTransition(R.anim.push_down_in, R.anim.push_down_in);
            }

        });
        sommaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AGUEILL.this,Sommaire.class);
                startActivity(i);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
            }

        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String stateSaved=savedInstanceState.getString("saved_state");
        if(stateSaved==null){
            //Toast.makeText(getApplicationContext(),"etat non restaure",Toast.LENGTH_LONG).show();
        }else {
            // Toast.makeText(getApplicationContext(),"restoration de letat",Toast.LENGTH_LONG).show();
            editTextn.setText(stateSaved);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String stateToSave=editTextn.getText().toString();
        outState.putString("saved_state",stateToSave);
        // Toast.makeText(getApplicationContext(),"enregistrement de letat"+stateToSave,Toast.LENGTH_LONG).show();

    }
}

