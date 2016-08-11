package com.tubbs.citychurchob.mediaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.activities.MessagesActivity;
import com.tubbs.citychurchob.models.SeriesObjectModel;
import com.tubbs.citychurchob.util.Keys;


public class MediaPlayerActivity extends ActionBarActivity  {

    private static final String TAG = "MediaPlayerActivity";
    private Toolbar mToolBar;
    private int position;

    private SeriesObjectModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);


        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragM = getSupportFragmentManager();
        FragmentTransaction trans = fragM.beginTransaction();

        MediaPlayerFragment fragment = newInstance();
        trans.add(R.id.fragment_container, fragment);
        trans.commit();



    }

    public MediaPlayerFragment newInstance() {
        if (getIntent().getExtras() != null) {
            String audio_url = getIntent().getExtras().getString(Keys.AUDIO_URL_KEY);
            position = getIntent().getExtras().getInt(Keys.POSTION_KEY);
            model = getIntent().getExtras().getParcelable(Keys.INTENT_SERIES_MODEL_OBJECT_KEY);
            return MediaPlayerFragment.newInstance(audio_url, position, model);
        } else {
            return new MediaPlayerFragment();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent parentActivtity = new Intent(this, MessagesActivity.class);
                parentActivtity.putExtra(Keys.INTENT_SERIES_MODEL_OBJECT_KEY,model);
                parentActivtity.putExtra(Keys.POSTION_KEY,position);
                startActivity(parentActivtity);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
