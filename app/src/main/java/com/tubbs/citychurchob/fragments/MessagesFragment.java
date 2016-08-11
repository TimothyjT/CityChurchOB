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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.adapters.TitleListAdapter;
import com.tubbs.citychurchob.mediaplayer.MediaPlayerActivity;
import com.tubbs.citychurchob.models.SeriesObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MessagesFragment extends Fragment {
    private static final String TAG = "MessagesFragment";


    private int mPostion;

    private TitleListAdapter adapter;

    public ListView titlesListView;
    public ProgressBar mProgressBar;

    private SeriesObjectModel mModel;

    private ImageView mSeriesImage;

    public MessagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messages_, container, false);

        mPostion = getArguments().getInt(Keys.POSTION_KEY);


        mModel = getArguments().getParcelable(Keys.INTENT_SERIES_MODEL_OBJECT_KEY);

        titlesListView = (ListView) v.findViewById(R.id.podcast_title_list);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        getImage(mModel.getmPosition());

        adapter = new TitleListAdapter(getActivity(), mModel);
        titlesListView.setAdapter(adapter);

        mSeriesImage = (ImageView) v.findViewById(R.id.series_image);
        titlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startMediaPlayer(position);
            }
        });
        return v;
    }


    public void startMediaPlayer(int position) {
        Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
        intent.putExtra(Keys.INTENT_SERIES_MODEL_OBJECT_KEY, mModel);
        intent.putExtra(Keys.POSTION_KEY, position);
        intent.putExtra(Keys.AUDIO_URL_KEY, mModel.getmAudioUrl().get(position));
        startActivity(intent);
    }


    public void getImage(final int position) {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
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
                                    Bitmap mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    if (mBitmap != null) {
                                        mProgressBar.setVisibility(ProgressBar.GONE);
                                        mSeriesImage.setImageBitmap(mBitmap);
                                    } else {
                                        Log.i(TAG, "Bitmap is null..." + mBitmap);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });

    }


    public static MessagesFragment newInstance(SeriesObjectModel model, int position) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putParcelable(Keys.INTENT_SERIES_MODEL_OBJECT_KEY, model);
        args.putInt(Keys.POSTION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

}
