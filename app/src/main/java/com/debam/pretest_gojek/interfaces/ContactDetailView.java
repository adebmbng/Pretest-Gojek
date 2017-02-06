package com.debam.pretest_gojek.interfaces;

import com.debam.pretest_gojek.BaseView;
import com.debam.pretest_gojek.models.Contact;

/**
 * Created by Debam on 2/6/2017.
 */

public interface ContactDetailView extends BaseView<ContactDetailPresenter> {

    void onSuccessLoad(Contact c);

    void onFailedLoad();
}
