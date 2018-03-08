package com.example.sadjang.codepenal.ReCycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.sadjang.codepenal.Detail_Article;
import com.example.sadjang.codepenal.Objets.Article;
import com.example.sadjang.codepenal.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by sadjang on 01/11/2016.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleHolder> implements Filterable {
    Context c;
    ArrayList<Article> liste_Article,filterList;
    CustumFilter filter=null;
    public ArticleAdapter(Context c, ArrayList<Article> liste_Article) {
        this.c = c;
        this.liste_Article= liste_Article;
        this.filterList=liste_Article;
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ArticleHolder holder = new ArticleHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        holder.text_num.setText(String.valueOf(liste_Article.get(position).getNumero()));
        holder.text_titre.setText(liste_Article.get(position).getNom());
        //holder.CORRIGER.setImageResource(listeCode_Telephone.get(position).getVALIDITE());
        holder.setItemeClickListener(new ItemeClickListener() {
            @Override
            public void onItemClick(View v,int position) {

                //Snackbar.make(v,String.valueOf(liste_Article.get(position).getNom()),Snackbar.LENGTH_SHORT).show();
                Intent i=new Intent(c, Detail_Article.class);
                // Picasso.with(c).load(listObjets.get(position).getImage_id()).into(imagev);
                Gson gson = new Gson();
                String detailObjet = gson.toJson(liste_Article.get(position));
                i.putExtra("ArticleDetail", detailObjet);
               // effectuer un finish dans le MainActivity
                c.startActivity(i);




            }
        });


    }




    @Override
    public int getItemCount() {
        return liste_Article.size();
    }
    // Return filter Objet

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new CustumFilter(filterList,this);
        }

        return filter;
    }
}
