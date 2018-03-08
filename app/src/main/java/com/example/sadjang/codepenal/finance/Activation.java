package com.example.sadjang.codepenal.finance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sadjang.codepenal.AGUEILL;
import com.example.sadjang.codepenal.R;
import com.example.sadjang.codepenal.bdcode.DatabaseHelper;
import com.example.sadjang.codepenal.cryptage.CryptageFile;
import com.example.sadjang.codepenal.globalVars;
import com.kosalgeek.genasync12.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tantine1 on 10/22/2016.
 */
public class Activation extends AppCompatActivity {

    EditText editCode ;
    Button btnValider;
    String serveur_addresse ;
    String codeCrypter;
    String appName;
    String[] get_params;
    ProgressDialog  pd;
    Context context;
    // String status = null;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fin_activation);
        context = this;
        appName = this.getString(R.string.app_name);
        editCode = (EditText)findViewById(R.id.editCode);
        btnValider  = (Button)findViewById(R.id.btnActiver);

        requestQueue = Volley.newRequestQueue(context);
        Bundle extras = getIntent().getExtras();
        get_params = extras.getStringArray("params_activation");

        try {
            WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            TelephonyManager teleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            ServerAdresse serverAdresse = new ServerAdresse();




            CryptageFile cryptage = new CryptageFile(context,get_params[1].length(),context.getString(R.string.app_name));

            codeCrypter = cryptage.initCryptage(get_params[0],get_params[1]);


            //adresse_mac="add";
            // Android_Id = "1234";
            //cryptage = new CryptageFile(context,get_params[1].length(),context.getString(R.string.app_name));
            //  codeCrypter = cryptage.initCryptage(adresse_mac,Android_Id);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Activer votre WIFI", Toast.LENGTH_LONG).show();
        }





        if (get_params[5]!=null)
            serveur_addresse = get_params[5];


        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (codeCrypter.equals(editCode.getText().toString().toUpperCase().trim())){
                    Toast.makeText(context,"OK",Toast.LENGTH_LONG).show();
                    // requette d'envoi
                   /* BackGroundSender backGroundSender = new BackGroundSender();
                    backGroundSender.execute(serveur_addresse,get_params[0]);*/
                    //  fin requette d'envoi a la bd

                    /**
                     * Activation du code
                     */

                    Activation_code(serveur_addresse);
                }else{
                    Toast.makeText(context,"Désoler code incorrect !"+codeCrypter,Toast.LENGTH_LONG).show();
                }

                /**
                 * Background task qui se connect a la base de donnée et envoi les infos vers la BD
                 * et ensuite passe a la fenetre suivante
                 */

            }
        });



    }


    /**
     * Activation envoi / Recupération des parametres
     */

    public void Activation_code(final String serveur_addresse){
     /*  pd= new ProgressDialog(context);
        pd.setTitle("Ajout");
        pd.setMessage("Chargement ... ");
        pd.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST,serveur_addresse, new Response.Listener<String>(

        ) {
            @Override
            public void onResponse(String response) {
                String status = null;
                String reponseServeur = null;
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("server_response");

                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        status = jsonObject.getString("status");
                        reponseServeur = jsonObject.getString("valider");
                        Log.i("status1",status);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }


                if (status.equals("1")) {
                    // pd.dismiss();
                    Toast.makeText(getApplicationContext(), "writer fichier"+codeCrypter, Toast.LENGTH_LONG).show();

                    DatabaseHelper myDB = new DatabaseHelper(getApplicationContext());

                    myDB.insertData(codeCrypter);



                    //on lance l'activité qui permet de saisir le code d'activation
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else if (status.equals("0")) {
                    Toast.makeText(getApplicationContext(), reponseServeur, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Le Serveur ne répond pas: erreur = "+error.toString(),Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> parameters = new HashMap<>();
                parameters.put("OK","1");
                parameters.put("ADRESSEMAC",get_params[0]);
                parameters.put("ANDROI_ID",get_params[1]);
                parameters.put("NOM_CLIEN",get_params[2]);
                parameters.put("IDVENDS",get_params[3]);
                parameters.put("APP_NAME",get_params[4]);


                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }




}
