package com.tubbs.citychurchob.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Tubbster on 7/10/15.
 */
public class SeriesObjectModel implements Parcelable {

    private String mSeriesName;
    private int mPosition;
    private Bitmap mSeriesImage;
    private ArrayList<String> mMessageTitle;
    private ArrayList<String> mAudioUrl;
    private ArrayList<String> mPublishDateMonth;
    private ArrayList<String> mPublishDateDay;
    private ArrayList<String> mMessageLength;


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSeriesName);
        dest.writeInt(mPosition);
        dest.writeStringList(mMessageTitle);
        dest.writeStringList(mAudioUrl);
        dest.writeStringList(mPublishDateMonth);
        dest.writeStringList(mPublishDateDay);
        dest.writeStringList(mMessageLength);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<SeriesObjectModel> CREATOR = new Parcelable.Creator<SeriesObjectModel>() {
        @Override
        public SeriesObjectModel createFromParcel(Parcel source) {
            return new SeriesObjectModel(source);
        }

        @Override
        public SeriesObjectModel[] newArray(int size) {
            return new SeriesObjectModel[size];
        }
    };

    public SeriesObjectModel() {

    }

    public SeriesObjectModel(Parcel pc) {
        mSeriesName = pc.readString();
        mPosition = pc.readInt();
        mMessageTitle = pc.createStringArrayList();
        mAudioUrl = pc.createStringArrayList();
        mPublishDateMonth = pc.createStringArrayList();
        mPublishDateDay = pc.createStringArrayList();
        mMessageLength = pc.createStringArrayList();


    }

    public String getmSeriesName() {
        return mSeriesName;
    }

    public void setmSeriesName(String mSeriesName) {
        this.mSeriesName = mSeriesName;
    }


    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public Bitmap getmSeriesImage() {
        return mSeriesImage;
    }

    public void setmSeriesImage(Bitmap mSeriesImage) {
        this.mSeriesImage = mSeriesImage;
    }

    public ArrayList<String> getmMessageTitle() {
        return mMessageTitle;
    }

    public void setmMessageTitle(ArrayList<String> mMessageTitle) {
        this.mMessageTitle = mMessageTitle;
    }

    public ArrayList<String> getmAudioUrl() {
        return mAudioUrl;
    }

    public void setmAudioUrl(ArrayList<String> mAudioUrl) {
        this.mAudioUrl = mAudioUrl;
    }

    public ArrayList<String> getmPublishDateMonth() {
        return mPublishDateMonth;
    }

    public void setmPublishDateMonth(ArrayList<String> mPublishDateMonth) {
        this.mPublishDateMonth = mPublishDateMonth;
    }

    public ArrayList<String> getmPublishDateDay() {
        return mPublishDateDay;
    }

    public void setmPublishDateDay(ArrayList<String> mPublishDateDay) {
        this.mPublishDateDay = mPublishDateDay;
    }

    public ArrayList<String> getmMessageLength() {
        return mMessageLength;
    }

    public void setmMessageLength(ArrayList<String> mMessageLength) {
        this.mMessageLength = mMessageLength;
    }




    public byte[] convertBitMaptoBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;

    }

    public Bitmap compressBitmap(Bitmap bitmap){
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        byte[] bitmapBytes = convertBitMaptoBytes(bitmap);
        Bitmap thumbnail = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length),96,96,false);
        return  thumbnail;
    }
}
