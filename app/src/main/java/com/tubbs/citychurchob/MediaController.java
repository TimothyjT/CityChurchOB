package com.tubbs.citychurchob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tubbs.citychurchob.activities.MainActivity;
import com.tubbs.citychurchob.models.SeriesObjectModel;
import com.tubbs.citychurchob.util.Keys;

import java.util.ArrayList;
import java.util.List;


public class MediaController extends ActionBarActivity {
    private static final String TAG = "MediaController";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_controller);

        generateSeriesObjects();
    }


    public void generateSeriesObjects() {
        final ArrayList<SeriesObjectModel> mSeriesObjectList = new ArrayList<>();

        ParseQuery<ParseObject> query = new ParseQuery<>(Keys.SERIES_KEY);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                int i;
                for (i = 0; i < list.size(); i++) {
                    SeriesObjectModel model = new SeriesObjectModel();
                    model.setmSeriesName(list.get(i).getString(Keys.SERIES_NAME_KEY));
                    model.setmPosition(i);
                    model.setmMessageTitle((ArrayList) list.get(i).getList(Keys.MESSAGE_TITLE_KEY));
                    model.setmAudioUrl((ArrayList) list.get(i).getList(Keys.PARSE_URL_KEY));
                    model.setmPublishDateMonth((ArrayList) list.get(i).getList(Keys.DATE_MONTH_KEY));
                    model.setmPublishDateDay((ArrayList) list.get(i).getList(Keys.DATE_DAY_KEY));
                    model.setmMessageLength((ArrayList) list.get(i).getList(Keys.MESSAGE_LENGTH_KEY));
                    mSeriesObjectList.add(model);


                }
                if (i == list.size()) {
                    Intent x = new Intent(getApplicationContext(), MainActivity.class);
                    x.putExtra(Keys.INTENT_SERIES_MODEL_KEY, mSeriesObjectList);
                    startActivity(x);
                }

            }
        });
    }


//    public void setSeriesImageThumbnail(final int position,final ArrayList<SeriesObjectModel> models,final SeriesObjectModel model) {
//        ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.SERIES_KEY);
//        query.orderByDescending("createdAt");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
//                    ParseFile file = list.get(position).getParseFile(Keys.SERIES_IMAGE_KEY);
//                    if (file != null) {
//                        file.getDataInBackground(new GetDataCallback() {
//                            @Override
//                            public void done(byte[] bytes, ParseException e) {
//                                if (e == null) {
//                                    Bitmap mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                                    models.get(position).setmEventImage(mBitmap);
//                                    Log.i(TAG,"image.." + models.get(position).getmEventImage());
//                                }
//                            }
//                        });
//                    }
//                }
//            }
//        });
//    }

}
