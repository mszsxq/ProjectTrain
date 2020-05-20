package com.example.carepet.adapter;

import android.widget.ListAdapter;

import com.etsy.android.grid.ExtendableListView;
import com.etsy.android.grid.HeaderViewListAdapter;

import java.util.ArrayList;

public class FootAdapter extends HeaderViewListAdapter {

    public FootAdapter(ArrayList<ExtendableListView.FixedViewInfo> headerViewInfos, ArrayList<ExtendableListView.FixedViewInfo> footerViewInfos, ListAdapter adapter) {
        super(headerViewInfos, footerViewInfos, adapter);
    }
}
