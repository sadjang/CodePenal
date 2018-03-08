package com.example.sadjang.codepenal.ReCycler;


import android.widget.Filter;

import com.example.sadjang.codepenal.Objets.Article;


import java.util.ArrayList;

/**
 * Created by sadjang on 30/09/2016.
 */
public class CustumFilter extends Filter {

    ArticleAdapter adapter;
    ArrayList<Article> filterList;


    public CustumFilter(ArrayList<Article> filterList, ArticleAdapter adapter ) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if(constraint!=null&& constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<Article> filterdObjet=new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                // choix de la selection
                if((filterList.get(i).getNom().toUpperCase().contains(constraint))
                   ||(filterList.get(i).getChapitre().toUpperCase().contains(constraint))
                   ||(filterList.get(i).getSection().toUpperCase().contains(constraint))
                   ||(filterList.get(i).getTitre().toUpperCase().contains(constraint))){
                   // add Objet to filterObjet
                    filterdObjet.add(filterList.get(i));
                }

            }
            results.count=filterdObjet.size();
            results.values=filterdObjet;
        }else{
            results.count=filterList.size();
            results.values=filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
         adapter.liste_Article= (ArrayList<Article>) results.values;
         // refrech adapter
        adapter.notifyDataSetChanged();

    }


}
