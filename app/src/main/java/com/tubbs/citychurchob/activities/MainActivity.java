package com.tubbs.citychurchob.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.adapters.TabsFragmentPagerAdapter;
import com.tubbs.citychurchob.models.SeriesObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.ArrayList;
import java.util.List;

import view.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {
    final static String TAG = "MainActivity";

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private TabsFragmentPagerAdapter mTabsAdapter;
    private ArrayList<SeriesObjectModel> mSeriesModels;
    private Toolbar mToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateSeriesObjects();

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolBar);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_media_player:

                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Sends a query to Parse.com to retrieve the objects under the "Series" class.
    //It then creates a new SeriesObjectModel and sets each value accordingly and
    //then adds the model object to a list of SeriesObjectModels. These Models are then used throughout
    //the app for their data they contain.
    public void generateSeriesObjects() {
        mSeriesModels = new ArrayList<>();

        ParseQuery<ParseObject> query = new ParseQuery<>(Keys.SERIES_KEY);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                int i;
                Log.i(TAG, "List size..." + list);
                for (i = 0; i < list.size(); i++) {
                    SeriesObjectModel model = new SeriesObjectModel();
                    model.setmSeriesName(list.get(i).getString(Keys.SERIES_NAME_KEY));
                    model.setmPosition(i);
                    model.setmMessageTitle((ArrayList) list.get(i).getList(Keys.MESSAGE_TITLE_KEY));
                    model.setmAudioUrl((ArrayList) list.get(i).getList(Keys.PARSE_URL_KEY));
                    model.setmPublishDateMonth((ArrayList) list.get(i).getList(Keys.DATE_MONTH_KEY));
                    model.setmPublishDateDay((ArrayList) list.get(i).getList(Keys.DATE_DAY_KEY));
                    model.setmMessageLength((ArrayList) list.get(i).getList(Keys.MESSAGE_LENGTH_KEY));
                    mSeriesModels.add(model);

                }
                mTabsAdapter = new TabsFragmentPagerAdapter(getSupportFragmentManager(), mSeriesModels);
                mViewPager.setAdapter(mTabsAdapter);
                mSlidingTabLayout.setDistributeEvenly(true);
                mSlidingTabLayout.setViewPager(mViewPager);


            }
        });
    }

}
