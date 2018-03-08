package com.example.sadjang.codepenal.ReCycler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadjang.codepenal.Objets.Article;
import com.example.sadjang.codepenal.R;

import java.util.List;
import java.util.Map;

/**
 * Created by sadjang on 22/11/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, List<Article>> chaptire;
    private List<String> listarticle;

    public ExpandableListAdapter(Activity context, List<String> listarticle, Map<String, List<Article>> chaptire) {
        this.context = context;
        this.listarticle = listarticle;
        this.chaptire = chaptire;
    }

    @Override
    public int getGroupCount() {
        return listarticle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return chaptire.get(listarticle.get(groupPosition)).size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return listarticle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return chaptire.get(listarticle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String articleName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.chapitre,
                    null);
        }


        TextView item = (TextView) convertView.findViewById(R.id.chapitre);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(articleName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Article listarticle = (Article) getChild(groupPosition, childPosition);
            LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }

        LinearLayout  idarticle = (LinearLayout) convertView.findViewById(R.id.idarticle);
        TextView  text_num = (TextView) convertView.findViewById(R.id.text_num);
        TextView  text_titre = (TextView) convertView.findViewById(R.id.text_titre);
        /*idarticle.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(context,"je pass",Toast.LENGTH_LONG).show();

            }
        });*/
        text_num.setText(String.valueOf(listarticle.getNumero()));
        text_titre.setText(listarticle.getNom());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
