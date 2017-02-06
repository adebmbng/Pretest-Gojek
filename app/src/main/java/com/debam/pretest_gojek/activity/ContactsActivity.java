package com.debam.pretest_gojek.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.debam.pretest_gojek.MyApplication;
import com.debam.pretest_gojek.R;
import com.debam.pretest_gojek.adapters.ContactAdapter;
import com.debam.pretest_gojek.adapters.ContactAdapterFavorites;
import com.debam.pretest_gojek.interfaces.ContactViewListener;
import com.debam.pretest_gojek.interfaces.PresenterContactsListener;
import com.debam.pretest_gojek.models.Contact;
import com.debam.pretest_gojek.presenters.ContactPresenter;
import com.debam.pretest_gojek.repository.ContactRepository;
import com.debam.pretest_gojek.services.APIServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Debam on 2/3/17.
 */

public class ContactsActivity extends AppCompatActivity implements ContactViewListener{

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.list_view) ListView mListView;
    @BindView(R.id.list_view_favorites) ListView mListViewFav;
    @BindView(R.id.fab) FloatingActionButton mFab;

    @BindView(R.id.vaMain) ViewAnimator vaMain;
    @BindView(R.id.tvLoading) TextView tvLoading;

    @BindString(R.string.loading)String loading;
    @BindString(R.string.no_contact_found)String no_contact_found;

    @BindView(R.id.progress)ProgressBar progress;

    private Context ctx;

    private PresenterContactsListener mPresenter;

    private List<Contact> contactsList;
    private List<Contact> mListFavorites;

    private String TAG = "";

    @Inject
    SharedPreferences sp;

    @Inject
    ContactRepository db;

    @Inject
    APIServices api;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        ctx = this;
        ButterKnife.bind(ContactsActivity.this);

        vaMain.setDisplayedChild(0);

        ((MyApplication) getApplication()).getDc().inject(this);

        setSupportActionBar(mToolbar);
        mPresenter = new ContactPresenter(this, db, api);

        //if db null
        if(db.countDB()==0) {
            Log.d(TAG, "db=0");
            mPresenter.start();
        } else {
            Log.d(TAG, "db="+db.countDB());
            showContacts();
        }

    }

    @OnClick(R.id.fab)
    void addContact(){
        Intent i = new Intent(ContactsActivity.this, AddContactActivity.class);
        startActivity(i);
    }

    @Override
    public void setLoadingIndicator(boolean status) {
        if(status) {
            vaMain.setDisplayedChild(1);
            tvLoading.setText(loading);
        } else {
            vaMain.setDisplayedChild(0);
            tvLoading.setText(loading);
        }
    }

    @Override
    public void showContacts() {
        contactsList = mPresenter.getContacts();
        mListFavorites = new ArrayList<>();
        for(Contact c : contactsList){
            if(c.getFavorite()!=null&&c.getFavorite().equalsIgnoreCase("true")){
                mListFavorites.add(c);
                contactsList.remove(c);
            }
        }
        if(mListFavorites.size()==0){
            mListViewFav.setVisibility(View.GONE);
        }
        ContactAdapterFavorites adapterFavorites = new ContactAdapterFavorites(ctx, R.layout.contact_item_facorites, mListFavorites);
        mListViewFav.setAdapter(adapterFavorites);

        ContactAdapter adapter = new ContactAdapter(ctx, R.layout.contact_item, contactsList);
        mListView.setAdapter(adapter);

    }

    @Override
    public void showFavorites() {

    }

    @Override
    public void onConectionProblem() {
        vaMain.setDisplayedChild(1);
        progress.setVisibility(View.GONE);
        tvLoading.setText(no_contact_found);
    }

    @Override
    public void onNoContact() {
        vaMain.setDisplayedChild(1);
        progress.setVisibility(View.GONE);
        tvLoading.setText(no_contact_found);
    }

    @Override
    public List<Contact> onLoadCompete() {

        return null;
    }

    @Override
    public void showToast() {

    }

    @Override
    public void setPresenter(PresenterContactsListener presenter) {
        if(presenter != null){
            mPresenter = presenter;
        }
    }
}
