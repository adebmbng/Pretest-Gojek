package com.debam.pretest_gojek.services.response;

import com.debam.pretest_gojek.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Debam on 2/5/17.
 */
public class GetAllContactResponse {
    private List<Contact> listContact;

    public List<Contact> getListContact() {
        return listContact;
    }

    public void setListContact(List<Contact> listContact) {
        this.listContact = listContact;
    }
}
