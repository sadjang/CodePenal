package com.example.sadjang.codepenal.cryptage;

import android.content.Context;
import android.util.Log;

/**
 * Created by Tantine1 on 10/22/2016.
 */
public class CryptageFile {

    public int taille;
    Context context;
    String appName;


    public CryptageFile(Context context, int taille, String appName) {
        this.taille = taille;
        this.context = context;
        this.appName =appName;
    }



    // méthode pour lancer le cryptage

    public String initCryptage(String MAC,String ANDROID_ID){
        int nombre=coder( MAC)+additionString(ANDROID_ID);
        return convertirDecHexa( Division(nombre));
    }



    /**
     * méthode qui va recupérer une chaine de caractere de nombre et les additioner et renvoyer un nombre
     *
     * @param number
     */


    public int additionString(String number) {


        int result = 0;

        //ici on addition
        for (int i = 0; i < number.length(); i++) {
            result += Integer.parseInt(String.valueOf(number.charAt(i)));
        }
        return result;
    }


    /**
     * Conversion de l'ANdroid Id en hexadecimal
     */


    public int[] Division(int hexa) {

        int i = 0;
        int[] rest = new int[taille];
        while (hexa != 0) {
            rest[i] = hexa % 16;
            Log.i("reste", String.valueOf(rest));
            hexa /= 16;

            i++;
        }
        return rest;
    }


    public String convertirDecHexa(int[] tab) {
        int len = tab.length;


        String app = appName.substring(2, 4).toUpperCase();

        String result = app;

        Log.i("hexa1", String.valueOf(len));

        for (int i = 0; i < 4; i++) {

            if (tab[i] > 9) {
                switch (tab[i]) {
                    case 10:
                        result += "A";
                    case 11:
                        result += "B";
                    case 12:
                        result += "C";
                    case 13:
                        result += "D";
                    case 14:
                        result += "E";
                    case 15:
                        result += "F";

                }
            } else {
                result += tab[i];
            }

        }

        return result;
    }

    //  semi criptage
    public int coder(String AdrMac) {// tranforme l addresse mac en entier selon le principe
        String[] Tab = AdrMac.split(":");
        int nomb = addition(Tab);
        return nomb;
    }


    public int convertirHexaDec(String c) {// convertir en hexadecimal

        switch (c) {
            case "A":
                return 10;
            case "B":
                return 11;
            case "C":
                return 12;
            case "D":
                return 13;
            case "E":
                return 14;
            case "F":
                return 15;
            case "a":
                return 10;
            case "b":
                return 11;
            case "c":
                return 12;
            case "d":
                return 13;
            case "e":
                return 14;
            case "f":
                return 15;
        }
        return Integer.parseInt(c);
    }


    public int addition(String[] tab) {
        int n = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length(); j++) {
                n += convertirHexaDec(String.valueOf(tab[i].charAt(j)));
            }
        }
        return n;
    }
    //  fin semi criptage


}
