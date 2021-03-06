package com.debam.pretest_gojek.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.debam.pretest_gojek.R;
import com.debam.pretest_gojek.activity.ContactDetailsActivity;
import com.debam.pretest_gojek.models.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Debam on 2/5/17.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private List<Contact> mList;
    private String TAG = "ContactAdapter";
    private int resorce;
    private Context ctx;


    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        mList = objects;
        Collections.sort(mList, new Comparator<Contact>() {
            @Override
            public int compare(Contact a, Contact b) {
                return a.getFirst_name().toLowerCase().compareTo(b.getFirst_name().toLowerCase());
            }
        });
        this.resorce = resource;
        this.ctx =context;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(resorce, null);
        }

        final Contact c = mList.get(position);

        TextView tvName = (TextView) v.findViewById(R.id.tvNameContact);
        tvName.setText(c.getFirst_name()+" "+c.getLast_name());

        TextView tvFront = (TextView) v.findViewById(R.id.tvFront);
        if(position==0){
            tvFront.setVisibility(View.VISIBLE);
            tvFront.setText(c.getFirst_name().substring(0,1));
        } else if(c.getFirst_name().substring(0,1).equalsIgnoreCase(mList.get(position-1).getFirst_name().substring(0,1))){
            tvFront.setVisibility(View.INVISIBLE);
        } else {
            tvFront.setVisibility(View.VISIBLE);
            tvFront.setText(c.getFirst_name().substring(0,1));
        }

        Drawable defaultImage = ctx.getDrawable(R.drawable.grey);
        CircleImageView profilPic = (CircleImageView) v.findViewById(R.id.profile_image);
        Glide.with(ctx).load(c.getProfile_pic())
                .thumbnail(0.5f)
                .crossFade()
                .error(defaultImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profilPic);

        LinearLayout ll = (LinearLayout) v.findViewById(R.id.llMain);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, ContactDetailsActivity.class);
                i.putExtra("id", c.getId());
                ctx.startActivity(i);
            }
        });


        return v;
    }
}
