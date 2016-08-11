package com.tubbs.citychurchob.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.fragments.EventsInfoFragment;
import com.tubbs.citychurchob.models.EventObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.ArrayList;

public class EventsInfoActivity extends ActionBarActivity {

    private int mPostion;
    private ArrayList<EventObjectModel> mEventModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R .layout.activity_events);

        getExtras();

        FragmentManager fragM = getSupportFragmentManager();
        FragmentTransaction trans = fragM.beginTransaction();

        EventsInfoFragment fragment = EventsInfoFragment.newInstance(mEventModels, mPostion);
        trans.add(R.id.events_fragment_container, fragment);
        trans.commit();

    }


    public void getExtras(){
        mPostion = getIntent().getExtras().getInt(Keys.POSTION_KEY);
        Bundle bundle = getIntent().getExtras().getBundle(Keys.INTENT_EVENTS_MODELS_KEY);
        mEventModels = bundle.getParcelableArrayList(Keys.PARSE_EVENTS_MODELS_KEY);
    }

}
