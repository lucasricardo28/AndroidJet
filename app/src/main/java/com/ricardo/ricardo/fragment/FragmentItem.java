package com.ricardo.ricardo.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ricardo.ricardo.R;
import com.ricardo.ricardo.models.ItemsCategory;

import java.util.List;

public class FragmentItem extends Fragment {
    private List<ItemsCategory> itemsCategories;
    private Integer position;
    private String title;

    public List<ItemsCategory> getItemsCategories() {
        return itemsCategories;
    }

    public void setItemsCategories(List<ItemsCategory> itemsCategories) {
        this.itemsCategories = itemsCategories;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_item, container, false);

        ItemsCategory itemsCategory = itemsCategories.get(getPosition());

        TextView textViewTitle = view.findViewById(R.id.tvFragmentItemTitle);
        TextView textViewSub = view.findViewById(R.id.tvFragmentItemSubTItle);
        TextView textViewText = view.findViewById(R.id.tvFragmentItemText);

        textViewTitle.setText(getTitle());
        textViewSub.setText(itemsCategory.getTitle());
        textViewText.setText(itemsCategory.getDescription());
        return view;
    }
}
