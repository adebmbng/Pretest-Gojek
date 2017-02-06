package com.debam.pretest_gojek.interfaces;

import com.debam.pretest_gojek.BasePresenter;
import com.debam.pretest_gojek.models.Contact;

/**
 * Created by Debam on 2/6/2017.
 */

public interface ContactDetailPresenter extends BasePresenter{

    void loadDetails(String id);

    void setFavorite(String id);
    void setUnFavorite(String id);

}
