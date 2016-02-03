package com.jacmobile.magicprices.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jacmobile.magicprices.R;
import com.jacmobile.magicprices.network.DeckBrewService;

import java.util.List;

public class MTGCardAdapter extends BaseAdapter
{
    private final List<DeckBrewService.MTGCard> cardList;

    public MTGCardAdapter(List<DeckBrewService.MTGCard> cardList)
    {
        this.cardList = cardList;
    }

    @Override public int getCount()
    {
        return cardList.size();
    }

    @Override public DeckBrewService.MTGCard getItem(int position)
    {
        return cardList.get(position);
    }

    @Override public long getItemId(int position)
    {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        final DeckBrewService.MTGCard currentItem = getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search_result, parent, false);
            holder.cardName = (TextView) convertView.findViewById(R.id.txt_card_name);
            holder.cardTypes = (TextView) convertView.findViewById(R.id.txt_card_types);
            holder.cardImage = (ImageView) convertView.findViewById(R.id.img_card_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (currentItem != null) {
            String types = "";

            if (currentItem.types.length > 1) {
                for (int i = 0; i < currentItem.types.length; i++) {
                    String s = currentItem.types[i];
                    s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
                    if (i < (currentItem.types.length-1)) {
                        types += s + ", ";
                    } else {
                        types += s;
                    }
                }
            } else {
                String s = currentItem.types[0];
                s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
                types += s;
            }
            types += "\n\n"+currentItem.text;

            holder.cardName.setText(getItem(position).name);
            holder.cardTypes.setText(types);

            if (currentItem.editions != null) {
                Glide.with(parent.getContext())
                        .load(currentItem.editions[0].image_url)
                        .crossFade()
                        .into(holder.cardImage);
            }
        }

        return convertView;
    }

    static class ViewHolder
    {
        TextView cardName;
        TextView cardTypes;
        ImageView cardImage;
    }
}
