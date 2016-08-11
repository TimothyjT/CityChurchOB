package com.tubbs.citychurchob.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.adapters.EventRecyclerListAdapter;
import com.tubbs.citychurchob.models.EventObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mNoEventsText;


    private static final String TAG = "EventsFragment";
    
    ArrayList<EventObjectModel> mEventsObjectList;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.events_card_list, container, false);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        mNoEventsText = (TextView)v.findViewById(R.id.no_events_text);
        Log.i(TAG,"textview..." + mNoEventsText);
        mNoEventsText.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        generateEventObjects();
        return v;
    }



    public void generateEventObjects(){
        mEventsObjectList = new ArrayList<>();

        mAdapter = new EventRecyclerListAdapter(mEventsObjectList);
        mRecyclerView.setAdapter(mAdapter);

        ParseQuery<ParseObject> query = new ParseQuery<>(Keys.PARSE_EVENTS_KEY);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size() == 0 || list == null){
                    mNoEventsText.setVisibility(View.VISIBLE);
                }else {
                    int i;
                    for (i = 0; i < list.size(); i++) {
                        EventObjectModel model = new EventObjectModel();
                        model.setmEventTitle(list.get(i).getString(Keys.PARSE_EVENTS_TITLE_KEY));
                        model.setmEventDate(list.get(i).getString(Keys.PARSE_EVENTS_DATE_KEY));
                        model.setmEventDesc(list.get(i).getString(Keys.PARSE_EVENTS_DESC_KEY));
                        model.setmPosition(i);
                        mEventsObjectList.add(model);
                        mAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }


}
