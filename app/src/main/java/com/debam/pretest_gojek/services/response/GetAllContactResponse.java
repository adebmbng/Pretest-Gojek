package com.debam.pretest_gojek.services.response;

import com.debam.pretest_gojek.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Debam on 2/5/17.
 */
public class GetAllContactResponse {
    private ArrayList<Contact> listContact;

    public ArrayList<Contact> getListContact() {
        return listContact;
    }

    public void setListContact(ArrayList<Contact> listContact) {
        this.listContact = listContact;
    }
}
