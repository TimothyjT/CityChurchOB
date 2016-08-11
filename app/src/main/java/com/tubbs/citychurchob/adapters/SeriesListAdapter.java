package com.tubbs.citychurchob.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.models.SeriesObjectModel;

import java.util.ArrayList;

/**
 * Created by Tubbster on 7/3/15.
 */
public class SeriesListAdapter extends ArrayAdapter<SeriesObjectModel> {
    private final Context context;
    private final ArrayList<SeriesObjectModel> models;


    public SeriesListAdapter(Context context, ArrayList<SeriesObjectModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.models = objects;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_series_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.series_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.series_image);

        textView.setText(models.get(position).getmSeriesName());
        imageView.setImageBitmap(models.get(position).getmSeriesImage());
        return rowView;
    }
}
