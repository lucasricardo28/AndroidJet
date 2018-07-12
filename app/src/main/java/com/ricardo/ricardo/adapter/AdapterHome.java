package com.ricardo.ricardo.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ricardo.ricardo.activity.ItemsActivity;
import com.ricardo.ricardo.R;
import com.ricardo.ricardo.models.ResponseList;

import java.util.List;

public class AdapterHome extends BaseAdapter {

    private List<ResponseList> responseLists;
    private LayoutInflater ctx;

    public AdapterHome(List<ResponseList> responseLists, LayoutInflater ctx) {
        this.responseLists = responseLists;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return responseLists.size();
    }

    @Override
    public Object getItem(int i) {
        return responseLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View item = ctx.inflate(R.layout.item_list_home, viewGroup, false);

        final ResponseList newItem = responseLists.get(position);

        TextView title = item.findViewById(R.id.tvItemHomeTitle);
        TextView bodyTitle = item.findViewById(R.id.tvItemHomeBodyTitle);
        TextView bodySubtitle = item.findViewById(R.id.tvItemHomeBodySubtitle);

        title.setText(newItem.getCategory());
        Integer countGallery = position+1;
        bodyTitle.setText("Galeria "+String.valueOf(countGallery));
        bodySubtitle.setText("Click para ver os itens da Galeria "+String.valueOf(countGallery));

        LinearLayout linearClick = item.findViewById(R.id.llItemHome);
        linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent play = new Intent(ctx.getContext(),ItemsActivity.class);
                play.putExtra("position",position);
                play.putExtra("gallery",newItem.getCategory());
                ctx.getContext().startActivity(play);
            }
        });
        return item;
    }
}
