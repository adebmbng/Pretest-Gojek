package com.debam.pretest_gojek.interfaces;

import com.debam.pretest_gojek.BaseView;
import com.debam.pretest_gojek.models.Contact;

import java.util.List;

/**
 * Created by Debam on 2/3/17.
 */

public interface ContactViewListener extends BaseView<PresenterContactsListener>{

    void setLoadingIndicator(boolean status);

    void showContacts();

    void showFavorites();

    void onConectionProblem();

    void onNoContact();

    List<Contact> onLoadCompete();

    void showToast();
}
