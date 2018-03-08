package com.example.sadjang.codepenal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadjang.codepenal.Objets.Article;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Detail_Article extends AppCompatActivity {
    Context c;
    private int cn;
    private InputStream inputStream;
    private BufferedReader reader;
    public static ArrayList<Article> arrayTab = new ArrayList<Article>();
    RecyclerView rv;
    TextView titre_arti,text_arti,numeros,numDepart,numFin;
    ImageView prec,prec1,suiv,suiv1;
    android.widget.SearchView sv;
    String numeroString=null;
    LinearLayout layoutdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__article);
        getSupportActionBar().setTitle("     CODE PENAL DU CAMEROUN");
        c=this;
        titre_arti=(TextView)findViewById(R.id.titre_arti);
        text_arti=(TextView)findViewById(R.id.text_art);
        layoutdetail=(LinearLayout)findViewById(R.id.layoutdetail);

        numeros=(TextView)findViewById(R.id.numero);
        numDepart=(TextView)findViewById(R.id.numDepart);
        numFin=(TextView)findViewById(R.id.numFin);
        prec=(ImageView)findViewById(R.id.prec);
        //prec1=(ImageView)findViewById(R.id.prec1);
        suiv=(ImageView)findViewById(R.id.suiv);
        //suiv1=(ImageView)findViewById(R.id.suiv1);
        sv= (android.widget.SearchView)findViewById(R.id.searchView1);
        Gson gson = new Gson();
        final Article article = gson.fromJson(getIntent().getStringExtra("ArticleDetail"), Article.class);
        if (article != null) {
            int num = article.getNumero();
            int numD =  article.getNumDepart();
            int numF =  article.getNumFin();
            LoadFichierPreciss (numD,num, numF);
        }else {
            Intent i=this. getIntent();
            numeroString=i.getStringExtra("Numero");
            if(numeroString!=null) {
                int numero = Integer.parseInt(numeroString);
                LoadFichierPreciss (0,numero,373);
            }else {
                LoadFichierPreciss(0, 1, 373);
            }
        }




        prec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt( numeros.getText().toString());
                int numD=Integer.parseInt(numDepart.getText().toString());
                int numF=Integer.parseInt( numFin.getText().toString());
                if(numD<num-1)
                    LoadFichierPreciss (numD,num-1, numF);
            }
        });
        suiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt( numeros.getText().toString());
                int numD=Integer.parseInt(numDepart.getText().toString());
                int numF=Integer.parseInt( numFin.getText().toString());
                if(numF>num+1)
                    LoadFichierPreciss (numD,num+1, numF);
            }
        });
     /*   prec1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt( numeros.getText().toString());
                int numD=Integer.parseInt(numDepart.getText().toString());
                int numF=Integer.parseInt( numFin.getText().toString());
                if(numD<num-1)
                    LoadFichierPreciss (numD,num-1, numF);
            }
        });
        suiv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt( numeros.getText().toString());
                int numD=Integer.parseInt(numDepart.getText().toString());
                int numF=Integer.parseInt( numFin.getText().toString());
                if(numF>num+1)
                    LoadFichierPreciss (numD,num+1, numF);
            }
        });*/

        // implementation de la  rechercher

        sv.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return false;
            }
        });
// fin  DE LA recherche

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String stateSaved=savedInstanceState.getString("saved_state");
        String stateSavedd=savedInstanceState.getString("saved_stated");
        String stateSavedf=savedInstanceState.getString("saved_statef");
        int num=Integer.parseInt(stateSaved);
        int numD=Integer.parseInt(stateSavedd);
        int numF=Integer.parseInt(stateSavedf);
        if(stateSaved==null){
            // Toast.makeText(getApplicationContext(),"etat non restaure",Toast.LENGTH_LONG).show();
        }else {
            // Toast.makeText(getApplicationContext(),"restoration de letat:"+stateSaved,Toast.LENGTH_LONG).show();
            // sv.setOnQueryTextListener(stateSaved));
            LoadFichierPreciss (numD,num, numF);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String stateToSave= numeros.getText().toString();
        String stateToSaveD= numDepart.getText().toString();
        String stateToSavef= numFin.getText().toString();
        outState.putString("saved_state",stateToSave);
        outState.putString("saved_stated",stateToSaveD);
        outState.putString("saved_statef",stateToSavef);

        //Toast.makeText(getApplicationContext(),"valeur de search: "+stateToSave,Toast.LENGTH_LONG).show();

        // Toast.makeText(getApplicationContext(),"enregistrement de letat"+stateToSave,Toast.LENGTH_LONG).show();
    }





    public void LoadFichierPreciss (int numdepart,int numero, int numfin){
        String titre_article=null;
        String resultat = null;
        String artNumero = "arti" + String.valueOf(numero);
        cn = getResources().getIdentifier(artNumero, "raw", c.getPackageName());
        if(cn>0){
            inputStream = getResources().openRawResource(cn);
            try {
                if (inputStream != null) {
                    reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
                    String str;
                    StringBuilder buf = new StringBuilder();
                    str=reader.readLine();
                    //titre_article = str;
                    titre_arti.setText(str);
                    numeros.setText(String.valueOf(numero));
                    numDepart.setText(String.valueOf(numdepart));
                    numFin.setText(String.valueOf(numfin));
                    // titre_titre
                    str=reader.readLine();
                    // titre_chapitre
                    str=reader.readLine();
                    //titre_section
                    str=reader.readLine();
                    while ((str=reader.readLine())!=null) {
                        buf.append(str + "\r\n");
                    }
                    resultat = buf.toString();
                  /*  layoutdetail.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.FILL_PARENT, RecyclerView.LayoutParams.FILL_PARENT));
                    layoutdetail.setOrientation(LinearLayout.VERTICAL);

                    //layoutdetail.setAnimation();
                    TranslateAnimation  moveLefttoRight = new TranslateAnimation(0, 200, 0, 0);
                    moveLefttoRight.setDuration(1000);
                    moveLefttoRight.setFillAfter(true);
                    text_arti.startAnimation(moveLefttoRight);*/
                    text_arti.setText(resultat);
                }
                reader.close();
                inputStream.close();
            }catch (Exception e){

                Toast.makeText(c,"Désoler! ce cantique n'existe pas encore",Toast.LENGTH_LONG).show();
            }
        }

    }


}

