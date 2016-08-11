package com.tubbs.citychurchob.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.tubbs.citychurchob.IPlaybackService;
import com.tubbs.citychurchob.models.SeriesObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.io.IOException;

public class MediaPlayerService extends Service {
    private final IBinder mNinder = new ServiceStub(this);


    private static final String TAG = "MediaPlayerService";
    //Binder given to MediaPlayerFragment
    //private final IBinder mBinder = new LocalBinder();
    public MediaPlayer mPlayer;
    private Intent mIntent;


    public SeriesObjectModel mModel;
    public String mAudioURL;
    public int mPosition;

    private MediaPlayerPlayback mediaPlayback;

    private AudioManager mAudioManager;


    private static final int FOCUS_CHANGE = 10;
    private static final int FADE_DOWN = 11;
    private static final int FADE_UP = 12;
    private static final int SERVER_DIED = 13;

    private boolean mPausedByTransientLossOfFocus = false;
    private boolean mIsSupposedToBePlaying = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            mIntent = intent;
            mModel = mIntent.getParcelableExtra(Keys.INTENT_SERIES_MODEL_OBJECT_KEY);
            mAudioURL = mIntent.getStringExtra(Keys.AUDIO_URL_KEY);
            mPosition = mIntent.getIntExtra(Keys.POSTION_KEY, 0);
            mediaPlayback = new MediaPlayerPlayback();
            mPlayer = mediaPlayback.prepareMediaPlayer();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mNinder;
    }


    private class MediaPlayerPlayback implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
        MediaPlayer mPlayer = new MediaPlayer();


        private boolean mIsPlayerInitialised = false;


        public MediaPlayer prepareMediaPlayer() {

            if (mAudioURL != null) {
                try {
                    mPlayer.setDataSource(mAudioURL);
                    mPlayer.setOnPreparedListener(this);
                    mPlayer.prepareAsync();
                } catch (IOException e) {

                }
            }
            return mPlayer;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mPlayer = mp;
            mPlayer.start();
            sendUpdateUIBroadcast();
            mIsPlayerInitialised = true;
            mIsSupposedToBePlaying = true;
        }

        public void start() {
            mPlayer.start();
        }

        public void stop() {
            mPlayer.reset();
            mIsSupposedToBePlaying = false;
        }

        /**
         * You CANNOT use this player after calling release
         */
        public void release() {
            stop();
            mPlayer.release();
        }

        public void pause() {
            mPlayer.pause();
        }

        public long getDuration() {
            if (mPlayer != null && mIsPlayerInitialised) {
                return mPlayer.getDuration();
            }
            return -1;
        }

        public long getPosition() {
            if (mPlayer != null && mIsPlayerInitialised) {
                return mPlayer.getCurrentPosition();
            }
            return 0;
        }

        public void seek(long whereTo) {
            mPlayer.seekTo((int) whereTo);
        }


        public void setDataSource(String path) {
            mIsPlayerInitialised = setDataSource(mPlayer, path);
        }


        private boolean setDataSource(MediaPlayer mediaPlayer, String path) {

            try {
                AssetFileDescriptor afd = getAssets().openFd(path);
                mediaPlayer.reset();
                mediaPlayer.setOnPreparedListener(null);
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.setOnErrorListener(this);

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                return false;
            } catch (SecurityException ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mPlayer.release();
            mPlayer = null;
        }

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {

            Log.e("PlaybackService", "Error: " + what);

            switch (what) {
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED: {

                    mIsPlayerInitialised = false;
                    mPlayer.release();
                    mPlayer = new MediaPlayer();

                    return true;
                }
            }

            return false;
        }

    }

    public void stop() {
        if (mediaPlayback != null && mediaPlayback.mIsPlayerInitialised) {
           mediaPlayback.release();
        }

    }

    public void play() {
        if (mediaPlayback.mIsPlayerInitialised) {
            mediaPlayback.start();
            mIsSupposedToBePlaying = true;
        }

    }

    public void pause() {
        if (mIsSupposedToBePlaying) {
            mediaPlayback.pause();
            mIsSupposedToBePlaying = false;
            mPausedByTransientLossOfFocus = false;
        }

    }

    public boolean openFile(String path) {
        {
            if (path == null) {
                return false;
            }
            mediaPlayback.setDataSource(path);
            if (mediaPlayback.mIsPlayerInitialised) {
                return true;
            }
            stop();
            return false;
        }
    }

    public long getDuration() {
        synchronized (this) {
            if (mediaPlayback != null && mediaPlayback.mIsPlayerInitialised) {
                return mediaPlayback.getDuration();
            }
        }
        return -1;
    }

    public long getPosition() {
        synchronized (this) {
            if (mediaPlayback != null && mediaPlayback.mIsPlayerInitialised) {
                return mediaPlayback.getPosition();
            }
        }
        return 0;
    }

    public void seek(long pos) {
        {
            if (mediaPlayback != null && mediaPlayback.mIsPlayerInitialised) {
                if (pos < 0) {
                    pos = 0;
                } else if (pos > mediaPlayback.getDuration()) {
                    pos = mediaPlayback.getDuration();
                }
                mediaPlayback.seek(pos);
            }
        }
    }

    public void sendUpdateUIBroadcast() {
        Intent serviceIntent = new Intent();
        serviceIntent.setAction("update_media_ui");
        sendBroadcast(serviceIntent);
    }


    private static class ServiceStub extends IPlaybackService.Stub {

        private final MediaPlayerService _service;

        private ServiceStub(final MediaPlayerService service) {
            _service = service;
        }

        @Override
        public void stop() throws RemoteException {
            _service.stop();
        }

        @Override
        public void play() throws RemoteException {
            _service.play();
        }

        @Override
        public void pause() throws RemoteException {
            _service.pause();
        }

        @Override
        public boolean openFile(String path) throws RemoteException {
            return _service.openFile(path);
        }

        @Override
        public long getDuration() throws RemoteException {
            return _service.getDuration();
        }


        @Override
        public long getPosition() throws RemoteException {
            return _service.getPosition();
        }

        @Override
        public void seek(long pos) throws RemoteException {
            _service.seek(pos);
        }

        @Override
        public boolean isPlaying() {
            return _service.mIsSupposedToBePlaying;
        }
    }
}
