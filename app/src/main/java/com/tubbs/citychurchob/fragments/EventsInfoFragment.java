package com.tubbs.citychurchob.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.models.EventObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.ArrayList;


public class EventsInfoFragment extends Fragment {

    public static final String TAG =  "EventsInfoFragment";

    private ImageView mEventInfoImage;
    private TextView mEventInfoTitle;
    private TextView mEventInfoDesc;


    private ArrayList<EventObjectModel> mEventModels;
    private EventObjectModel mModel;
    private int mPosition;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_events_info, container, false);
        mEventInfoImage = (ImageView)v.findViewById(R.id.event_info_image);
        mEventInfoTitle = (TextView)v.findViewById(R.id.event_info_title);
        mEventInfoDesc = (TextView)v.findViewById(R.id.event_info_desc);

        mEventModels = getArguments().getParcelableArrayList(Keys.PARSE_EVENTS_MODELS_KEY);
        Log.i(TAG,"model list..." + mEventModels);
        mPosition = getArguments().getInt(Keys.POSTION_KEY);

        mModel = mEventModels.get(mPosition);


        mEventInfoTitle.setText(mModel.getmEventTitle());
        mEventInfoDesc.setText(mModel.getmEventDesc());

        return v;
    }




    public static EventsInfoFragment newInstance(ArrayList<EventObjectModel> models,int position){
        EventsInfoFragment fragment = new EventsInfoFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Keys.PARSE_EVENTS_MODELS_KEY, models);
        args.putInt(Keys.POSTION_KEY, position);
        fragment.setArguments(args);
        return fragment;

    }







}
