package com.tubbs.citychurchob.mediaplayer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.models.SeriesObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MediaPlayerFragment extends Fragment implements Runnable, ServiceConnection {

    private static final String TAG = "MediaPlayerFragment";


    public ImageView mSeriesImage;

    public MediaPlayerService mService;

    private SeriesObjectModel mModel;
    private int mPosition;
    private String mAudioURL;


    public boolean isPrepared;

    public ProgressBar progressBar;
    public SeekBar seekBar;
    public ImageButton playButton;
    public ImageButton pauseButton;
    public TextView messageTitle;
    public TextView seriesTitle;
    public TextView mProgressText;
    public TextView mDurationText;
    private TextView mNoAudioText;

    private BroadcastReceiver updateMediaUIReciever;

    private ServiceUtils.ServiceToken mToken;

    public MediaPlayerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_media_player, container, false);
        mSeriesImage = (ImageView) v.findViewById(R.id.series_image);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        seekBar = (SeekBar) v.findViewById(R.id.seek_bar);
        playButton = (ImageButton) v.findViewById(R.id.play_button);
        pauseButton = (ImageButton) v.findViewById(R.id.pause_button);
        messageTitle = (TextView) v.findViewById(R.id.message_title);
        seriesTitle = (TextView) v.findViewById(R.id.series_name);
        mProgressText = (TextView) v.findViewById(R.id.progress_text);
        mDurationText = (TextView) v.findViewById(R.id.duration_text);
        mNoAudioText = (TextView) v.findViewById(R.id.no_audio_text);


        mNoAudioText.setVisibility(v.GONE);
        playButton.setVisibility(v.GONE);
        pauseButton.setVisibility(v.GONE);


        if (getArguments() != null) {
            mAudioURL = getArguments().getString(Keys.AUDIO_URL_KEY);
            mModel = getArguments().getParcelable(Keys.INTENT_SERIES_MODEL_OBJECT_KEY);
            mPosition = getArguments().getInt(Keys.POSTION_KEY);
            messageTitle.setText(mModel.getmMessageTitle().get(mPosition));
            seriesTitle.setText(mModel.getmSeriesName());
        }

        if(PlaybackUtils.isPlaying()){
            PlaybackUtils.stop();

        }
        //Bind to MediaPlayerService
        mToken = ServiceUtils.bindToService(getActivity(), this);



        //Defines a Broadcast Reciever to know when the MediaPlayer has been fully prepared so we can update the UI with information from the MediaPlayer
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("update_media_ui");
        updateMediaUIReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int minutesDuration = (int)PlaybackUtils.getDuration() / 60000;
                int secondsDuration = ((int)PlaybackUtils.getDuration() % 60000) / 1000;
                mDurationText.setText(Integer.toString(minutesDuration) + ":" + Integer.toString(secondsDuration));
                progressBar.setVisibility(ProgressBar.GONE);
                pauseButton.setVisibility(View.VISIBLE);
                seekBar.setEnabled(true);
                seekBar.setMax((int)PlaybackUtils.getDuration());
                new Thread(MediaPlayerFragment.this).start();
                playback();
            }
        };
        getActivity().registerReceiver(updateMediaUIReciever, intentFilter);

        return v;
    }


    /*
    Calculates and displays the correct amount of progress where the audio file is at. Example:  23:33 --> 23:34 --> 23:35
     */
    @Override
    public void run() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    long prg = (long) progress;
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(prg);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(prg) % 60;
                    PlaybackUtils.seek(progress);
                    if (minutes == 0 && seconds < 10) {
                        mProgressText.setText("00" + ":" + "0" + seconds);
                    } else if (minutes == 0 && seconds >= 10) {
                        mProgressText.setText("00" + ":" + seconds);
                    } else if (minutes < 10 && seconds < 10) {
                        mProgressText.setText("0" + minutes + ":" + "0" + seconds);
                    } else if (minutes < 10 && seconds >= 10) {
                        mProgressText.setText("0" + minutes + ":" + seconds);
                    } else if (minutes >= 10 && seconds < 10) {
                        mProgressText.setText(minutes + ":" + "0" + seconds);
                    } else {
                        mProgressText.setText(minutes + ":" + seconds);
                    }
                } else {
                    long prg = (long) progress;
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(prg);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(prg) % 60;
                    if (minutes == 0 && seconds < 10) {
                        mProgressText.setText("00" + ":" + "0" + seconds);
                    } else if (minutes == 0 && seconds >= 10) {
                        mProgressText.setText("00" + ":" + seconds);
                    } else if (minutes < 10 && seconds < 10) {
                        mProgressText.setText("0" + minutes + ":" + "0" + seconds);
                    } else if (minutes < 10 && seconds >= 10) {
                        mProgressText.setText("0" + minutes + ":" + seconds);
                    } else if (minutes >= 10 && seconds < 10) {
                        mProgressText.setText(minutes + ":" + "0" + seconds);
                    } else {
                        mProgressText.setText(minutes + ":" + seconds);
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        int currentPosition = 0;
        int total = (int)PlaybackUtils.getDuration();
        while (currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = (int)PlaybackUtils.getPosition();
                seekBar.setProgress(currentPosition);
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
        }
    }


    public void getImage(final int position) {
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

    public static MediaPlayerFragment newInstance(String url, int position, SeriesObjectModel model) {
        MediaPlayerFragment fragment = new MediaPlayerFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.POSTION_KEY, position);
        args.putParcelable(Keys.INTENT_SERIES_MODEL_OBJECT_KEY, model);
        args.putString(Keys.AUDIO_URL_KEY, url);
        fragment.setArguments(args);
        return fragment;
    }


    public void playback() {
        seekBar.setEnabled(true);
        if (pauseButton.getVisibility() != View.GONE) {
            pauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlaybackUtils.pause();
                    pauseButton.setVisibility(View.GONE);
                    playButton.setVisibility(View.VISIBLE);
                    playback();
                }
            });
        } else if (playButton.getVisibility() != View.GONE) {
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlaybackUtils.play();
                    pauseButton.setVisibility(View.VISIBLE);
                    playButton.setVisibility(View.GONE);
                    playback();
                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(updateMediaUIReciever);
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        //Hooray, we've connected to our service.
        //If we've arrived here, we should be able to make any calls via our PlaybackUtils class, and expect the service to
        //respond accordingly


        //Pass an intent to MediaPlayerService which will recieve it in onStartCommand() once the Service has been fully binded
        Intent intent = new Intent(getActivity(), MediaPlayerService.class);
        intent.putExtra(Keys.INTENT_SERIES_MODEL_OBJECT_KEY, mModel);
        intent.putExtra(Keys.POSTION_KEY, mPosition);
        intent.putExtra(Keys.AUDIO_URL_KEY, mAudioURL);
        getActivity().startService(intent);
        getImage(mModel.getmPosition());
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        //The service is disconnected.. We don't really care about this.. do we?
    }

    //
//    public void startMediaPlayer() {
//        if (getArguments() != null) {
//            progressBar.setVisibility(ProgressBar.VISIBLE);
//            pauseButton.setVisibility(Button.GONE);
//            playButton.setVisibility(Button.GONE);
//
//
//            getImage(mModel.getmPosition());
//
//            messageTitle.setText(mModel.getmMessageTitle().get(mPosition));
//            seriesTitle.setText(mModel.getmSeriesName());
//
//        } else {
//            mProgressText.setVisibility(View.GONE);
//            mDurationText.setVisibility(View.GONE);
//            progressBar.setVisibility(ProgressBar.GONE);
//            seekBar.setVisibility(SeekBar.GONE);
//            messageTitle.setVisibility(View.GONE);
//            seriesTitle.setVisibility(View.GONE);
//            pauseButton.setVisibility(Button.GONE);
//            playButton.setVisibility(Button.GONE);
//        }
//    }
//
//
//
//    public void initUI() {
//        //startMediaPlayer();
//        if (seekBar.getProgress() > 0) {
//        } else {
//            Log.i(TAG, "Inside else");
//            int minutesDuration = mp.getDuration() / 60000;
//            int secondsDuration = (mp.getDuration() % 60000) / 1000;
//            mDurationText.setText(Integer.toString(minutesDuration) + ":" + Integer.toString(secondsDuration));
//            mProgressText.setText("00:00");
//            seekBar.setProgress(0);
//            seekBar.setMax(mp.getDuration());
//        }
//
//        isPlaying = true;
//        pauseButton.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(ProgressBar.GONE);
//    }

    //Defines callbacks for service binding, passed to bindService()
//    private ServiceConnection mConnection = new ServiceConnection() {
//        private LocalBinder binder;
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            //We've bound to MediaPlayerService, cast the IBinder and get MediaPlayerService instance
//            binder = (LocalBinder) service;
//            mService = binder.getService();
//            getImage(mPosition);
//            mp = mService.getMediaPlayer();
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//
//        public void getBinder(LocalBinder binder){
//
//        }
//    };
}



