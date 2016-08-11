package com.tubbs.citychurchob.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.activities.MessagesActivity;
import com.tubbs.citychurchob.adapters.SeriesListAdapter;
import com.tubbs.citychurchob.models.SeriesObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tubbster on 5/25/15.
 */
public class SeriesFragment extends Fragment {


    private ListView seriesNameView;
    private SeriesListAdapter adapter;

    private ArrayList<SeriesObjectModel> mSeriesModels;



    public Bitmap mBitmap;


    private static final String TAG = "SeriesFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_series, container, false);

        //Get a ListView from main view
        seriesNameView = (ListView) v.findViewById(R.id.series_title_list);
        mSeriesModels = getArguments().getParcelableArrayList(Keys.PARSE_SERIES_MODELS_KEY);
        for(int i = 0; i < mSeriesModels.size(); i++){
            setSeriesImageThumbnail(mSeriesModels.get(i).getmPosition());
        }
        adapter = new SeriesListAdapter(getActivity(),mSeriesModels);
        seriesNameView.setAdapter(adapter);
//        generateSeriesObjects();


        seriesNameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"mSeriesModel...." + mSeriesModels.get(position));
                Intent i = new Intent(getActivity(), MessagesActivity.class);
                i.putExtra(Keys.INTENT_SERIES_MODEL_OBJECT_KEY,mSeriesModels.get(position));
                i.putExtra(Keys.POSTION_KEY, mSeriesModels.get(position).getmPosition());
                startActivity(i);
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    //    public void generateSeriesObjects() {
//        mPodcastSeriesModels = new ArrayList<>();
//
//        //Create a List Adapter
//        adapter = new SeriesListAdapter(getActivity(), mSeriesModels);
//
//        //Attach Adapter to List
//        seriesNameView.setAdapter(adapter);
//
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.SERIES_KEY);
//        query.orderByDescending("createdAt");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                for (int i = 0; i < list.size(); i++) {
//                    model = new PodcastSeriesModel();
//                    model.setSeriesTitle(list.get(i).getString(Keys.SERIES_NAME_KEY));
//                    setSeriesImageThumbnail(i);
//                    mPodcastSeriesModels.add(model);
//                }
//            }
//        });
//
//
//    }


    public void setSeriesImageThumbnail(final int position) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.SERIES_KEY);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    ParseFile file = list.get(position).getParseFile(Keys.SERIES_IMAGE_KEY);
                    if (file != null) {
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                if (e == null) {
                                    mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    mSeriesModels.get(position).setmSeriesImage(mBitmap);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });
    }




    public static SeriesFragment newInstance(ArrayList<SeriesObjectModel> models) {
        SeriesFragment fragment = new SeriesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Keys.PARSE_SERIES_MODELS_KEY, models);
        fragment.setArguments(args);
        return fragment;
    }


}
