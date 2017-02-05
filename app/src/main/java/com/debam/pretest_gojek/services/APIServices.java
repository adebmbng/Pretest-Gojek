package com.debam.pretest_gojek.services;

import com.debam.pretest_gojek.models.Contact;
import com.debam.pretest_gojek.services.response.GetAllContactResponse;
import com.debam.pretest_gojek.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Debam on 2/5/17.
 */

public interface APIServices {

    @GET(Constant.URL_CONSTANT.GET_CONTACTS)
    Call<GetAllContactResponse> getContacts();

    @GET
    Call<Contact> getContact(@Url String url);

}
