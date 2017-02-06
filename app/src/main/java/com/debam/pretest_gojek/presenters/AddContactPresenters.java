package com.debam.pretest_gojek.presenters;

import android.content.Context;
import android.util.Log;

import com.debam.pretest_gojek.interfaces.AddContactPresenter;
import com.debam.pretest_gojek.interfaces.AddContactView;
import com.debam.pretest_gojek.models.Contact;
import com.debam.pretest_gojek.repository.ContactRepository;
import com.debam.pretest_gojek.services.APIServices;

import java.text.DateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Debam on 2/6/2017.
 */

public class AddContactPresenters implements AddContactPresenter {

    private AddContactView mview;
    private Context ctx;
    private APIServices api;
    private ContactRepository db;

    public AddContactPresenters(AddContactView mview, Context ctx, APIServices api, ContactRepository db) {
        this.mview = mview;
        this.ctx = ctx;
        this.api = api;
        this.db = db;
    }

    @Override
    public boolean validateInput() {
        mview.onValidationSuccess(mview.gettilFirstName());
        mview.onValidationSuccess(mview.gettilLastName());
        mview.onValidationSuccess(mview.gettilPhone());
        mview.onValidationSuccess(mview.gettilEmail());

        if(mview.getetFirstName().getText().toString().equals("")||
                mview.getetFirstName().getText()==null){
            mview.onValidationFailed(mview.gettilFirstName());
            return false;
        } else if(mview.getetLastName().getText().toString().equals("")||
                mview.getetLastName().getText()==null){
            mview.onValidationFailed(mview.gettilLastName());
            return false;
        } else if(mview.getPhone().getText().toString().equals("")||
                mview.getPhone().getText()==null){
            mview.onValidationFailed(mview.gettilPhone());
            return false;
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mview.getetEmail().getText()).matches()||
                mview.getetEmail().getText().toString().equals("")||
                mview.getetEmail().getText()==null){
            mview.onValidationFailed(mview.gettilEmail());
            return false;
        }

        return true;
    }

    @Override
    public void start() {

        if(validateInput()){
            mview.onLoad();
            String fName = mview.getetFirstName().getText().toString();
            String lName = mview.getetLastName().getText().toString();
            String email = mview.getetEmail().getText().toString();
            String phone = mview.getPhone().getText().toString();
            String date = DateFormat.getDateTimeInstance().format(new Date());

            Call<Contact> call = api.createContact(fName, lName, phone, email, null, null, date, date);
            call.enqueue(new Callback<Contact>() {
                @Override
                public void onResponse(Call<Contact> call, Response<Contact> response) {
                    Log.d("Presenters", response.toString());
                    if(response.isSuccessful()){
                        mview.onSuccessfull();

                    } else {
                        mview.onFailed();
                    }
                }

                @Override
                public void onFailure(Call<Contact> call, Throwable t) {
                    mview.onFailed();
                    Log.d("Presenters", t.toString());
                }
            });
        }

    }
}
