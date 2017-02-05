package com.debam.pretest_gojek.presenters;

import android.util.Log;

import com.debam.pretest_gojek.interfaces.ContactViewListener;
import com.debam.pretest_gojek.interfaces.PresenterContactsListener;
import com.debam.pretest_gojek.models.Contact;
import com.debam.pretest_gojek.repository.ContactRepository;
import com.debam.pretest_gojek.services.APIServices;
import com.debam.pretest_gojek.services.response.GetAllContactResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Debam on 2/4/17.
 */


public class ContactPresenter implements PresenterContactsListener {

    private ContactViewListener view;
    private ContactRepository db;
    private APIServices api;

    public ContactPresenter(ContactViewListener view, ContactRepository db, APIServices api) {
        this.view = view;
        this.db = db;
        this.api = api;
    }

    @Override
    public void onLoadStarted() {


    }

    @Override
    public void onLoadCompleted() {

    }

    @Override
    public void onLoadContactsBComplete() {

    }

    @Override
    public List<Contact> getContacts() {
        return db.loaddb();
    }

    @Override
    public Contact getContactById(int i) {
        return null;
    }

    @Override
    public Contact getProductByNum(String num) {
        return null;
    }

    @Override
    public void deleteContact(Contact c) {

    }

    @Override
    public void updateContact(Contact old, Contact newContact) {

    }

    @Override
    public void addContact(Contact c) {

    }

    @Override
    public void start() {
        view.setLoadingIndicator(true);
        Call<GetAllContactResponse> call = api.getContacts();
        call.enqueue(new Callback<GetAllContactResponse>() {
            @Override
            public void onResponse(Call<GetAllContactResponse> call, Response<GetAllContactResponse> response) {
                Log.d("response", response.body().toString());
                if(response.isSuccessful()){
                    Log.d("response", response.body().toString());
                    db.insert(response.body().getListContact());
                    view.setLoadingIndicator(false);
                    view.showContacts();
                } else {
                    view.onConectionProblem();
                }
            }

            @Override
            public void onFailure(Call<GetAllContactResponse> call, Throwable t) {
                view.onConectionProblem();
                Log.d("call", t.toString()+" "+call.toString());
            }
        });
    }
}
