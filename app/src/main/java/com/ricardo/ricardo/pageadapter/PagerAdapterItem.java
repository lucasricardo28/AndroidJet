package com.ricardo.ricardo.pageadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ricardo.ricardo.fragment.FragmentItem;
import com.ricardo.ricardo.models.ItemsCategory;

import java.util.List;

public class PagerAdapterItem extends FragmentStatePagerAdapter {
    private Integer qtdTabs;
    private List<ItemsCategory> items;
    private String currentTitle;

    public PagerAdapterItem(FragmentManager fm, Integer qtdTabs, List<ItemsCategory> items, String currentTitle) {
        super(fm);
        this.qtdTabs = qtdTabs;
        this.items = items;
        this.currentTitle = currentTitle;
    }

    @Override
    public Fragment getItem(int position) {

        FragmentItem frag = new FragmentItem();
        frag.setItemsCategories(items);
        frag.setPosition(position);
        frag.setTitle(currentTitle);
        return frag;
    }

    @Override
    public int getCount() {
        return qtdTabs;
    }
}
