package com.jacmobile.magicprices.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jacmobile.magicprices.R;
import com.jacmobile.magicprices.network.DeckBrewService.MTGSet;

import java.util.List;

public class SetAdapter extends BaseAdapter
{
    private List<MTGSet> setList;

    public SetAdapter(List<MTGSet> setList)
    {
        this.setList = setList;
    }

    @Override public int getCount()
    {
        return setList.size();
    }

    @Override public MTGSet getItem(int position)
    {
        return setList.get(position);
    }

    @Override public long getItemId(int position)
    {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_list_item, parent, false);
            holder.titleSet = (TextView) convertView.findViewById(R.id.title_list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleSet.setText(getItem(position).name);

        return convertView;
    }

    static class ViewHolder
    {
        TextView titleSet;
    }
}
