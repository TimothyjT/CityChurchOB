package com.tubbs.citychurchob.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

/**
 * Created by Tubbster on 7/24/15.
 */
public class EventObjectModel implements Parcelable {
    private String mEventTitle;
    private int mPosition;
    private Bitmap mEventImage;
    private String mEventDate;
    private String mEventDesc;


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEventTitle);
        dest.writeInt(mPosition);
        dest.writeString(mEventDate);
        dest.writeString(mEventDesc);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<EventObjectModel> CREATOR = new Parcelable.Creator<EventObjectModel>() {
        @Override
        public EventObjectModel createFromParcel(Parcel source) {
            return new EventObjectModel(source);
        }

        @Override
        public EventObjectModel[] newArray(int size) {
            return new EventObjectModel[size];
        }
    };

    public EventObjectModel() {

    }

    public EventObjectModel(Parcel pc) {
        mEventTitle = pc.readString();
        mPosition = pc.readInt();
        mEventDate = pc.readString();
        mEventDesc = pc.readString();


    }

    public String getmEventTitle() {
        return mEventTitle;
    }

    public void setmEventTitle(String mEventTitle) {
        this.mEventTitle = mEventTitle;
    }


    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public Bitmap getmEventImage() {
        return mEventImage;
    }

    public void setmEventImage(Bitmap mEventImage) {
        this.mEventImage = mEventImage;
    }

    public String getmEventDate() {
        return mEventDate;
    }

    public void setmEventDate(String mEventDate) {
        this.mEventDate = mEventDate;
    }

    public String getmEventDesc() {
        return mEventDesc;
    }

    public void setmEventDesc(String mEventDesc) {
        this.mEventDesc = mEventDesc;
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
