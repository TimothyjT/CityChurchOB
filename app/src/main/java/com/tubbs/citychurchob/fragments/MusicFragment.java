package com.tubbs.citychurchob.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tubbs.citychurchob.R;

/**
 * Created by Tubbster on 5/25/15.
 */
public class MusicFragment extends Fragment {
    public TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.music_fragment,container,false);
        return v;
    }


}
