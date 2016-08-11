package com.tubbs.citychurchob.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tubbs.citychurchob.R;
import com.tubbs.citychurchob.models.SeriesObjectModel;

/**
 * Created by Tubbster on 7/3/15.
 */
public class TitleListAdapter extends ArrayAdapter<SeriesObjectModel> {
    private static final String TAG = "TitleListAdapter";
    private final Context context;
    private SeriesObjectModel model;

    public TitleListAdapter(Context context, SeriesObjectModel model) {
        super(context,0);
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.getmMessageTitle().size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_messages_list_item, parent, false);
        TextView mDateMonthText = (TextView) rowView.findViewById(R.id.date_month_text);
        TextView mDateDayText = (TextView) rowView.findViewById(R.id.date_day_text);
        TextView mMessageTitle = (TextView) rowView.findViewById(R.id.title_text);





        mDateMonthText.setText(model.getmPublishDateMonth().get(position));
        mDateDayText.setText(model.getmPublishDateDay().get(position));
        mMessageTitle.setText(model.getmMessageTitle().get(position));

        return rowView;
    }
}
