package com.tubbs.citychurchob.mediaplayer;

//Utils class:
//Provides convenience methods for interacting with the service & silently handling exceptions

import android.os.RemoteException;
import android.util.Log;

public class PlaybackUtils {

    private final static String TAG = "PlaybackUtils";

    public static void openFile(String path) {
        if (ServiceUtils.sService == null) {
            return;
        }
        try {
            ServiceUtils.sService.openFile(path);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void play() {
        if (ServiceUtils.sService == null) {
            return;
        }
        try {
            ServiceUtils.sService.play();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void pause() {
        if (ServiceUtils.sService == null) {
            Log.i(TAG,"sService inside pause : " + ServiceUtils.sService);
            return;
        }
        try {
            ServiceUtils.sService.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (ServiceUtils.sService == null) {
            return;
        }
        try {
            ServiceUtils.sService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static long getDuration() {

        if (ServiceUtils.sService != null) {
            try {
                return ServiceUtils.sService.getDuration();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static long getPosition() {

        if (ServiceUtils.sService != null) {
            try {
                return ServiceUtils.sService.getPosition();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static boolean isPlaying() {
        if (ServiceUtils.sService != null) {
            try {
                return ServiceUtils.sService.isPlaying();
            } catch (final RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void seek(long position) {
        if (ServiceUtils.sService != null) {
            try {
                ServiceUtils.sService.seek(position);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


}