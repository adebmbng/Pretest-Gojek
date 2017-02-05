package com.debam.pretest_gojek.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.debam.pretest_gojek.R;
import com.debam.pretest_gojek.models.Contact;

import java.util.List;

/**
 * Created by Debam on 2/5/17.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private List<Contact> mList;


    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        Contact c = mList.get(position);

        TextView tvName = (TextView) v.findViewById(R.id.tvNameContact);
        tvName.setText(c.getFirst_name()+" "+c.getLast_name());

        return v;
    }
}
