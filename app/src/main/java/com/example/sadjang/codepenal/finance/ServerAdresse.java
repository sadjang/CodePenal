package com.example.sadjang.codepenal.finance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sadjang.codepenal.R;
import com.example.sadjang.codepenal.globalVars;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Tantine1 on 10/21/2016.
 */
public class ServerAdresse extends Activity {


    ImageButton val_server_add;
    EditText edit_num_vend;
    String Mac_adresse, Android_Id, Nom_client;
    String numberoVend;
    ProgressDialog pd;

    String reponseServeur, status;
    String appName;
    public Context context = this;


    @Override
    protected void onResume() {
        super.onResume();
//        val_server_add.setImageResource(R.mipmap.btn_pressed);
        // val_server_add.setImageResource(R.mipmap.val_server);
        //       val_server_add.setEnabled(true);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fin_server_adresse);

        //attribution des id
        val_server_add = (ImageButton) findViewById(R.id.btn_serverAdresse);
        edit_num_vend = (EditText) findViewById(R.id.editNumbVend);

        /**
         * initialisation de la mac et android id
         */


        getInformationClient();


        //* Initialisation du cryptage

        appName = this.getString(R.string.app_name);



        /**
         *  Listener sur le bouton valider qui permet de récuperer
         *   l'adresse IP du serveur et initialise la variable qui va stocké
         *   cette adresse dans la class globalVars.
         *
         */
        val_server_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                val_server_add.setEnabled(false);
                numberoVend = edit_num_vend.getText().toString().trim();

                Log.i("numero",numberoVend);
                /**
                 * on fabrique l'objet qui va permettre de recuperer l'adresse saisir et initialiser
                 * la variable de l'adresse du serveur qui va permettre de se connecter a notre base
                 * pour envoyer notre id , IMEI et autres avant de passer a la prochaine fenetre
                 */
                /**  global = new globalVars();
                 global.setAdresseServeur(edit_adresse_serveur.getText().toString().trim());
                 **/

                /**
                 * Background task qui se connect a la base de donnée et envoi les infos vers la BD
                 * et ensuite passe a la fenetre suivante
                 */
                if(numberoVend.length()!=0) {
                    BackGroundSender backGroundSender = new BackGroundSender();
                    Log.i("tintin",globalVars.adresseServeur);
                    backGroundSender.execute(globalVars.adresseServeur);
                }else {
                    Toast.makeText(context,"Veuillez entré le numero du vendeur",Toast.LENGTH_LONG).show();
                }
                //  Intent intent = new Intent(context,Activation.class);
                //  context.startActivity(intent);
            }
        });


    }


    /**
     * Méthode qui permet d'avoir les information de client
     * Mac_adresse, Android_Id, et nom
     */
    public void getInformationClient() {

        TelephonyManager teleManager;
        WifiManager wifiManager;
        try {
            //recupération de l'addresse mac
            wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            Mac_adresse = info.getMacAddress();

            Log.i("String",Mac_adresse);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } finally {

            //Ici on recuper l'id du telephone
            try {

                teleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                //Récupération de l'id de l'android
                Android_Id = teleManager.getDeviceId();
                //Récupération du nom du telephone du client a chercher sur le net
                Nom_client = getDeviceName();


                /**
                 * lister tous les infos regarder comment faire sur le net
                 *
                 * List<CellInfo> t = null;
                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                 t = teleManager.getAllCellInfo();
                 }
                 t.get(1);
                 for (int i=0 ; i<t.size();i++){

                 }**/
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }


    class BackGroundSender extends AsyncTask<String, Void, Integer> {

        //pour vérifier si la connection est passé(1) ou non (0)
        public Integer result;

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            //   getInformationClient();
            pd = new ProgressDialog(context);
            pd.setTitle("Ajout");
            pd.setMessage("Chargement ... ");
            pd.show();

        }


        @Override
        protected Integer doInBackground(String... params) {

            //initialisation de result
            result = 0;
            HttpURLConnection urlConnection;

            /**
             * on appelle la méthode qui nous permet de recupérer
             * Mac_adresse, android_id, nom
             */

            try {
                //recupération de l'url de la bd envoyer par l'objet backGroundSender
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();


                //Données a envoyer dans la bd avec leur clés
                String data = URLEncoder.encode("ADRESSEMAC", "UTF-8") + "=" + URLEncoder.encode(Mac_adresse, "UTF-8");
                data += "&" + URLEncoder.encode("ANDROI_ID", "UTF-8") + "=" + URLEncoder.encode(Android_Id, "UTF-8");
                data += "&" + URLEncoder.encode("NOM_CLIEN", "UTF-8") + "=" + URLEncoder.encode(Nom_client, "UTF-8");
                data += "&" + URLEncoder.encode("IDVENDS", "UTF-8") + "=" + URLEncoder.encode(numberoVend, "UTF-8");
                data += "&" + URLEncoder.encode("APP_NAME", "UTF-8") + "=" + URLEncoder.encode(appName, "UTF-8");


                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                OutputStreamWriter oSW = new OutputStreamWriter(urlConnection.getOutputStream());
                oSW.write(data);

                oSW.flush();



                //Récupération du code de reponse du serveur si c oki 200
                int statusCode = urlConnection.getResponseCode();

                Log.v("statusCode",String.valueOf(statusCode));

                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);

                    }

                    parseResult(String.valueOf(response));
                    result = 1;
                } else {

                    result = 0;
                }


            } catch (Exception e) {

            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {


            if (result == 1) {

                if (status.equals("1")) {
                    //Toast.makeText(getApplicationContext(), reponseServeur, Toast.LENGTH_LONG).show();
                    /**
                     * tableau de valeur a envoyer a
                     */


                    String array[] = {Mac_adresse,Android_Id,Nom_client,numberoVend,appName,globalVars.adresseServeur};
                    //on lance l'activité qui permet de saisir le code d'activation
                    Intent intent = new Intent(getApplicationContext(), Activation.class);
                    intent.putExtra("params_activation",array);
                    startActivity(intent);
                } else if (status.equals("0")) {
                    Toast.makeText(getApplicationContext(), reponseServeur, Toast.LENGTH_LONG).show();
                }
            } else if (result == 0)
                Toast.makeText(getApplicationContext(), "Erreur de connection, verifiez l'adresse du serveur", Toast.LENGTH_LONG).show();


            val_server_add.setEnabled(true);
            pd.dismiss();
        }

        private void parseResult(String resultat) {


            try {

                JSONObject response = new JSONObject(resultat);
                JSONArray jsonArray = response.getJSONArray("server_response");
                int count = 0;


                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    count++;


                    status = jo.optString("status");
                    if (status.equals("0"))
                        reponseServeur = jo.optString("echec");

                    else
                        reponseServeur = jo.optString("valider");
                }


            } catch (Exception e) {

            }
        }
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}