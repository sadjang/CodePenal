package com.example.sadjang.codepenal.ReCycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sadjang.codepenal.R;

/**
 * Created by sadjang on 01/11/2016.
 */
public class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView text_num,text_titre ;
    ImageView img_spinner;
    ItemeClickListener itemeClickListener;
    public ArticleHolder(View itemView) {
        super(itemView);
        text_num=(TextView) itemView.findViewById(R.id.text_num);
        text_titre=(TextView)itemView.findViewById(R.id.text_titre);
        img_spinner=(ImageView)itemView.findViewById(R.id.img_spinner);
        itemView.setOnClickListener(this);
    }
    public void setItemeClickListener(ItemeClickListener ic){
        this.itemeClickListener=ic;
    }

    @Override
    public void onClick(View v) {
        this.itemeClickListener.onItemClick(v,getLayoutPosition());
    }

}
