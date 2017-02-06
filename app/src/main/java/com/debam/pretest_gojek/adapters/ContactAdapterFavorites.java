package com.debam.pretest_gojek.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.debam.pretest_gojek.R;
import com.debam.pretest_gojek.models.Contact;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Debam on 2/6/2017.
 */

public class ContactAdapterFavorites extends ArrayAdapter<Contact> {

    private List<Contact> mList;
    private String TAG = "ContactAdapter";
    private int resorce;
    private Context ctx;


    public ContactAdapterFavorites(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        mList = objects;
        Collections.sort(mList, new Comparator<Contact>() {
            @Override
            public int compare(Contact a, Contact b) {
                return a.getFirst_name().compareTo(b.getFirst_name());
            }
        });
        this.resorce = resource;
        this.ctx = context;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(resorce, null);
        }

        Contact c = mList.get(position);

        TextView tvName = (TextView) v.findViewById(R.id.tvNameContact);
        tvName.setText(c.getFirst_name() + " " + c.getLast_name());

        ImageView tvFront = (ImageView) v.findViewById(R.id.imgFavorites);
        if (position == 0) {
            tvFront.setVisibility(View.VISIBLE);
        } else {
            tvFront.setVisibility(View.INVISIBLE);
        }

        Drawable defaultImage = ctx.getDrawable(R.drawable.grey);
        CircleImageView profilPic = (CircleImageView) v.findViewById(R.id.profile_image);
        Glide.with(ctx).load(c.getProfile_pic())
                .thumbnail(0.5f)
                .crossFade()
                .error(defaultImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profilPic);

        return v;
    }
}
