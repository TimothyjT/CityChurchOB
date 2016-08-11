package com.tubbs.citychurchob;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Tubbster on 8/12/15.
 */
public class ParseInitialization extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "qbIfzMgf0z6mDilJib9nIAWqnS5mU8ET2Byuc3gA", "4X2CUIroVEIaCPJbMxGPJirg5Bz3XbgC2Wcfg1oy");
    }
}
