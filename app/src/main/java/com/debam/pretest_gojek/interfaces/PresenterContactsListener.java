package com.debam.pretest_gojek.interfaces;

import android.content.Context;

import com.debam.pretest_gojek.BasePresenter;
import com.debam.pretest_gojek.models.Contact;

import java.util.List;

/**
 * Created by Debam on 2/3/17.
 */

public interface PresenterContactsListener extends BasePresenter{

    void onLoadStarted();

    void onLoadCompleted();

    void onLoadContactsBComplete();

    List<Contact> getContacts();

    Contact getContactById(int i);

    Contact getProductByNum(String num);

    void deleteContact(Contact c);

    void updateContact(Contact old, Contact newContact);

    void addContact(Contact c);

}
