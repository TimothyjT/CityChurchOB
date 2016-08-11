package com.tubbs.citychurchob.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.activities.EventsInfoActivity;
import com.tubbs.citychurchob.models.EventObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.ArrayList;

/**
 * Created by Tubbster on 7/29/15.
 */
public class EventRecyclerListAdapter extends RecyclerView.Adapter<EventRecyclerListAdapter.DataObjectHolder> {

    public static final String TAG = "EventListAdapter";

    public ArrayList<EventObjectModel> mDataset;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView mEventTitle;
        TextView mEventDate;
        TextView mEventDesc;
        View mView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mEventTitle = (TextView) itemView.findViewById(R.id.event_title_text);
            mEventDate = (TextView) itemView.findViewById(R.id.event_date);
            mEventDesc = (TextView)itemView.findViewById(R.id.event_desc_text);
            mView = itemView;


        }

    }

    public EventRecyclerListAdapter(ArrayList<EventObjectModel> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_card_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.mEventTitle.setText(mDataset.get(position).getmEventTitle());
        holder.mEventDate.setText(mDataset.get(position).getmEventDate());
        holder.mEventDesc.setText(mDataset.get(position).getmEventDesc());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.mView.getContext(),EventsInfoActivity.class);
                Bundle args = new Bundle();
                Log.i(TAG,"MData..." + mDataset);
                args.putParcelableArrayList(Keys.PARSE_EVENTS_MODELS_KEY,mDataset);
                i.putExtra(Keys.INTENT_EVENTS_MODELS_KEY, args);
                i.putExtra(Keys.POSTION_KEY,position);
                holder.mView.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
