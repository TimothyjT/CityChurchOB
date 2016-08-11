package com.tubbs.citychurchob.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.fragments.MessagesFragment;
import com.tubbs.citychurchob.models.SeriesObjectModel;
import com.tubbs.citychurchob.util.Keys;


public class MessagesActivity extends ActionBarActivity {
    private final static String TAG = "MessagesActivity";

    private int postion;
    private SeriesObjectModel mModel;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_messages);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState != null) {
            postion = savedInstanceState.getInt(Keys.POSTION_KEY);
            mModel = savedInstanceState.getParcelable(Keys.INTENT_SERIES_MODEL_OBJECT_KEY);
            getSupportActionBar().setTitle(mModel.getmSeriesName());
        } else {
            getExtras();
        }


        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (mModel != null) {
            FragmentManager fragM = getSupportFragmentManager();
            FragmentTransaction trans = fragM.beginTransaction();

            MessagesFragment fragment = MessagesFragment.newInstance(mModel, postion);
            trans.add(R.id.fragment_container, fragment);
            trans.commit();
        }

    }

    public void getExtras() {
        if (getIntent().getExtras() != null) {
            postion = getIntent().getExtras().getInt(Keys.POSTION_KEY);
            mModel = getIntent().getExtras().getParcelable(Keys.INTENT_SERIES_MODEL_OBJECT_KEY);
            getSupportActionBar().setTitle(mModel.getmSeriesName());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putParcelable(Keys.INTENT_SERIES_MODEL_OBJECT_KEY, mModel);
        savedInstance.putInt(Keys.POSTION_KEY, postion);


    }



    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                Intent i = new Intent(this, MainActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                NavUtils.getParentActivityIntent(this);
//                NavUtils.navigateUpTo(this, i);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
