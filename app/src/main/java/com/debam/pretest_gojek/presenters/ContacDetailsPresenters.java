package com.debam.pretest_gojek.presenters;

import com.debam.pretest_gojek.interfaces.ContactDetailPresenter;
import com.debam.pretest_gojek.interfaces.ContactDetailView;
import com.debam.pretest_gojek.models.Contact;
import com.debam.pretest_gojek.repository.ContactRepository;
import com.debam.pretest_gojek.services.APIServices;

/**
 * Created by Debam on 2/6/2017.
 */

public class ContacDetailsPresenters implements ContactDetailPresenter {

    private ContactDetailView view;
    private ContactRepository db;
    private APIServices api;

    public ContacDetailsPresenters(ContactDetailView view, ContactRepository db, APIServices api) {
        this.view = view;
        this.db = db;
        this.api = api;
    }

    @Override
    public void loadDetails(String id) {
        view.onSuccessLoad(db.getContactById(id));
    }

    @Override
    public void setFavorite(String id) {
        db.setFavorite(id, true);
    }

    @Override
    public void setUnFavorite(String id) {
        db.setFavorite(id, false);
    }

    @Override
    public void start() {

    }
}
