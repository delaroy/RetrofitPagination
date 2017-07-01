package com.bamideleoguntuga.retrofitpagination.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bamideleoguntuga.retrofitpagination.R;
import com.bamideleoguntuga.retrofitpagination.services.models.PaginationItem;

import java.util.List;

/**
 * Created by delaroy on 6/30/17.
 */
public class PaginationListAdapter extends ArrayAdapter<PaginationItem> {

    private Context context;
    private List<PaginationItem> values;

    public PaginationListAdapter(Context context, List<PaginationItem> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_pagination, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.list_item_pagination_text);

        PaginationItem item = values.get(position);
        String message = item.getMessage();
        textView.setText(message);

        return row;
    }
}