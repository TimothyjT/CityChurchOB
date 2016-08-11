package com.tubbs.citychurchob.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tubbs.citychurchob.R;

import java.util.List;

/**
 * Created by Tubbster on 5/25/15.
 */
public class MoreFragment extends Fragment {
    private static final String TAG = "MoreFragment";

    public ImageView imageView;

    private Button mFacebookBtn;
    private Button mTwitterBtn;
    private Button mInstagramBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.more_fragment, container, false);
        mFacebookBtn = (Button)v.findViewById(R.id.facebook_button);
        mTwitterBtn = (Button)v.findViewById(R.id.twitter_button);
        mInstagramBtn = (Button)v.findViewById(R.id.instagram_button);

        mFacebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFacebook();
            }
        });

        mTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTwitter();

            }
        });

        mInstagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInstagram();

            }
        });

        return v;
    }


    public void startFacebook(){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("fb://page/99976576537")));
        } catch (Exception e) {
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/citychurchob")));
        }
    }


    public void startTwitter(){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=citychurchob"));
            startActivity(intent);
        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/#!/citychurchob")));
        }
    }

    public void startInstagram(){
        Uri uri = Uri.parse("http://instagram.com/_u/citychurchob");
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);
        insta.setPackage("com.instagram.android");

        if (isIntentAvailable(getActivity(), insta)){
            startActivity(insta);
        } else{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/citychurchob")));
        }


    }


    private boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
