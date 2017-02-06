package com.debam.pretest_gojek.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.debam.pretest_gojek.MyApplication;
import com.debam.pretest_gojek.R;
import com.debam.pretest_gojek.interfaces.ContactDetailPresenter;
import com.debam.pretest_gojek.interfaces.ContactDetailView;
import com.debam.pretest_gojek.models.Contact;
import com.debam.pretest_gojek.presenters.ContacDetailsPresenters;
import com.debam.pretest_gojek.repository.ContactRepository;
import com.debam.pretest_gojek.services.APIServices;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Debam on 2/6/2017.
 */

public class ContactDetailsActivity extends AppCompatActivity implements ContactDetailView{

    @BindView(R.id.tvName)TextView tvName;
    @BindView(R.id.tvEmail)TextView tvEmail;
    @BindView(R.id.tvPhone)TextView tvPhone;

    @BindView(R.id.imgProfile)CircleImageView imgProfile;

    @BindDrawable(R.drawable.grey)Drawable grey;

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.toolbar_title)TextView titleToolbar;

    private ContactDetailPresenter mPresenter;

    private Context ctx;

    private Contact con;

    @Inject
    SharedPreferences sp;

    @Inject
    ContactRepository db;

    @Inject
    APIServices api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_activity);

        ctx =this;
        ButterKnife.bind(ContactDetailsActivity.this);

        ((MyApplication) getApplication()).getDc().inject(this);

        mPresenter = new ContacDetailsPresenters(this, db, api);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleToolbar.setText("Detail");

        imgProfile.setImageDrawable(grey);

        mPresenter.loadDetails(getIntent().getStringExtra("id"));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessLoad(Contact c) {
        con = c;
        Log.d("Con", con.getPhone_number()+" "+con.getEmail());
        tvEmail.setText(c.getEmail());
        tvName.setText(c.getFirst_name()+" "+c.getLast_name());
        tvPhone.setText(c.getPhone_number());
        Glide.with(ctx).load(c.getProfile_pic())
                .thumbnail(0.5f)
                .crossFade()
                .error(grey)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
    }

    @OnClick(R.id.fav)
    void fav(){
        if(con.getFavorite()!=null && con.getFavorite().equals("")){
            if(con.getFavorite().equalsIgnoreCase("false")){
                mPresenter.setFavorite(getIntent().getStringExtra("id"));
            } else {
                mPresenter.setUnFavorite(getIntent().getStringExtra("id"));
            }
        }

    }

    @Override
    public void onFailedLoad() {

    }

    @Override
    public void setPresenter(ContactDetailPresenter presenter) {
        if(presenter==null){
            mPresenter = presenter;
        }
    }
}
