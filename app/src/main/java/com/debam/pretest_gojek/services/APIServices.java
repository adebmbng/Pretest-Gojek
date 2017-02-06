package com.debam.pretest_gojek.services;

import com.debam.pretest_gojek.models.Contact;
import com.debam.pretest_gojek.services.response.GetAllContactResponse;
import com.debam.pretest_gojek.utils.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Debam on 2/5/17.
 */

public interface APIServices {

    @GET(Constant.URL_CONSTANT.GET_CONTACTS)
    Call<List<Contact>> getContacts();

    @GET
    Call<Contact> getContact(@Url String url);

    @FormUrlEncoded
    @POST(Constant.URL_CONSTANT.GET_CONTACTS)
    Call<Contact> createContact (@Field("first_name") String firstName,
                                 @Field("last_name") String lastName,
                                 @Field("phone_number") String phone,
                                 @Field("email") String email,
                                 @Field("profile_pic") String pic,
                                 @Field("favorite") String favorite,
                                 @Field("created_at") String created,
                                 @Field("updated_at") String updated);

}
