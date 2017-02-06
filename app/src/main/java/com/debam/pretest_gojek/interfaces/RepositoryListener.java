package com.debam.pretest_gojek.interfaces;

import com.debam.pretest_gojek.models.Contact;

import java.util.List;

/**
 * Created by Debam on 2/4/17.
 */

public interface RepositoryListener {
    void insert(List<Contact> lContact);

    List<Contact> loaddb();

    Contact getContactById(String i);

    Contact getContactByNum(String num);

    void deleteContact(Contact c);

    int countDB();
}
